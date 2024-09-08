package br.hendrew.gestor_biblioteca.cenarios;

import br.hendrew.gestor_biblioteca.dtos.LivroDto;
import br.hendrew.gestor_biblioteca.enums.CategoriaLivro;
import br.hendrew.gestor_biblioteca.model.Livro;

import java.time.LocalDate;

public class CenarioLivro {

    public static Livro livro() {
        Livro livro = new Livro();
        livro.setTitulo("Título do Livro");
        livro.setAutor("Autor do Livro");
        livro.setIsbn("1234567890");
        livro.setDataPublicacao(LocalDate.now().minusDays(1));
        livro.setCategoria(CategoriaLivro.FICCAO);
        return livro;
    }

    public static LivroDto livroDto() {
        LivroDto dto = new LivroDto();
        dto.setTitulo("Título do Livro");
        dto.setAutor("Autor do Livro");
        dto.setIsbn("1234567890");
        dto.setDataPublicacao(LocalDate.now().minusDays(1));
        dto.setCategoria(CategoriaLivro.FICCAO);
        return dto;
    }
}
