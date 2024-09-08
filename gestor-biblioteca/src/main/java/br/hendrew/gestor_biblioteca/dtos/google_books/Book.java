package br.hendrew.gestor_biblioteca.dtos.google_books;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private String title;
    private String authors;
    private String description;
    private LocalDate publishedDate;
    private String category;
    private String isbn;

}
