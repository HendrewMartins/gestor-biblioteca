package br.hendrew.gestor_biblioteca.repository;

import br.hendrew.gestor_biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
}
