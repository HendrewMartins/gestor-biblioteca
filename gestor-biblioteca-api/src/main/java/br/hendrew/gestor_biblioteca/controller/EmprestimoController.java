package br.hendrew.gestor_biblioteca.controller;

import br.hendrew.gestor_biblioteca.crud.CrudController;
import br.hendrew.gestor_biblioteca.dtos.EmprestimoDto;
import br.hendrew.gestor_biblioteca.dtos.LivroDto;
import br.hendrew.gestor_biblioteca.model.Emprestimo;
import br.hendrew.gestor_biblioteca.service.EmprestimoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/emprestimo")
public class EmprestimoController extends CrudController<Emprestimo, EmprestimoService, EmprestimoDto> {

    @GetMapping(path = "/recomendacao/{id}")
    public List<LivroDto> getRecomendacao(@PathVariable(name = "id", required = true) Integer usuarioId) throws Exception {
        return getService().recomendacao(usuarioId);
    }
}
