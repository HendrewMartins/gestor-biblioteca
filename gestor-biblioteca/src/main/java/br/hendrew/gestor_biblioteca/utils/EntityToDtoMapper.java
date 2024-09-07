package br.hendrew.gestor_biblioteca.utils;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embeddable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class EntityToDtoMapper {

    public <E, D> D mapEntityToDto(E entity, Class<D> dtoClass) {
        try {
            D dto = dtoClass.getDeclaredConstructor().newInstance();

            Field[] entityFields = entity.getClass().getDeclaredFields();
            Field[] dtoFields = dtoClass.getDeclaredFields();

            for (Field entityField : entityFields) {
                if (walk(entity, entityField, dtoFields, dto)) continue;
            }

            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping entity to DTO", e);
        }
    }

    private <E, D> boolean walk(E entity, Field entityField, Field[] dtoFields, D dto) throws IllegalAccessException {
        if (entityField.isAnnotationPresent(JsonIgnore.class)) return true;
        entityField.setAccessible(true);

        for (Field dtoField : dtoFields) {
            dtoField.setAccessible(true);
            create(entity, entityField, dto, dtoField);
        }
        return false;
    }

    private <E, D> void create(E entity, Field entityField, D dto, Field dtoField) throws IllegalAccessException {
        if (entityField.getName().equals(dtoField.getName())) {
            if (Collection.class.isAssignableFrom(entityField.getType())) {

                Collection<?> entityCollection = (Collection<?>) entityField.get(entity);
                Collection<Object> dtoCollection;

                if (Set.class.isAssignableFrom(dtoField.getType())) {
                    dtoCollection = new HashSet<>();
                } else if (List.class.isAssignableFrom(dtoField.getType())) {
                    dtoCollection = new ArrayList<>();
                } else {
                    throw new RuntimeException("Tipo de coleção desconhecido: " + dtoField.getType());
                }

                if (entityCollection != null) {
                    for (Object item : entityCollection) {
                        if (item.getClass().isEnum()) {
                            dtoCollection.add(item);
                        } else {
                            Object dtoItem = mapEntityToDto(item, item.getClass());
                            dtoCollection.add(dtoItem);
                        }
                    }
                }
                dtoField.set(dto, dtoCollection);
            } else if (entityField.getType().isAnnotationPresent(Embeddable.class)) {
                dtoField.set(dto, mapEntityToDto(entityField.get(entity), dtoField.getType()));
            } else if (entityField.getType().equals(dtoField.getType())) {
                dtoField.set(dto, entityField.get(entity));
            }
        }
    }
}

