package br.hendrew.gestor_biblioteca.controller;

import br.hendrew.gestor_biblioteca.dtos.LivroDto;
import br.hendrew.gestor_biblioteca.enums.CategoriaLivro;
import br.hendrew.gestor_biblioteca.service.LivroService;
import br.hendrew.gestor_biblioteca.utils.generic_reponse.GenericResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LivroController.class)
public class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    LivroService livroService;

    @Test
    void testGetAllLivro() throws Exception {
        List<LivroDto> livros = Arrays.asList(new LivroDto(1), new LivroDto(2));

        when(livroService.findAll()).thenReturn(livros);

        mockMvc.perform(get("/api/v1/livro"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1},{\"id\":2}]"));
    }

    @Test
    void testGetLivroById() throws Exception {
        LivroDto livro = new LivroDto(1);

        when(livroService.findDtoById(anyInt())).thenReturn(livro);

        mockMvc.perform(get("/api/v1/livro/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1}"));
    }

    @Test
    void testSaveLivro() throws Exception {
        LivroDto livroDto = getLivroDto();
        GenericResponse response = new GenericResponse("Registro(s) salvo(s) com sucesso.", HttpStatus.OK.value());
        when(livroService.save(any())).thenReturn(response);
        mockMvc.perform(post("/api/v1/livro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(livroDto)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Registro(s) salvo(s) com sucesso.\"}"))
                .andExpect(content().json("{\"status\":200}"));
    }

    @Test
    void testDeleteLivro() throws Exception {
        GenericResponse response = new GenericResponse("Registro(s) deletado(s) com sucesso.", HttpStatus.OK.value());

        when(livroService.deleteById(anyInt())).thenReturn(response);

        mockMvc.perform(delete("/api/v1/livro/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Registro(s) deletado(s) com sucesso.\"}"))
                .andExpect(content().json("{\"status\":200}"));
    }

    @Test
    void testUpdateLivro() throws Exception {
        LivroDto livroDto = getLivroDto();
        livroDto.setAutor("UPDATE");
        GenericResponse response = new GenericResponse("Registro(s) salvo(s) com sucesso.", HttpStatus.OK.value());
        when(livroService.update(any(LivroDto.class), anyInt())).thenReturn(response);

        mockMvc.perform(put("/api/v1/livro/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(livroDto)))
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

    private LivroDto getLivroDto() {
        LivroDto livroDto = new LivroDto();
        livroDto.setId(1);
        livroDto.setTitulo("Insert");
        livroDto.setAutor("Autor livro");
        livroDto.setIsbn("987654321");
        livroDto.setCategoria(CategoriaLivro.AUTOAJUDA);
        livroDto.setDataPublicacao(LocalDate.parse("2024-09-07"));
        return livroDto;
    }
}
