package br.hendrew.gestor_biblioteca.repository;

import br.hendrew.gestor_biblioteca.cenarios.CenarioUsuario;
import br.hendrew.gestor_biblioteca.model.Usuario;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;


    @Transactional
    void deleteCenario() {
        usuarioRepository.deleteAll();
    }

    @Test
    void testSave_valid() {
        Usuario usuario = CenarioUsuario.usuario();
        usuarioRepository.saveAndFlush(usuario);
        assertNotNull(usuario.getId());
        this.deleteCenario();
    }

    @Test
    void testFindById_found() {
        Usuario usuario = CenarioUsuario.usuario();
        usuarioRepository.saveAndFlush(usuario);
        Optional<Usuario> result = usuarioRepository.findById(usuario.getId());
        assertNotNull(result);
        assertTrue(result.isPresent());
        this.deleteCenario();
    }

    @Test
    void testFindById_notFound() {
        Optional<Usuario> optional = usuarioRepository.findById(999);
        assertFalse(optional.isPresent());
    }

    @Test
    void testFindAll() {
        Usuario dto1 = CenarioUsuario.usuario();
        usuarioRepository.saveAndFlush(dto1);

        Usuario dto2 = CenarioUsuario.usuario();
        usuarioRepository.saveAndFlush(dto2);
        List<Usuario> result = usuarioRepository.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        this.deleteCenario();
    }

    @Test
    void testUpdate() {
        Usuario usuario = CenarioUsuario.usuario();
        usuarioRepository.saveAndFlush(usuario);
        usuario.setNome("Nome update");
        usuarioRepository.save(usuario);
        Optional<Usuario> result = usuarioRepository.findById(usuario.getId());
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals("Nome update", result.get().getNome());
        this.deleteCenario();
    }

    @Test
    void testDeleteById_found() {
        Usuario usuario = CenarioUsuario.usuario();
        usuarioRepository.save(usuario);
        assertTrue(usuarioRepository.findById(usuario.getId()).isPresent());
        usuarioRepository.deleteById(usuario.getId());
        assertFalse(usuarioRepository.findById(usuario.getId()).isPresent());
        this.deleteCenario();
    }
}
