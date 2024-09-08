package br.hendrew.gestor_biblioteca.repository;

import br.hendrew.gestor_biblioteca.cenarios.CenarioLivro;
import br.hendrew.gestor_biblioteca.dtos.LivroDto;
import br.hendrew.gestor_biblioteca.exception.NotFoundException;
import br.hendrew.gestor_biblioteca.model.Livro;
import br.hendrew.gestor_biblioteca.service.LivroService;
import br.hendrew.gestor_biblioteca.utils.generic_reponse.GenericResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LivroRepositoryTest {

    @Autowired
    LivroService livroService;

    @Autowired
    LivroRepository livroRepository;

    @Transactional
    void deleteCenario() {
        livroRepository.deleteAll();
    }

    @Test
    void testSaveLivro_validData() {
        LivroDto dto = CenarioLivro.livroDto();
        GenericResponse response = livroService.save(dto);
        assertNotNull(response);
        assertEquals("Registro(s) salvo(s) com sucesso.", response.getMessage());
        this.deleteCenario();
    }

    @Test
    void testFindById_found() {
        Livro livro = CenarioLivro.livro();
        livroRepository.save(livro);
        Livro result = livroService.findById(livro.getId());
        assertNotNull(result);
        this.deleteCenario();
    }

    @Test
    void testFindById_notFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            livroService.findById(999);
        });
        assertEquals("404 NOT_FOUND \"Nenhum registro encontrado.\"", exception.getMessage());
    }

    @Test
    void testFindAll() {
        LivroDto dto1 = CenarioLivro.livroDto();
        livroService.save(dto1);

        LivroDto dto2 = CenarioLivro.livroDto();
        livroService.save(dto2);
        List<LivroDto> result = livroService.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        this.deleteCenario();
    }

    @Test
    void testFindDtoById_found() {
        Livro livro = CenarioLivro.livro();
        livroRepository.save(livro);
        LivroDto result = livroService.findDtoById(livro.getId());
        assertNotNull(result);
        this.deleteCenario();
    }

    @Test
    void testFindDtoById_notFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            livroService.findDtoById(999);
        });

        assertEquals("404 NOT_FOUND \"Nenhum registro encontrado.\"", exception.getMessage());
    }

    @Test
    void testUpdate() {
        Livro livro = CenarioLivro.livro();
        livroRepository.save(livro);
        LivroDto dto = CenarioLivro.livroDto();
        dto.setId(livro.getId());
        dto.setTitulo("Titulo UPDATE");
        GenericResponse response = livroService.update(dto, dto.getId());
        assertNotNull(response);
        assertEquals("Registro(s) salvo(s) com sucesso.", response.getMessage());
        this.deleteCenario();
    }

    @Test
    void testDeleteById_found() {
        Livro livro = CenarioLivro.livro();
        livroRepository.save(livro);
        GenericResponse response = livroService.deleteById(livro.getId());

        assertNotNull(response);
        assertEquals("Registro(s) deletado(s) com sucesso.", response.getMessage());
        this.deleteCenario();
    }

    @Test
    void testDeleteById_notFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            livroService.deleteById(999);
        });

        assertEquals("404 NOT_FOUND \"Nenhum registro encontrado.\"", exception.getMessage());
    }
}
