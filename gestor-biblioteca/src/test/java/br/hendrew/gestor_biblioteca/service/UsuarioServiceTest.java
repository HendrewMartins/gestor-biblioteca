package br.hendrew.gestor_biblioteca.service;

import br.hendrew.gestor_biblioteca.cenarios.CenarioUsuario;
import br.hendrew.gestor_biblioteca.dtos.UsuarioDto;
import br.hendrew.gestor_biblioteca.exception.NotFoundException;
import br.hendrew.gestor_biblioteca.exception.ValidationException;
import br.hendrew.gestor_biblioteca.model.Usuario;
import br.hendrew.gestor_biblioteca.repository.UsuarioRepository;
import br.hendrew.gestor_biblioteca.utils.generic_reponse.GenericResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioServiceTest {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Transactional
    void deleteCenario() {
        usuarioRepository.deleteAll();
    }

    @Test
    void testSaveUsuario_valid() {
        UsuarioDto dto = CenarioUsuario.usuarioDto();
        GenericResponse response = usuarioService.save(dto);
        assertNotNull(response);
        assertEquals("Registro(s) salvo(s) com sucesso.", response.getMessage());
        this.deleteCenario();
    }

    @Test
    void testValidateUsuarioDto_validDataUsuario() {
        UsuarioDto dto = new UsuarioDto();
        dto.setDataCadastro(LocalDate.now());
        dto.setEmail("email@email.com.br");
        assertDoesNotThrow(dto::validate);
    }

    @Test
    void testValidateUsuarioDto_invalidDataUsuario() {
        UsuarioDto dto = new UsuarioDto();
        dto.setDataCadastro(LocalDate.now().plusDays(1));
        ValidationException exception = assertThrows(ValidationException.class, dto::validate);
        assertEquals("Data de cadastro não pode ser maior que a data atual!", exception.getMessage());
    }

    @Test
    void testValidateUsuarioDto_validEmail() {
        UsuarioDto dto = new UsuarioDto();
        dto.setEmail("email@email.com.br");
        assertDoesNotThrow(dto::validate);
    }

    @Test
    void testValidateUsuarioDto_invalidEmail() {
        UsuarioDto dto = new UsuarioDto();
        dto.setEmail("email.com.br");
        ValidationException exception = assertThrows(ValidationException.class, dto::validate);
        assertEquals("E-mail inválido: " + dto.getEmail() + "!", exception.getMessage());
    }

    @Test
    void testFindById_found() {
        Usuario usuario = CenarioUsuario.usuario();
        usuarioRepository.save(usuario);
        Usuario result = usuarioService.findById(usuario.getId());
        assertNotNull(result);
        this.deleteCenario();
    }

    @Test
    void testFindById_notFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            usuarioService.findById(999);
        });
        assertEquals("404 NOT_FOUND \"Nenhum registro encontrado.\"", exception.getMessage());
    }

    @Test
    void testFindAll() {
        UsuarioDto dto1 = CenarioUsuario.usuarioDto();
        usuarioService.save(dto1);

        UsuarioDto dto2 = CenarioUsuario.usuarioDto();
        usuarioService.save(dto2);
        List<UsuarioDto> result = usuarioService.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        this.deleteCenario();
    }

    @Test
    void testFindDtoById_found() {
        Usuario usuario = CenarioUsuario.usuario();
        usuarioRepository.save(usuario);
        UsuarioDto result = usuarioService.findDtoById(usuario.getId());
        assertNotNull(result);
        this.deleteCenario();
    }

    @Test
    void testFindDtoById_notFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            usuarioService.findDtoById(999);
        });

        assertEquals("404 NOT_FOUND \"Nenhum registro encontrado.\"", exception.getMessage());
    }

    @Test
    void testUpdate() {
        Usuario usuario = CenarioUsuario.usuario();
        usuarioRepository.save(usuario);
        UsuarioDto dto = CenarioUsuario.usuarioDto();
        dto.setId(usuario.getId());
        dto.setNome("Nome Update");
        GenericResponse response = usuarioService.update(dto, dto.getId());

        assertNotNull(response);
        assertEquals("Registro(s) salvo(s) com sucesso.", response.getMessage());
        this.deleteCenario();
    }

    @Test
    void testDeleteById_found() {
        Usuario usuario = CenarioUsuario.usuario();
        usuarioRepository.save(usuario);
        GenericResponse response = usuarioService.deleteById(usuario.getId());

        assertNotNull(response);
        assertEquals("Registro(s) deletado(s) com sucesso.", response.getMessage());
        this.deleteCenario();
    }

    @Test
    void testDeleteById_notFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            usuarioService.deleteById(999);
        });

        assertEquals("404 NOT_FOUND \"Nenhum registro encontrado.\"", exception.getMessage());
    }
}
