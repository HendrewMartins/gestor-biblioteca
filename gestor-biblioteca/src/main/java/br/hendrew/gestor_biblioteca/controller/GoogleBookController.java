package br.hendrew.gestor_biblioteca.controller;

import br.hendrew.gestor_biblioteca.dtos.google_books.Book;
import br.hendrew.gestor_biblioteca.service.GoogleBookService;
import br.hendrew.gestor_biblioteca.utils.generic_reponse.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/google-books")
public class GoogleBookController {

    @Autowired
    GoogleBookService googleBookService;

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String title) {
        List<Book> books = this.googleBookService.searchBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    @PostMapping("/add")
    public GenericResponse addBook(@RequestBody Book book) {
        this.googleBookService.addLivroByBook(book);
        return GenericResponse.getGenericResponse("Importado livro para a biblioteca!", HttpStatus.OK.value());
    }
}
