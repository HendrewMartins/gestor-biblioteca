package br.hendrew.gestor_biblioteca.utils;

import br.hendrew.gestor_biblioteca.annotation.QueryDtoMapper;
import br.hendrew.gestor_biblioteca.annotation.TransientDTO;
import br.hendrew.gestor_biblioteca.utils.translate.ReflectionUtil;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import jakarta.persistence.Tuple;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TupleFactory<T> {

    private Field[] getFieldsToDto(Class<T> clazz) {
        QueryDtoMapper queryToDTO = clazz.getAnnotation(QueryDtoMapper.class);
        if (queryToDTO != null && queryToDTO.includeSuperClasses()) {
            return ReflectionUtil.getAllFieldsIncludeSuperClasses(clazz).toArray(new Field[0]);
        }
        return clazz.getDeclaredFields();
    }

    public List<T> toDTO(NativeQuery query, Class<T> clazz) throws InstantiationException, IllegalAccessException {
        HashMap<String, Boolean> propriedadeTransient = new HashMap<>();
        HashMap<String, String> propriedadeEnum = new HashMap<>();
        Field[] fieldsClass = getFieldsToDto(clazz);

        for (Field declaredField : fieldsClass) {
            if (isTransient(declaredField)) {
                propriedadeTransient.put(declaredField.getName().toLowerCase(), true);
            } else {
                handleEnumeratedField(query, declaredField, propriedadeEnum);
            }
        }

        List<T> list = new ArrayList<>();
        List<Tuple> resultList = query.getResultList();

        for (Tuple tuple : resultList) {
            T instance = clazz.newInstance();
            populateInstanceFromTuple(instance, fieldsClass, tuple, propriedadeTransient, propriedadeEnum);
            list.add(instance);
        }

        return list;
    }

    private void handleEnumeratedField(NativeQuery query, Field field, HashMap<String, String> propriedadeEnum) {
        Enumerated annotationEnum = field.getAnnotation(Enumerated.class);
        if (annotationEnum != null) {
            if (annotationEnum.value().equals(EnumType.ORDINAL)) {
                query.addScalar(field.getName(), StandardBasicTypes.INTEGER);
                propriedadeEnum.put(field.getName().toLowerCase(), EnumType.ORDINAL.name());
            } else {
                query.addScalar(field.getName(), StandardBasicTypes.STRING);
                propriedadeEnum.put(field.getName().toLowerCase(), EnumType.STRING.name());
            }
        } else {
            montarQueryScalar(field, query);
        }
    }

    private void populateInstanceFromTuple(T instance, Field[] fields, Tuple tuple,
                                           HashMap<String, Boolean> propriedadeTransient,
                                           HashMap<String, String> propriedadeEnum) throws IllegalAccessException {
        for (Field field : fields) {
            if (!propriedadeTransient.containsKey(field.getName().toLowerCase())) {
                field.setAccessible(true);
                String enumType = propriedadeEnum.get(field.getName().toLowerCase());
                if (enumType == null) {
                    field.set(instance, tuple.get(field.getName()));
                } else {
                    handleEnumField(instance, field, tuple, enumType);
                }
            }
        }
    }

    private void handleEnumField(T instance, Field field, Tuple tuple, String enumType) throws IllegalAccessException {
        if (enumType.equals(EnumType.ORDINAL.name())) {
            Integer position = (Integer) tuple.get(field.getName());
            if (position != null && field.getType().getEnumConstants().length > position) {
                field.set(instance, field.getType().getEnumConstants()[position]);
            }
        } else if (enumType.equals(EnumType.STRING.name())) {
            Integer position = getEnumByName(field.getType().getEnumConstants(), (String) tuple.get(field.getName()));
            if (position != null && field.getType().getEnumConstants().length > position) {
                field.set(instance, field.getType().getEnumConstants()[position]);
            }
        }
    }

    private boolean isTransient(Field field) {
        return field.isAnnotationPresent(Transient.class) || field.isAnnotationPresent(TransientDTO.class);
    }

    private Integer getEnumByName(Object[] enums, String name) {
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].toString().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return null;
    }

    private void montarQueryScalar(Field field, NativeQuery query) {
        Class<?> fieldType = field.getType();
        if (fieldType.equals(BigDecimal.class)) {
            query.addScalar(field.getName(), StandardBasicTypes.BIG_DECIMAL);
        } else if (fieldType.equals(Integer.class)) {
            query.addScalar(field.getName(), StandardBasicTypes.INTEGER);
        } else if (fieldType.equals(Short.class)) {
            query.addScalar(field.getName(), StandardBasicTypes.SHORT);
        } else if (fieldType.equals(Long.class)) {
            query.addScalar(field.getName(), StandardBasicTypes.LONG);
        } else if (fieldType.equals(LocalDate.class)) {
            query.addScalar(field.getName(), StandardBasicTypes.LOCAL_DATE);
        } else if (fieldType.equals(LocalDateTime.class)) {
            query.addScalar(field.getName(), StandardBasicTypes.LOCAL_DATE_TIME);
        } else if (fieldType.equals(LocalTime.class)) {
            query.addScalar(field.getName(), StandardBasicTypes.LOCAL_TIME);
        } else if (fieldType.equals(Double.class)) {
            query.addScalar(field.getName(), StandardBasicTypes.DOUBLE);
        } else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
            query.addScalar(field.getName(), StandardBasicTypes.BOOLEAN);
        } else {
            query.addScalar(field.getName(), StandardBasicTypes.STRING);
        }
    }
}
