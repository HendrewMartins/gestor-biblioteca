package br.hendrew.gestor_biblioteca.controller;

import br.hendrew.gestor_biblioteca.crud.CrudController;
import br.hendrew.gestor_biblioteca.dtos.LivroDto;
import br.hendrew.gestor_biblioteca.model.Livro;
import br.hendrew.gestor_biblioteca.service.LivroService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/livro")
public class LivroController extends CrudController<Livro, LivroService, LivroDto> {
}
