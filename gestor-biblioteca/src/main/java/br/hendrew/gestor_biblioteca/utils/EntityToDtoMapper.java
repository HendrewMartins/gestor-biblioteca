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

