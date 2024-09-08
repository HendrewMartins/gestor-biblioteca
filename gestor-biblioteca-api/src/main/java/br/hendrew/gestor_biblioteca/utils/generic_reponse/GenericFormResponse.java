package br.hendrew.gestor_biblioteca.utils.generic_reponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GenericFormResponse<T> {

    private Integer pagina;
    private Integer quantidade;
    private Long total;
    private List<T> lista;

    public void setLista(List<T> list) {
        lista = list;
        total = Long.valueOf(list.size());
    }

}
