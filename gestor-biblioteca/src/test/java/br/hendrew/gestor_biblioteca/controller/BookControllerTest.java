package br.hendrew.gestor_biblioteca.controller;

import br.hendrew.gestor_biblioteca.dtos.google_books.Book;
import br.hendrew.gestor_biblioteca.service.GoogleBookService;
import br.hendrew.gestor_biblioteca.utils.generic_reponse.GenericResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BookControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private BookController bookController;

    @Mock
    private GoogleBookService googleBookService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void testSearchBooks() {
        Book book = new Book();
        book.setTitle("Test Book");
        List<Book> mockBooks = Arrays.asList(book);
        when(googleBookService.searchBooksByTitle("Test")).thenReturn(mockBooks);
        ResponseEntity<List<Book>> response = bookController.searchBooks("Test");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Test Book", response.getBody().get(0).getTitle());
    }

    @Test
    public void testAddBook() {
        Book book = new Book();
        book.setTitle("New Book");
        GenericResponse response = bookController.addBook(book);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Importado livro para a biblioteca!", response.getMessage());
    }
}
