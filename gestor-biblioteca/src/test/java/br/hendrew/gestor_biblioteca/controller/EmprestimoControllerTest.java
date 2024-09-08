package br.hendrew.gestor_biblioteca.controller;

import br.hendrew.gestor_biblioteca.dtos.EmprestimoDto;
import br.hendrew.gestor_biblioteca.dtos.LivroDto;
import br.hendrew.gestor_biblioteca.dtos.UsuarioDto;
import br.hendrew.gestor_biblioteca.enums.Status;
import br.hendrew.gestor_biblioteca.service.EmprestimoService;
import br.hendrew.gestor_biblioteca.utils.generic_reponse.GenericResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmprestimoController.class)
public class EmprestimoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    EmprestimoService emprestimoService;

    @Test
    void testGetAllEmprestimos() throws Exception {
        List<EmprestimoDto> emprestimos = Arrays.asList(new EmprestimoDto(1), new EmprestimoDto(2));

        when(emprestimoService.findAll()).thenReturn(emprestimos);

        mockMvc.perform(get("/api/v1/emprestimo"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1},{\"id\":2}]"));
    }

    @Test
    void testGetEmprestimoById() throws Exception {
        EmprestimoDto emprestimo = new EmprestimoDto(1);

        when(emprestimoService.findDtoById(anyInt())).thenReturn(emprestimo);

        mockMvc.perform(get("/api/v1/emprestimo/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1}"));
    }

    @Test
    void testGetRecomendacao() throws Exception {
        List<LivroDto> recomendacoes = Arrays.asList(new LivroDto(1, "Livro 1"), new LivroDto(2, "Livro 2"));

        when(emprestimoService.recomendacao(anyInt())).thenReturn(recomendacoes);

        mockMvc.perform(get("/api/v1/emprestimo/recomendacao/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"titulo\":\"Livro 1\"},{\"titulo\":\"Livro 2\"}]"));
    }

    @Test
    void testSaveEmprestimo() throws Exception {
        EmprestimoDto emprestimoDto = getEmprestimoDto();
        GenericResponse response = new GenericResponse("Registro(s) salvo(s) com sucesso.", HttpStatus.OK.value());
        when(emprestimoService.save(any())).thenReturn(response);
        mockMvc.perform(post("/api/v1/emprestimo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(emprestimoDto)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Registro(s) salvo(s) com sucesso.\"}"))
                .andExpect(content().json("{\"status\":200}"));
    }

    @Test
    void testDeleteEmprestimo() throws Exception {
        GenericResponse response = new GenericResponse("Registro(s) deletado(s) com sucesso.", HttpStatus.OK.value());

        when(emprestimoService.deleteById(anyInt())).thenReturn(response);

        mockMvc.perform(delete("/api/v1/emprestimo/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Registro(s) deletado(s) com sucesso.\"}"))
                .andExpect(content().json("{\"status\":200}"));
    }

    @Test
    void testUpdateEmprestimo() throws Exception {
        EmprestimoDto emprestimoDto = getEmprestimoDto();
        emprestimoDto.setStatus(Status.BAIXADO);
        emprestimoDto.setDataDevolucao(LocalDate.of(2024,9,8));
        GenericResponse response = new GenericResponse("Registro(s) salvo(s) com sucesso.", HttpStatus.OK.value());
        when(emprestimoService.update(any(EmprestimoDto.class), anyInt())).thenReturn(response);

        mockMvc.perform(put("/api/v1/emprestimo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(emprestimoDto)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Registro(s) salvo(s) com sucesso.\"}"))
                .andExpect(content().json("{\"status\":200}"));
    }

    private String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private EmprestimoDto getEmprestimoDto() {
        EmprestimoDto emprestimoDto = new EmprestimoDto();
        emprestimoDto.setId(1);
        emprestimoDto.setDataEmprestimo(LocalDate.parse("2024-09-07"));
        emprestimoDto.setStatus(Status.ATIVO);
        emprestimoDto.setLivro(new LivroDto(1));
        emprestimoDto.setUsuario(new UsuarioDto(1));
        return emprestimoDto;
    }

}
