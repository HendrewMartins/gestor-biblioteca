package br.hendrew.gestor_biblioteca.repository;

import br.hendrew.gestor_biblioteca.cenarios.CenarioEmprestimo;
import br.hendrew.gestor_biblioteca.enums.Status;
import br.hendrew.gestor_biblioteca.model.Emprestimo;
import br.hendrew.gestor_biblioteca.model.Livro;
import br.hendrew.gestor_biblioteca.model.Usuario;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmprestimoRepositoryTest {

    @Autowired
    EmprestimoRepository emprestimoRepository;

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
    void testSave_valid() {
        this.createCenario();
        Emprestimo emprestimo = CenarioEmprestimo.getEmprestimo(this.livro.getId(), this.usuario.getId());
        emprestimoRepository.saveAndFlush(emprestimo);
        assertNotNull(emprestimo.getId());
        this.deleteCenario();
    }

    @Test
    void testFindById_found() {
        this.createCenario();
        Emprestimo emprestimo = CenarioEmprestimo.getEmprestimo(this.livro.getId(), this.usuario.getId());
        emprestimoRepository.saveAndFlush(emprestimo);
        Optional<Emprestimo> result = emprestimoRepository.findById(emprestimo.getId());
        assertNotNull(result);
        assertTrue(result.isPresent());
        this.deleteCenario();
    }

    @Test
    void testFindById_notFound() {
        Optional<Emprestimo> optional = emprestimoRepository.findById(999);
        assertFalse(optional.isPresent());
    }

    @Test
    void testFindAll() {
        this.createCenario();
        Emprestimo dto1 = CenarioEmprestimo.getEmprestimo(this.livro.getId(), this.usuario.getId());
        emprestimoRepository.saveAndFlush(dto1);

        this.createCenario();
        Emprestimo dto2 = CenarioEmprestimo.getEmprestimo(this.livro.getId(), this.usuario.getId());
        emprestimoRepository.saveAndFlush(dto2);
        List<Emprestimo> result = emprestimoRepository.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        this.deleteCenario();
    }

    @Test
    void testUpdate() {
        this.createCenario();
        Emprestimo emprestimo = CenarioEmprestimo.getEmprestimo(this.livro.getId(), this.usuario.getId());
        emprestimoRepository.saveAndFlush(emprestimo);
        emprestimo.setDataDevolucao(LocalDate.now());
        emprestimo.setStatus(Status.BAIXADO);
        emprestimoRepository.save(emprestimo);
        Optional<Emprestimo> result = emprestimoRepository.findById(emprestimo.getId());
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(Status.BAIXADO, result.get().getStatus());
        this.deleteCenario();
    }

    @Test
    void testDeleteById_found() {
        this.createCenario();
        Emprestimo emprestimo = CenarioEmprestimo.getEmprestimo(this.livro.getId(), this.usuario.getId());
        emprestimoRepository.save(emprestimo);
        assertTrue(emprestimoRepository.findById(emprestimo.getId()).isPresent());
        emprestimoRepository.deleteById(emprestimo.getId());
        assertFalse(emprestimoRepository.findById(emprestimo.getId()).isPresent());
        this.deleteCenario();
    }

}
