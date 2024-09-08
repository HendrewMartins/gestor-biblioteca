package br.hendrew.gestor_biblioteca.controller;

import br.hendrew.gestor_biblioteca.dtos.google_books.Book;
import br.hendrew.gestor_biblioteca.service.GoogleBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/google-books")
public class BookController {

    @Autowired
    private GoogleBookService googleBookService;

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String title) {
        List<Book> books = googleBookService.searchBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

//    @PostMapping("/add")
//    public ResponseEntity<Book> addBook(@RequestBody Book book) {
//        Book savedBook = bookService.addBook(book);
//        return ResponseEntity.ok(savedBook);
//    }
}
