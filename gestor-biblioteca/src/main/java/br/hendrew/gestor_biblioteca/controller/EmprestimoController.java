package br.hendrew.gestor_biblioteca.controller;

import br.hendrew.gestor_biblioteca.crud.GenericCrudController;
import br.hendrew.gestor_biblioteca.dtos.EmprestimoDto;
import br.hendrew.gestor_biblioteca.model.Emprestimo;
import br.hendrew.gestor_biblioteca.service.EmprestimoService;
import br.hendrew.gestor_biblioteca.utils.generic_reponse.GenericResponse;
import jakarta.validation.ValidationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/emprestimo")
public class EmprestimoController extends GenericCrudController<Emprestimo, EmprestimoService, EmprestimoDto> {

    @Override
    public GenericResponse save(EmprestimoDto genericClass) {
        if(getService().livroEmprestado(genericClass.getLivro().getId()))
            throw new ValidationException("Livro " + genericClass.getLivro().getTitulo() + " já se encontra emprestado!");
        return super.save(genericClass);
    }
}
