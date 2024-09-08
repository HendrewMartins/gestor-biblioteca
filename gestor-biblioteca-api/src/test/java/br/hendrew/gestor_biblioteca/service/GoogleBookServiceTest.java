package br.hendrew.gestor_biblioteca.service;

import br.hendrew.gestor_biblioteca.dtos.google_books.Book;
import br.hendrew.gestor_biblioteca.model.Livro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class GoogleBookServiceTest {

    @Mock
    LivroService livroService;

    @InjectMocks
    GoogleBookService googleBookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchBooksByTitle() {
        String title = "Test Book";
        List<Book> books = googleBookService.searchBooksByTitle(title);
        assertNotNull(books);
    }

    @Test
    void testAddLivroByBook() {
        Book book = new Book();
        book.setTitle("Test Title");
        book.setAuthors("Author1, Author2");
        book.setDescription("Test Description");
        book.setPublishedDate(LocalDate.parse("2022-01-01"));
        book.setIsbn("1234567890123");
        book.setCategory("Fiction");
        googleBookService.addLivroByBook(book);
        verify(livroService, times(1)).saveEntity(any(Livro.class));
    }
}
