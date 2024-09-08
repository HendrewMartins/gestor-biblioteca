package br.hendrew.gestor_biblioteca.service;

import br.hendrew.gestor_biblioteca.dtos.google_books.*;
import br.hendrew.gestor_biblioteca.model.Livro;
import br.hendrew.gestor_biblioteca.enums.CategoriaLivro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class GoogleBookServiceTest {

    @Mock
    RestTemplate restTemplate;

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
        Response mockResponse = new Response();
        mockResponse.setItems(Collections.singletonList(createMockItem()));

        when(restTemplate.getForObject(anyString(), eq(Response.class)))
                .thenReturn(mockResponse);
        List<Book> books = googleBookService.searchBooksByTitle(title);
        assertNotNull(books);
        assertEquals(1, books.size());
        Book book = books.get(0);
        assertEquals("Test Title", book.getTitle());
        assertEquals("Author1, Author2", book.getAuthors());
        assertEquals("Test Description", book.getDescription());
        assertEquals(LocalDate.parse("2022-01-01"), book.getPublishedDate());
        assertEquals("1234567890123", book.getIsbn());
        assertEquals("Category1", book.getCategory());
    }

    @Test
    void testAddLivroByBook() {
        Book book = new Book();
        book.setTitle("Test Title");
        book.setAuthors("Author1, Author2");
        book.setDescription("Test Description");
        book.setPublishedDate(LocalDate.parse("2022-01-01"));
        book.setIsbn("1234567890123");
        book.setCategory("Category1");

        googleBookService.addLivroByBook(book);
        verify(livroService, times(1)).saveEntity(any(Livro.class));
    }

    private ItemBook createMockItem() {
        ItemBook item = new ItemBook();
        InfoBook infoBook = new InfoBook();
        infoBook.setTitle("Test Title");
        infoBook.setAuthors(Arrays.asList("Author1", "Author2"));
        infoBook.setDescription("Test Description");
        infoBook.setPublishedDate("2022-01-01");
        infoBook.setCategories(Collections.singletonList("Category1"));
        infoBook.setIndustryIdentifiers(Arrays.asList(
                new IndustryIdentifier("ISBN_13", "1234567890123")
        ));
        item.setVolumeInfo(infoBook);
        return item;
    }
}
