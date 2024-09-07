package br.hendrew.gestor_biblioteca.crud;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
public abstract class GenericCrudService <T, ID, R extends JpaRepository<T, ID>> {

    @Getter
    @Autowired
    R repository;

    private static final String MESSAGE_NOT_FOUND = "Nenhum registro encontrado.";
    private static final String MESSAGE_SUCCESS = "Registro(s) salvo(s) com sucesso.";
    private static final String MESSAGE_DELETE = "Registro(s) deletado(s) com sucesso.";

    public List<T> findAll() {
        return repository.listAll();
    }

    public List<T> findAllWithOrderBy(Boolean asc, String... orders) {
        return repository.findAll(asc, orders);
    }

    public GenericFormResponse<T> findAllGenericForm() {
        return getGenericFormResponse(findAll());
    }

    public GenericFormResponse<T> findAllGenericForm(Boolean asc, String... orders) {
        return getGenericFormResponse(findAllWithOrderBy(asc, orders));
    }

    public GenericFormResponse<T> getGenericFormResponse(List list) {
        if (list.isEmpty())
            throwNotFoundException();

        GenericFormResponse<T> genericFormResponse = new GenericFormResponse<>();
        genericFormResponse.setLista(list);

        return genericFormResponse;
    }

    public GenericResponse saveList(List<T> list) {

        for (T genericForm : list) {
            repository.merge(genericForm);
        }

        return GenericResponse.getGenericResponse(MESSAGE_SUCCESS, Response.Status.OK.getStatusCode());
    }

    public GenericResponse save(T genericClass) {
        repository.persist(genericClass);
        return GenericResponse.getGenericResponse(MESSAGE_SUCCESS, Response.Status.OK.getStatusCode());
    }

    public GenericResponse update(T genericClass) {
        repository.merge(genericClass);
        return GenericResponse.getGenericResponse(MESSAGE_SUCCESS, Response.Status.OK.getStatusCode());
    }

    public T findById(ID id) {
        Optional<T> genericClass = Optional.ofNullable(repository.findById(id));
        if (!genericClass.isPresent())
            throw new NotFoundException(MESSAGE_NOT_FOUND);

        return genericClass.get();
    }

    public GenericResponse deleteById(ID id) {
        if (!repository.deleteById(id))
            throwNotFoundException();

        return GenericResponse.getGenericResponse(MESSAGE_DELETE, Response.Status.OK.getStatusCode());
    }

    public void throwNotFoundException() {
        throw new NotFoundException(MESSAGE_NOT_FOUND);
    }

    public void throwNotFoundException(String message) {
        throw new NotFoundException(message);
    }
}
