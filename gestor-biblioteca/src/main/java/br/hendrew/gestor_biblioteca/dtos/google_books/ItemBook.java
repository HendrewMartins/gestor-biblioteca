package br.hendrew.gestor_biblioteca.dtos.google_books;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemBook {
    private InfoBook volumeInfo;
}