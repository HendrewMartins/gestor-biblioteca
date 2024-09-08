package br.hendrew.gestor_biblioteca.utils.translate;

import br.hendrew.gestor_biblioteca.annotation.TransientDTO;
import br.hendrew.gestor_biblioteca.exception.ExceptionUtil;
import br.hendrew.gestor_biblioteca.exception.RestWarnException;
import org.codehaus.plexus.util.ReflectionUtils;
import org.codehaus.plexus.util.StringUtils;
import org.reflections.Reflections;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

public class ReflectionUtil {

    private static final String PATH = "br.hendrew.gestor_biblioteca";

    public static Reflections getReflections() {
        return new Reflections(PATH);
    }

    public static Method getGetter(String fieldName, Class<?> clazz) {
        String getterName = "get" + StringUtils.capitalizeFirstLetter(fieldName);
        return Arrays.stream(clazz.getMethods())
                .filter(method -> method.getName().equals(getterName))
                .findFirst().orElse(null);
    }

    public static void copyProperty(Object dtoOrigem, Object dtoDestino) {
        if (dtoOrigem == null || dtoDestino == null) {
            return;
        }

        for (Field fieldOrigem : ReflectionUtils.getFieldsIncludingSuperclasses(dtoOrigem.getClass())) {
            if (fieldOrigem.isAnnotationPresent(TransientDTO.class)) {
                continue;
            }

            try {
                Field fieldDestino = ReflectionUtils.getFieldByNameIncludingSuperclasses(fieldOrigem.getName(), dtoDestino.getClass());
                if (fieldDestino == null) {
                    continue;
                }

                fieldOrigem.setAccessible(true);
                fieldDestino.setAccessible(true);
                fieldDestino.set(dtoDestino, fieldOrigem.get(dtoOrigem));

            } catch (Exception e) {
                System.err.println("Erro ao setar propriedade: " + e.getMessage());
            }
        }
    }

    public static void copyPropertySet(Object dtoOrigem, Object dtoDestino) {
        copyPropertySet(dtoOrigem, dtoDestino, null);
    }

    public static void copyPropertySet(Object dtoOrigem, Object dtoDestino, Object dtoPropriedades) {
        if (dtoOrigem == null || dtoDestino == null) {
            return;
        }

        for (Field fieldOrigem : ReflectionUtils.getFieldsIncludingSuperclasses(dtoOrigem.getClass())) {
            if (fieldOrigem.isAnnotationPresent(TransientDTO.class)) {
                continue;
            }

            String fieldName = fieldOrigem.getName();
            if (dtoPropriedades != null && ReflectionUtils.getSetter(fieldName, dtoPropriedades.getClass()) == null) {
                continue;
            }

            Method metodoSet = ReflectionUtils.getSetter(fieldName, dtoDestino.getClass());
            Method metodoGet = getGetter(fieldName, dtoOrigem.getClass());

            if (metodoSet == null || metodoGet == null || !paramsCompatible(metodoSet.getParameterTypes()[0], metodoGet.getReturnType())) {
                continue;
            }

            try {
                metodoSet.invoke(dtoDestino, metodoGet.invoke(dtoOrigem));
            } catch (Exception e) {
                System.err.println("Erro ao setar propriedade " + fieldName + ": " + e.getMessage());
            }
        }
    }

    public static void copyProperties(Object destino, HashMap<String, ?> registro) {
        for (Field field : destino.getClass().getDeclaredFields()) {
            Object valorCampo = registro.get(field.getName());

            if (valorCampo == null) {
                continue;
            }

            field.setAccessible(true);
            try {
                if (valorCampo instanceof Date) {
                    field.set(destino, ((Date) valorCampo).toLocalDate());
                } else if (valorCampo instanceof Time) {
                    field.set(destino, ((Time) valorCampo).toLocalTime());
                } else if (valorCampo instanceof Timestamp) {
                    field.set(destino, ((Timestamp) valorCampo).toLocalDateTime());
                } else if (valorCampo instanceof BigDecimal val) {
                    field.set(destino, val.scale() == 0 ? val.longValue() : val);
                } else {
                    field.set(destino, valorCampo);
                }
            } catch (Exception e) {
                System.out.println("Erro ao setar campo " + field.getName() + ": " + ExceptionUtil.getMensagem(e));
            }
        }
    }

    public static Object getValueObjectByFieldName(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    private static boolean paramsCompatible(Class<?> paramentroSet, Class<?> parametroGet) {
        return paramentroSet.equals(Object.class) || parametroGet.equals(Object.class) || paramentroSet.equals(parametroGet);
    }

    public static Type[] getActualTypeArgumentsOfFirstParameter(Method method) {
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        if (genericParameterTypes.length > 0 && genericParameterTypes[0] instanceof ParameterizedType) {
            return ((ParameterizedType) genericParameterTypes[0]).getActualTypeArguments();
        }
        return new Type[0];
    }

    public static List<Field> getAllFieldsIncludeSuperClasses(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null && !clazz.equals(Object.class)) {
            Collections.addAll(fieldList, clazz.getDeclaredFields());
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }

    public static Object getValueFromProperty(Object entidade, String property, boolean obrigatorio) {
        try {
            Object valorRetorno = entidade;
            for (String prop : property.split("\\.")) {
                Method valorMetoGet = getGetter(prop, valorRetorno.getClass());
                if (valorMetoGet == null) {
                    throw new RestWarnException("Propriedade não encontrada: " + prop + " em " + valorRetorno.getClass().getSimpleName() + " (" + property + ")");
                }
                valorRetorno = valorMetoGet.invoke(valorRetorno);
                if (valorRetorno == null && obrigatorio) {
                    throw new RestWarnException("Propriedade com valor nulo: " + prop + " (" + property + ")");
                }
            }
            return valorRetorno;
        } catch (Exception e) {
            throw new RestWarnException(e.getMessage());
        }
    }

    public static boolean canInstantiate(Class<?> clazz) {
        if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers()) || clazz.isEnum() || clazz.isPrimitive() || clazz.isArray() || clazz == Void.TYPE) {
            return false;
        }

        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            return Modifier.isPublic(constructor.getModifiers());
        } catch (NoSuchMethodException e) {
            return false;
        }
    }
}
