package br.hendrew.gestor_biblioteca.crud;

import br.hendrew.gestor_biblioteca.exception.NotFoundException;
import br.hendrew.gestor_biblioteca.utils.generic_reponse.GenericFormResponse;
import br.hendrew.gestor_biblioteca.utils.generic_reponse.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
        return repository.findAll();
    }

    public List<T> findAllWithOrderBy(Sort sorts) {
        return repository.findAll(sorts);
    }

    public GenericFormResponse<T> findAllGenericForm() {
        return getGenericFormResponse(findAll());
    }

    public GenericFormResponse<T> findAllGenericForm(Sort sorts) {
        return getGenericFormResponse(findAllWithOrderBy(sorts));
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
            repository.saveAndFlush(genericForm);
        }

        return GenericResponse.getGenericResponse(MESSAGE_SUCCESS, 200);
    }

    public GenericResponse save(T genericClass) {
        repository.saveAndFlush(genericClass);
        return GenericResponse.getGenericResponse(MESSAGE_SUCCESS, 200);
    }

    public GenericResponse update(T genericClass) {
        repository.save(genericClass);
        return GenericResponse.getGenericResponse(MESSAGE_SUCCESS, 200);
    }

    public T findById(ID id) {
        Optional<T> optionalEntity = repository.findById(id);
        if (!optionalEntity.isPresent()) {
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
        return optionalEntity.get();
    }

    public GenericResponse deleteById(ID id) {
        if (!repository.existsById(id))
            throwNotFoundException();

        repository.deleteById(id);

        return GenericResponse.getGenericResponse(MESSAGE_DELETE, 200);
    }

    public void throwNotFoundException() {
        throw new NotFoundException(MESSAGE_NOT_FOUND);
    }

    public void throwNotFoundException(String message) {
        throw new NotFoundException(message);
    }
}
