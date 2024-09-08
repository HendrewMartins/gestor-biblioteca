package br.hendrew.gestor_biblioteca.service;

import br.hendrew.gestor_biblioteca.dtos.google_books.Book;
import br.hendrew.gestor_biblioteca.dtos.google_books.Response;
import br.hendrew.gestor_biblioteca.enums.CategoriaLivro;
import br.hendrew.gestor_biblioteca.model.Livro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class GoogleBookService {

    @Autowired
    LivroService livroService;

    private final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    public List<Book> searchBooksByTitle(String title) {
        RestTemplate restTemplate = new RestTemplate();
        Response response = restTemplate.getForObject(GOOGLE_BOOKS_API_URL + title, Response.class);
        return response.getItems().stream()
                .map(item -> {
                    Book book = new Book();
                    book.setTitle(item.getVolumeInfo().getTitle());
                    if (item.getVolumeInfo().getAuthors() != null) {
                        book.setAuthors(String.join(", ", item.getVolumeInfo().getAuthors()));
                    }
                    book.setDescription(item.getVolumeInfo().getDescription());
                    if (item.getVolumeInfo().getPublishedDate() != null && Objects.equals(item.getVolumeInfo().getPublishedDate().length(), 10)) {
                        book.setPublishedDate(LocalDate.parse(item.getVolumeInfo().getPublishedDate()));
                    } else {
                        book.setPublishedDate(LocalDate.now());
                    }
                    if (item.getVolumeInfo().getCategories() != null && !item.getVolumeInfo().getCategories().isEmpty()) {
                        book.setCategory(item.getVolumeInfo().getCategories().get(0));
                    }

                    item.getVolumeInfo().getIndustryIdentifiers().stream()
                            .filter(identifier -> "ISBN_13".equals(identifier.getType()))
                            .findFirst()
                            .ifPresent(identifier -> book.setIsbn(identifier.getIdentifier()));

                    return book;
                })
                .toList();
    }

    public void addLivroByBook(Book book) {
        Livro livro = new Livro();
        livro.setAutor(book.getAuthors());
        livro.setIsbn(book.getIsbn());
        livro.setTitulo(book.getTitle());
        livro.setDataPublicacao(book.getPublishedDate());
        livro.setCategoria(CategoriaLivro.fromCategoria(book.getCategory()));
        livroService.saveEntity(livro);
    }

}
