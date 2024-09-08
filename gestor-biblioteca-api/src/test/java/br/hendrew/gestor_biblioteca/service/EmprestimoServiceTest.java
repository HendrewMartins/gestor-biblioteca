package br.hendrew.gestor_biblioteca.service;

import br.hendrew.gestor_biblioteca.cenarios.CenarioEmprestimo;
import br.hendrew.gestor_biblioteca.dtos.EmprestimoDto;
import br.hendrew.gestor_biblioteca.enums.Status;
import br.hendrew.gestor_biblioteca.exception.NotFoundException;
import br.hendrew.gestor_biblioteca.exception.ValidationException;
import br.hendrew.gestor_biblioteca.model.Emprestimo;
import br.hendrew.gestor_biblioteca.model.Livro;
import br.hendrew.gestor_biblioteca.model.Usuario;
import br.hendrew.gestor_biblioteca.repository.EmprestimoRepository;
import br.hendrew.gestor_biblioteca.repository.LivroRepository;
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

@SpringBootTest
@AutoConfigureMockMvc
class EmprestimoServiceTest {

    @Autowired
    EmprestimoRepository emprestimoRepository;

    @Autowired
    EmprestimoService emprestimoService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    LivroRepository livroRepository;

    private Livro livro;
    private Usuario usuario;

    @Transactional
    void createCenario() {
        this.livro = livroRepository.save(CenarioEmprestimo.livro());
        this.usuario = usuarioRepository.save(CenarioEmprestimo.usuario());
    }

    @Transactional
    void deleteCenario() {
        emprestimoRepository.deleteAll();
        livroRepository.deleteAll();
        usuarioRepository.deleteAll();
    }

    @Test
    void testSaveEmprestimo_validData() {
        this.createCenario();
        EmprestimoDto dto = CenarioEmprestimo.getEmprestimoDto(this.livro.getId(), this.usuario.getId());
        GenericResponse response = emprestimoService.save(dto);
        assertNotNull(response);
        assertEquals("Registro(s) salvo(s) com sucesso.", response.getMessage());
        this.deleteCenario();
    }


    @Test
    void testSaveEmprestimo_livroEmprestado() {
        this.createCenario();
        EmprestimoDto dto = CenarioEmprestimo.getEmprestimoDto(this.livro.getId(), this.usuario.getId());
        emprestimoService.save(dto);
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            emprestimoService.save(dto);
        });
        assertEquals("Livro " + dto.getLivro().getTitulo() + " já se encontra emprestado!", exception.getMessage());
        this.deleteCenario();
    }

    @Test
    void testValidateEmprestimoDto_validData() {
        EmprestimoDto dto = new EmprestimoDto();
        dto.setDataEmprestimo(LocalDate.now());
        dto.setStatus(Status.ATIVO);
        assertDoesNotThrow(dto::validate);
    }

    @Test
    void testValidateEmprestimoDto_invalidDataEmprestimo() {
        EmprestimoDto dto = new EmprestimoDto();
        dto.setDataEmprestimo(LocalDate.now().plusDays(1));
        dto.setStatus(Status.ATIVO);
        ValidationException exception = assertThrows(ValidationException.class, dto::validate);
        assertEquals("Data de emprestimo não pode ser maior que a data atual!", exception.getMessage());
    }

    @Test
    void testLivroEmprestado() {
        this.createCenario();
        EmprestimoDto dto = CenarioEmprestimo.getEmprestimoDto(this.livro.getId(), this.usuario.getId());
        emprestimoService.save(dto);
        Boolean result = emprestimoService.livroEmprestado(dto.getLivro().getId());
        assertTrue(result);
        this.deleteCenario();
    }

    @Test
    void testFindById_found() {
        this.createCenario();
        Emprestimo emprestimo = CenarioEmprestimo.getEmprestimo(this.livro.getId(), this.usuario.getId());
        emprestimoRepository.save(emprestimo);
        Emprestimo result = emprestimoService.findById(emprestimo.getId());
        assertNotNull(result);
        this.deleteCenario();
    }

    @Test
    void testFindById_notFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            emprestimoService.findById(999);
        });
        assertEquals("404 NOT_FOUND \"Nenhum registro encontrado.\"", exception.getMessage());
    }

    @Test
    void testFindAll() {
        this.createCenario();
        EmprestimoDto dto1 = CenarioEmprestimo.getEmprestimoDto(this.livro.getId(), this.usuario.getId());
        emprestimoService.save(dto1);

        this.createCenario();
        EmprestimoDto dto2 = CenarioEmprestimo.getEmprestimoDto(this.livro.getId(), this.usuario.getId());
        emprestimoService.save(dto2);
        List<EmprestimoDto> result = emprestimoService.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        this.deleteCenario();
    }

    @Test
    void testFindDtoById_found() {
        this.createCenario();
        Emprestimo emprestimo = CenarioEmprestimo.getEmprestimo(this.livro.getId(), this.usuario.getId());
        emprestimoRepository.save(emprestimo);
        EmprestimoDto result = emprestimoService.findDtoById(emprestimo.getId());
        assertNotNull(result);
        this.deleteCenario();
    }

    @Test
    void testFindDtoById_notFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            emprestimoService.findDtoById(999);
        });

        assertEquals("404 NOT_FOUND \"Nenhum registro encontrado.\"", exception.getMessage());
    }

    @Test
    void testUpdate() {
        this.createCenario();
        Emprestimo emprestimo = CenarioEmprestimo.getEmprestimo(this.livro.getId(), this.usuario.getId());
        emprestimoRepository.save(emprestimo);
        EmprestimoDto dto = CenarioEmprestimo.getEmprestimoDto(this.livro.getId(), this.usuario.getId());
        dto.setId(emprestimo.getId());
        dto.setDataDevolucao(LocalDate.now());
        dto.setStatus(Status.BAIXADO);
        GenericResponse response = emprestimoService.update(dto, dto.getId());

        assertNotNull(response);
        assertEquals("Registro(s) salvo(s) com sucesso.", response.getMessage());
        this.deleteCenario();
    }

    @Test
    void testDeleteById_found() {
        this.createCenario();
        Emprestimo emprestimo = CenarioEmprestimo.getEmprestimo(this.livro.getId(), this.usuario.getId());
        emprestimoRepository.save(emprestimo);
        GenericResponse response = emprestimoService.deleteById(emprestimo.getId());

        assertNotNull(response);
        assertEquals("Registro(s) deletado(s) com sucesso.", response.getMessage());
        this.deleteCenario();
    }

    @Test
    void testDeleteById_notFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            emprestimoService.deleteById(999);
        });

        assertEquals("404 NOT_FOUND \"Nenhum registro encontrado.\"", exception.getMessage());
    }
}
