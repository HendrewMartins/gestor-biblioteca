package br.hendrew.gestor_biblioteca.controller;

import br.hendrew.gestor_biblioteca.crud.GenericCrudController;
import br.hendrew.gestor_biblioteca.dtos.UsuarioDto;
import br.hendrew.gestor_biblioteca.model.Usuario;
import br.hendrew.gestor_biblioteca.service.UsuarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/usuario")
public class UsuarioController extends GenericCrudController<Usuario, UsuarioService, UsuarioDto> {
}
