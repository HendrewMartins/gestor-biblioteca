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
public class Response {
    private List<ItemBook> items;
}
