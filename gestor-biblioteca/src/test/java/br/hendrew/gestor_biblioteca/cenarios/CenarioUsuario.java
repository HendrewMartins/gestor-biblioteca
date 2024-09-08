package br.hendrew.gestor_biblioteca.cenarios;

import br.hendrew.gestor_biblioteca.dtos.UsuarioDto;
import br.hendrew.gestor_biblioteca.model.Usuario;

import java.time.LocalDate;

public class CenarioUsuario {

    public static Usuario usuario() {
        Usuario usuario = new Usuario();
        usuario.setNome("Nome do Usuário");
        usuario.setEmail("usuario@example.com");
        usuario.setDataCadastro(LocalDate.now());
        usuario.setTelefone("1234567890");
        return usuario;
    }

    public static UsuarioDto usuarioDto() {
        UsuarioDto dto = new UsuarioDto();
        dto.setNome("Nome do Usuário");
        dto.setEmail("usuario@example.com");
        dto.setDataCadastro(LocalDate.now());
        dto.setTelefone("1234567890");
        return dto;
    }

}
