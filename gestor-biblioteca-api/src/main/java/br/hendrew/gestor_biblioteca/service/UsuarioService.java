package br.hendrew.gestor_biblioteca.service;

import br.hendrew.gestor_biblioteca.crud.GenericCrudService;
import br.hendrew.gestor_biblioteca.dtos.UsuarioDto;
import br.hendrew.gestor_biblioteca.model.Usuario;
import br.hendrew.gestor_biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends GenericCrudService<Usuario, Integer, UsuarioRepository, UsuarioDto> {

    public UsuarioService() {
        super(UsuarioDto.class, Usuario.class);
    }
}
