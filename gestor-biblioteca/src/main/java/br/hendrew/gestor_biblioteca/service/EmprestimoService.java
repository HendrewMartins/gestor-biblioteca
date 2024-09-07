package br.hendrew.gestor_biblioteca.service;

import br.hendrew.gestor_biblioteca.crud.GenericCrudService;
import br.hendrew.gestor_biblioteca.dtos.EmprestimoDto;
import br.hendrew.gestor_biblioteca.model.Emprestimo;
import br.hendrew.gestor_biblioteca.repository.EmprestimoRepository;
import org.springframework.stereotype.Service;

@Service
public class EmprestimoService extends GenericCrudService<Emprestimo, Integer, EmprestimoRepository, EmprestimoDto> {

}
