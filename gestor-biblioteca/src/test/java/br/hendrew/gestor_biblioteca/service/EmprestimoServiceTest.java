package br.hendrew.gestor_biblioteca.service;

import br.hendrew.gestor_biblioteca.dtos.EmprestimoDto;
import br.hendrew.gestor_biblioteca.dtos.LivroDto;
import br.hendrew.gestor_biblioteca.dtos.UsuarioDto;
import br.hendrew.gestor_biblioteca.enums.CategoriaLivro;
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
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setUp() {
        Livro livro = new Livro();
        livro.setTitulo("Título do Livro");
        livro.setAutor("Autor do Livro");
        livro.setIsbn("1234567890");
        livro.setDataPublicacao(LocalDate.now().minusDays(1));
        livro.setCategoria(CategoriaLivro.FICCAO);
        livroRepository.save(livro);

        Usuario usuario = new Usuario();
        usuario.setNome("Nome do Usuário");
        usuario.setEmail("usuario@example.com");
        usuario.setDataCadastro(LocalDate.now());
        usuario.setTelefone("1234567890");
        usuarioRepository.save(usuario);
    }

    @AfterEach
    @Transactional
    void setDow() {
        livroRepository.deleteAll();
        usuarioRepository.deleteAll();
        emprestimoRepository.deleteAll();
    }

    @Test
    void testSaveEmprestimo_validData() {
        EmprestimoDto dto = new EmprestimoDto();
        dto.setLivro(new LivroDto(1));
        dto.setUsuario(new UsuarioDto(1));
        dto.setDataEmprestimo(LocalDate.now());
        dto.setStatus(Status.ATIVO);

        GenericResponse response = emprestimoService.save(dto);

        assertNotNull(response);
        assertEquals("Registro(s) salvo(s) com sucesso.", response.getMessage());
    }

//    @Test
//    void testSaveEmprestimo_livroEmprestado() {
//        EmprestimoDto dto = new EmprestimoDto();
//        dto.setLivro(new LivroDto());
//        dto.getLivro().setId(1);
//        dto.setUsuario(new UsuarioDto());
//        dto.setDataEmprestimo(LocalDate.now());
//        dto.setStatus(Status.ATIVO);
//
//        emprestimoService.save(dto);
//
//        ValidationException exception = assertThrows(ValidationException.class, () -> {
//            emprestimoService.save(dto);
//        });
//
//        assertEquals("Livro " + dto.getLivro().getTitulo() + " já se encontra emprestado!", exception.getMessage());
//    }
//
//    @Test
//    void testValidateEmprestimoDto_validData() {
//        EmprestimoDto dto = new EmprestimoDto();
//        dto.setDataEmprestimo(LocalDate.now());
//        dto.setStatus(Status.ATIVO);
//
//        assertDoesNotThrow(dto::validate);
//    }
//
//    @Test
//    void testValidateEmprestimoDto_invalidDataEmprestimo() {
//        EmprestimoDto dto = new EmprestimoDto();
//        dto.setDataEmprestimo(LocalDate.now().plusDays(1)); // Data futura
//        dto.setStatus(Status.ATIVO);
//
//        ValidationException exception = assertThrows(ValidationException.class, dto::validate);
//        assertEquals("Data de emprestimo não pode ser maior que a data atual!", exception.getMessage());
//    }
//
//    @Test
//    void testLivroEmprestado() {
//        EmprestimoDto dto = new EmprestimoDto();
//        dto.setLivro(new LivroDto());
//        dto.getLivro().setId(1); // Supondo que este ID esteja em uso
//        dto.setUsuario(new UsuarioDto());
//        dto.setDataEmprestimo(LocalDate.now());
//        dto.setStatus(Status.ATIVO);
//
//        emprestimoService.save(dto); // Salva o primeiro empréstimo
//
//        Boolean result = emprestimoService.livroEmprestado(dto.getLivro().getId());
//        assertTrue(result);
//    }
//
//    @Test
//    void testFindById_found() {
//        EmprestimoDto dto = new EmprestimoDto();
//        dto.setLivro(new LivroDto());
//        dto.setUsuario(new UsuarioDto());
//        dto.setDataEmprestimo(LocalDate.now());
//        dto.setStatus(Status.ATIVO);
//
//        GenericResponse response = emprestimoService.save(dto);
//        Emprestimo result = emprestimoService.findById(dto.getId());
//
//        assertNotNull(result);
//    }
//
//    @Test
//    void testFindById_notFound() {
//        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
//            emprestimoService.findById(999); // ID não existente
//        });
//
//        assertEquals("Nenhum registro encontrado.", exception.getMessage());
//    }
//
//    @Test
//    void testFindAll() {
//        EmprestimoDto dto1 = new EmprestimoDto();
//        dto1.setLivro(new LivroDto());
//        dto1.setUsuario(new UsuarioDto());
//        dto1.setDataEmprestimo(LocalDate.now());
//        dto1.setStatus(Status.ATIVO);
//        emprestimoService.save(dto1);
//
//        EmprestimoDto dto2 = new EmprestimoDto();
//        dto2.setLivro(new LivroDto());
//        dto2.setUsuario(new UsuarioDto());
//        dto2.setDataEmprestimo(LocalDate.now());
//        dto2.setStatus(Status.ATIVO);
//        emprestimoService.save(dto2);
//
//        List<EmprestimoDto> result = emprestimoService.findAll();
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//    }
//
//    @Test
//    void testFindDtoById_found() {
//        EmprestimoDto dto = new EmprestimoDto();
//        dto.setLivro(new LivroDto());
//        dto.setUsuario(new UsuarioDto());
//        dto.setDataEmprestimo(LocalDate.now());
//        dto.setStatus(Status.ATIVO);
//
//        GenericResponse response = emprestimoService.save(dto);
//        EmprestimoDto result = emprestimoService.findDtoById(dto.getId());
//
//        assertNotNull(result);
//    }
//
//    @Test
//    void testFindDtoById_notFound() {
//        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
//            emprestimoService.findDtoById(999); // ID não existente
//        });
//
//        assertEquals("Nenhum registro encontrado.", exception.getMessage());
//    }
//
//    @Test
//    void testUpdate() {
//        EmprestimoDto dto = new EmprestimoDto();
//        dto.setLivro(new LivroDto());
//        dto.setUsuario(new UsuarioDto());
//        dto.setDataEmprestimo(LocalDate.now());
//        dto.setStatus(Status.ATIVO);
//
//        emprestimoService.update(dto,1);
//
//        dto.setStatus(Status.BAIXADO);
//        GenericResponse response = emprestimoService.update(dto, dto.getId());
//
//        assertNotNull(response);
//        assertEquals("Registro(s) salvo(s) com sucesso.", response.getMessage());
//    }
//
//    @Test
//    void testDeleteById_found() {
//        EmprestimoDto dto = new EmprestimoDto();
//        dto.setLivro(new LivroDto());
//        dto.setUsuario(new UsuarioDto());
//        dto.setDataEmprestimo(LocalDate.now());
//        dto.setStatus(Status.ATIVO);
//
//        emprestimoService.save(dto);
//        GenericResponse response = emprestimoService.deleteById(dto.getId());
//
//        assertNotNull(response);
//        assertEquals("Registro(s) deletado(s) com sucesso.", response.getMessage());
//    }
//
//    @Test
//    void testDeleteById_notFound() {
//        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
//            emprestimoService.deleteById(999); // ID não existente
//        });
//
//        assertEquals("Nenhum registro encontrado.", exception.getMessage());
//    }
}
