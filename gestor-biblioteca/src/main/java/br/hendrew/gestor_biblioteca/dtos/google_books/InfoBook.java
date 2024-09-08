package br.hendrew.gestor_biblioteca.dtos.google_books;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoBook {
    private String title;
    private List<String> authors;
    private String description;
    private String publishedDate;
    private List<IndustryIdentifier> industryIdentifiers;
    private int pageCount;
    private String printType;
    private List<String> categories;
}