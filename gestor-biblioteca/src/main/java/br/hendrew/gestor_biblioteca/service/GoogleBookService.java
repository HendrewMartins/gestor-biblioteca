package br.hendrew.gestor_biblioteca.service;

import br.hendrew.gestor_biblioteca.dtos.google_books.Book;
import br.hendrew.gestor_biblioteca.dtos.google_books.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GoogleBookService {

    private final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    public List<Book> searchBooksByTitle(String title) {
        RestTemplate restTemplate = new RestTemplate();
        Response response = restTemplate.getForObject(GOOGLE_BOOKS_API_URL + title, Response.class);
        return response.getItems().stream()
                .map(item -> {
                    Book book = new Book();
                    book.setTitle(item.getVolumeInfo().getTitle());
                    book.setAuthors(String.join(", ", item.getVolumeInfo().getAuthors()));
                    book.setDescription(item.getVolumeInfo().getDescription());
                    book.setPublishedDate(item.getVolumeInfo().getPublishedDate());
                    return book;
                })
                .toList();
    }

}
