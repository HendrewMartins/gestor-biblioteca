package br.hendrew.gestor_biblioteca.repository;

import br.hendrew.gestor_biblioteca.cenarios.CenarioLivro;
import br.hendrew.gestor_biblioteca.model.Livro;
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
public class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;


    @Transactional
    void deleteCenario() {
        livroRepository.deleteAll();
    }

    @Test
    void testSave_valid() {
        Livro livro = CenarioLivro.livro();
        livroRepository.saveAndFlush(livro);
        assertNotNull(livro.getId());
        this.deleteCenario();
    }

    @Test
    void testFindById_found() {
        Livro livro = CenarioLivro.livro();
        livroRepository.saveAndFlush(livro);
        Optional<Livro> result = livroRepository.findById(livro.getId());
        assertNotNull(result);
        assertTrue(result.isPresent());
        this.deleteCenario();
    }

    @Test
    void testFindById_notFound() {
        Optional<Livro> optional = livroRepository.findById(999);
        assertFalse(optional.isPresent());
    }

    @Test
    void testFindAll() {
        Livro dto1 = CenarioLivro.livro();
        livroRepository.saveAndFlush(dto1);

        Livro dto2 = CenarioLivro.livro();
        livroRepository.saveAndFlush(dto2);
        List<Livro> result = livroRepository.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        this.deleteCenario();
    }

    @Test
    void testUpdate() {
        Livro livro = CenarioLivro.livro();
        livroRepository.saveAndFlush(livro);
        livro.setTitulo("Titulo update");
        livroRepository.save(livro);
        Optional<Livro> result = livroRepository.findById(livro.getId());
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals("Titulo update", result.get().getTitulo());
        this.deleteCenario();
    }

    @Test
    void testDeleteById_found() {
        Livro livro = CenarioLivro.livro();
        livroRepository.save(livro);
        assertTrue(livroRepository.findById(livro.getId()).isPresent());
        livroRepository.deleteById(livro.getId());
        assertFalse(livroRepository.findById(livro.getId()).isPresent());
        this.deleteCenario();
    }
}
