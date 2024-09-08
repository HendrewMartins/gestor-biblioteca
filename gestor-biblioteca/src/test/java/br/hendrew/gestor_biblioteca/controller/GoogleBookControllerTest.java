package br.hendrew.gestor_biblioteca.controller;

import br.hendrew.gestor_biblioteca.dtos.google_books.Book;
import br.hendrew.gestor_biblioteca.service.GoogleBookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GoogleBookController.class)
public class GoogleBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    GoogleBookService googleBookService;

    @Autowired
    ObjectMapper objectMapper;

    private static final String DESCRIPTION = "The Definitive Guide to Java Platform Best Practices...";

    @Test
    public void testSearchBooks() throws Exception {
        Book mockBook = getBook();
        List<Book> books = List.of(mockBook);

        when(googleBookService.searchBooksByTitle(any())).thenReturn(books);
        mockMvc.perform(get("/api/v1/google-books/search")
                        .param("title", "Java")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Effective Java"))
                .andExpect(jsonPath("$[0].authors").value("Joshua Bloch"))
                .andExpect(jsonPath("$[0].description").value(DESCRIPTION))
                .andExpect(jsonPath("$[0].isbn").value("9780134686042"));

        verify(googleBookService, times(1)).searchBooksByTitle("Java");
    }

    @Test
    public void testAddBook() throws Exception {
        Book book = getBook();
        mockMvc.perform(post("/api/v1/google-books/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Importado livro para a biblioteca!"))
                .andExpect(jsonPath("$.status").value(200));

        verify(googleBookService, times(1)).addLivroByBook(any(Book.class));
    }

    private Book getBook() {
        return new Book("Effective Java",
                "Joshua Bloch", DESCRIPTION,
                LocalDate.parse("2017-12-18"),
                "Computers",
                "9780134686042"
        );
    }
}
