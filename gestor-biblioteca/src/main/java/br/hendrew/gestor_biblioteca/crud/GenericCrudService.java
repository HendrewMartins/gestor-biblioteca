package br.hendrew.gestor_biblioteca.crud;

import br.hendrew.gestor_biblioteca.exception.NotFoundException;
import br.hendrew.gestor_biblioteca.interfaces.Validatable;
import br.hendrew.gestor_biblioteca.utils.DtoToEntityMapper;
import br.hendrew.gestor_biblioteca.utils.EntityToDtoMapper;
import br.hendrew.gestor_biblioteca.utils.generic_reponse.GenericFormResponse;
import br.hendrew.gestor_biblioteca.utils.generic_reponse.GenericResponse;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
public abstract class GenericCrudService <T, ID, R extends JpaRepository<T, ID>, D extends Validatable> {

    @Getter
    @Autowired
    R repository;

    @Getter
    @Autowired
    EntityManager entityManager;

    @Autowired
    EntityToDtoMapper entityToDtoMapper;

    @Autowired
    DtoToEntityMapper dtoToEntityMapper;

    private Class<D> dtoClass;

    private Class<T> entityClass;

    public GenericCrudService(Class<D> dtoClass, Class<T> entityClass) {
        this.dtoClass = dtoClass;
        this.entityClass = entityClass;
    }

    private static final String MESSAGE_NOT_FOUND = "Nenhum registro encontrado.";
    private static final String MESSAGE_SUCCESS = "Registro(s) salvo(s) com sucesso.";
    private static final String MESSAGE_DELETE = "Registro(s) deletado(s) com sucesso.";

    public List<D> findAll() {
        List<T> list = repository.findAll();
        return getListDto(list);
    }

    private List<D> getListDto(List<T> list) {
        List<D> listDto = new ArrayList<>();
        for(T item : list) {
            listDto.add(getDto(item));
        }
        return listDto;
    }

    private D getDto(T item) {
        return entityToDtoMapper.mapEntityToDto(item, dtoClass);
    }

    public List<D> findAllWithOrderBy(Sort sorts) {
        List<T> list = repository.findAll(sorts);
        return getListDto(list);
    }

    public GenericFormResponse<D> findAllGenericForm() {
        List<T> list = repository.findAll();
        return getGenericFormResponse(getListDto(list));
    }

    public GenericFormResponse<D> findAllGenericForm(Sort sorts) {
        return getGenericFormResponse(findAllWithOrderBy(sorts));
    }

    public GenericFormResponse<D> getGenericFormResponse(List list) {
        if (list.isEmpty())
            throwNotFoundException();

        GenericFormResponse<D> genericFormResponse = new GenericFormResponse<>();
        genericFormResponse.setLista(list);

        return genericFormResponse;
    }

    private T getEntity(D dto) {
        return dtoToEntityMapper.mapDtoToEntity(dto, entityClass);
    }

    public GenericResponse saveList(List<D> list) {

        for (D genericForm : list) {
            repository.saveAndFlush(getEntity(genericForm));
        }

        return GenericResponse.getGenericResponse(MESSAGE_SUCCESS, HttpStatus.OK.value());
    }

    public GenericResponse save(D genericClass) {
        genericClass.validate();
        repository.saveAndFlush(getEntity(genericClass));
        return GenericResponse.getGenericResponse(MESSAGE_SUCCESS, HttpStatus.OK.value());
    }

    public GenericResponse update(D genericClass, Integer id) {
        genericClass.validate();
        repository.save(getEntity(genericClass));
        return GenericResponse.getGenericResponse(MESSAGE_SUCCESS, HttpStatus.OK.value());
    }

    public T findById(ID id) {
        Optional<T> optionalEntity = repository.findById(id);
        if (!optionalEntity.isPresent()) {
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
        return optionalEntity.get();
    }

    public D findDtoById(ID id) {
        Optional<T> optionalEntity = repository.findById(id);
        if (!optionalEntity.isPresent()) {
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
        return getDto(optionalEntity.get());
    }

    public GenericResponse deleteById(ID id) {
        if (!repository.existsById(id))
            throwNotFoundException();

        repository.deleteById(id);

        return GenericResponse.getGenericResponse(MESSAGE_DELETE, HttpStatus.OK.value());
    }

    public void throwNotFoundException() {
        throw new NotFoundException(MESSAGE_NOT_FOUND);
    }

    public void throwNotFoundException(String message) {
        throw new NotFoundException(message);
    }
}
