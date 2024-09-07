package br.hendrew.gestor_biblioteca.repository;

import br.hendrew.gestor_biblioteca.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Integer> {
}
