package br.hendrew.gestor_biblioteca.controller;

import br.hendrew.gestor_biblioteca.dtos.UsuarioDto;
import br.hendrew.gestor_biblioteca.service.UsuarioService;
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

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UsuarioService usuarioService;

    @Test
    void testGetAllUsuario() throws Exception {
        List<UsuarioDto> usuarios = Arrays.asList(new UsuarioDto(1), new UsuarioDto(2));

        when(usuarioService.findAll()).thenReturn(usuarios);

        mockMvc.perform(get("/api/v1/usuario"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1},{\"id\":2}]"));
    }

    @Test
    void testGetUsuarioById() throws Exception {
        UsuarioDto usuarioDto = new UsuarioDto(1);

        when(usuarioService.findDtoById(anyInt())).thenReturn(usuarioDto);

        mockMvc.perform(get("/api/v1/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1}"));
    }

    @Test
    void testSaveUsuario() throws Exception {
        UsuarioDto usuarioDto = getUsuarioDto();
        GenericResponse response = new GenericResponse("Registro(s) salvo(s) com sucesso.", HttpStatus.OK.value());
        when(usuarioService.save(any())).thenReturn(response);
        mockMvc.perform(post("/api/v1/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(usuarioDto)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Registro(s) salvo(s) com sucesso.\"}"))
                .andExpect(content().json("{\"status\":200}"));
    }

    @Test
    void testDeleteUsuario() throws Exception {
        GenericResponse response = new GenericResponse("Registro(s) deletado(s) com sucesso.", HttpStatus.OK.value());

        when(usuarioService.deleteById(anyInt())).thenReturn(response);

        mockMvc.perform(delete("/api/v1/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Registro(s) deletado(s) com sucesso.\"}"))
                .andExpect(content().json("{\"status\":200}"));
    }

    @Test
    void testUpdateUsuario() throws Exception {
        UsuarioDto usuarioDto = getUsuarioDto();
        GenericResponse response = new GenericResponse("Registro(s) salvo(s) com sucesso.", HttpStatus.OK.value());
        when(usuarioService.update(any(UsuarioDto.class), anyInt())).thenReturn(response);

        mockMvc.perform(put("/api/v1/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(usuarioDto)))
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

    private UsuarioDto getUsuarioDto() {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setId(1);
        usuarioDto.setNome("Augusto");
        usuarioDto.setDataCadastro(LocalDate.parse("2024-09-07"));
        usuarioDto.setEmail("augusto@gmail.com");
        usuarioDto.setTelefone("44999998888");
        return usuarioDto;
    }
}
