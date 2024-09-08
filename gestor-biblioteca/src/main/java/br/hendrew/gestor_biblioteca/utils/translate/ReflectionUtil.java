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

    public static List<Field> getAllFieldsIncludeSuperClasses(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null && !clazz.equals(Object.class)) {
            Collections.addAll(fieldList, clazz.getDeclaredFields());
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }
}
