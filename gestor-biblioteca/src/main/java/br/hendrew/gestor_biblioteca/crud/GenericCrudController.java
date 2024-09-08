package br.hendrew.gestor_biblioteca.crud;

import br.hendrew.gestor_biblioteca.exception.TratamentoNotFoundException;
import br.hendrew.gestor_biblioteca.interfaces.Validatable;
import br.hendrew.gestor_biblioteca.utils.generic_reponse.GenericResponse;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class GenericCrudController<T, RS extends GenericCrudService, D extends Validatable> {

    @Getter
    @Autowired
    RS service;

    @GetMapping
    public List<D> get() {
        return service.findAll();
    }

    @GetMapping(path= "{id}")
    public Object getById(@PathVariable(name = "id", required = true) Long id) throws TratamentoNotFoundException  {
        return service.findDtoById(id);
    }

    @PostMapping
    public GenericResponse save(@RequestBody @Valid D genericClass)  {
        return service.save(genericClass);
    }

    @DeleteMapping(path = "{id}")
    public GenericResponse deleteById(@PathVariable(name = "id", required = true) Long id) throws TratamentoNotFoundException {
        return service.deleteById(id);
    }

    @PutMapping(path = "{id}")
    public GenericResponse update(@PathVariable(name = "id", required = true) Long id, @RequestBody @Valid D genericClass) throws TratamentoNotFoundException  {
        return service.save(genericClass);
    }

}