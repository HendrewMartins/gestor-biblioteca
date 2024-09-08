package br.hendrew.gestor_biblioteca.utils.translate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReflectionUtil {

    public static List<Field> getAllFieldsIncludeSuperClasses(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null && !clazz.equals(Object.class)) {
            Collections.addAll(fieldList, clazz.getDeclaredFields());
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }
}
