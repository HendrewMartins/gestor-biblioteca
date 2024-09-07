package br.hendrew.gestor_biblioteca.utils;

import jakarta.persistence.Embeddable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class DtoToEntityMapper {
    public <D, E> E mapDtoToEntity(D dto, Class<E> entityClass) {
        try {
            E entity = entityClass.getDeclaredConstructor().newInstance();

            Field[] dtoFields = dto.getClass().getDeclaredFields();
            Field[] entityFields = entityClass.getDeclaredFields();

            for (Field dtoField : dtoFields) {
                dtoField.setAccessible(true);

                walkDto(dto, dtoField, entityFields, entity);
            }

            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping DTO to entity", e);
        }
    }

    private <D, E> void walkDto(D dto, Field dtoField, Field[] entityFields, E entity) throws IllegalAccessException {
        for (Field entityField : entityFields) {
            entityField.setAccessible(true);

            creatEntity(dto, dtoField, entity, entityField);
        }
    }

    private <D, E> void creatEntity(D dto, Field dtoField, E entity, Field entityField) throws IllegalAccessException {
        if (dtoField.getName().equals(entityField.getName())) {
            if (Collection.class.isAssignableFrom(dtoField.getType())) {

                Collection<?> dtoCollection = (Collection<?>) dtoField.get(dto);
                Collection<Object> entityCollection;

                if (Set.class.isAssignableFrom(entityField.getType())) {
                    entityCollection = new HashSet<>();
                } else if (List.class.isAssignableFrom(entityField.getType())) {
                    entityCollection = new ArrayList<>();
                } else {
                    throw new RuntimeException("Tipo de coleção desconhecido: " + entityField.getType());
                }

                if (dtoCollection != null) {
                    for (Object item : dtoCollection) {
                        if (item.getClass().isEnum()) {
                            entityCollection.add(item);
                        } else {
                            Object entityItem = mapDtoToEntity(item, entityField.getType());
                            entityCollection.add(entityItem);
                        }
                    }
                }
                entityField.set(entity, entityCollection);
            } else if (entityField.getType().isAnnotationPresent(Embeddable.class)) {
                entityField.set(entity, mapDtoToEntity(dtoField.get(dto), entityField.getType()));
            } else if (dtoField.getType().equals(entityField.getType())) {
                entityField.set(entity, dtoField.get(dto));
            }
        }
    }
}
