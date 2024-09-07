package br.hendrew.gestor_biblioteca.utils;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.engine.query.spi.NamedParameterDescriptor;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class QueryDto<E> {

    private Class<E> dtoClass;

    @Getter
    private NativeQuery query;

    public QueryDto(String sql, EntityManager em, Class<E> dtoClass) {
        this.query = em.unwrap(Session.class).createNativeQuery(sql, Tuple.class);
        this.dtoClass = dtoClass;
    }

    public List<E> getResultList() throws Exception {
        TupleUtil<E> util = new TupleUtil<>();
        return util.toDTO(this.query, this.dtoClass);
    }

    public Long getCount() {
        return this.query.getResultCount();
    }

    public E getSingleResult() throws Exception {
        TupleUtil<E> util = new TupleUtil<>();
        List<E> list = util.toDTO(this.query, this.dtoClass);
        if (list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    public void setParameter(String parametro, String objct) {
        this.query.setParameter(parametro, objct);
    }

    public void setParameter(String parametro, Object objct) {
        this.query.setParameter(parametro, objct);
    }

    public void setParameter(String parametro, Long objct) {
        this.query.setParameter(parametro, objct);
    }

    public boolean parameterExists(String parameter) {
        return this.query.getParameters().contains(new NamedParameterDescriptor(parameter, null, null));
    }

    public void setParameterIfExists(String parametro, Object objct) {
        if (parameterExists(parametro))
            this.query.setParameter(parametro, objct);
    }


    public void paginator(Integer count, Integer page) {
        Integer recordPage = count != null ? count : 0;
        Integer firstPage = (page == null) ? 0 : recordPage * (page - 1);
        this.query.setFirstResult(firstPage);
        this.query.setMaxResults(recordPage);
    }

}
