package br.hendrew.gestor_biblioteca.service;

import br.hendrew.gestor_biblioteca.crud.GenericCrudService;
import br.hendrew.gestor_biblioteca.model.Livro;
import br.hendrew.gestor_biblioteca.repository.LivroRepository;
import org.springframework.stereotype.Service;

@Service
public class LivroService extends GenericCrudService<Livro, Integer, LivroRepository> {
}
