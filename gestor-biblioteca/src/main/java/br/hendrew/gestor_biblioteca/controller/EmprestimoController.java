package br.hendrew.gestor_biblioteca.controller;

import br.hendrew.gestor_biblioteca.crud.GenericCrudController;
import br.hendrew.gestor_biblioteca.dtos.EmprestimoDto;
import br.hendrew.gestor_biblioteca.model.Emprestimo;
import br.hendrew.gestor_biblioteca.service.EmprestimoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/emprestimo")
public class EmprestimoController extends GenericCrudController<Emprestimo, EmprestimoService, EmprestimoDto> {

}
