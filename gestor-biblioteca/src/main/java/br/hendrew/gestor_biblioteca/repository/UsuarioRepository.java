package br.hendrew.gestor_biblioteca.repository;

import br.hendrew.gestor_biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
