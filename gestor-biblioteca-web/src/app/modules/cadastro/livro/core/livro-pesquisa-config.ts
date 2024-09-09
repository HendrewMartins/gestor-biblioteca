import { PesquisaConfig } from "../../../pesquisa/models/pesquisa-config";


export const PESQUISA_LIVRO_CONFIG: PesquisaConfig = {
    colunas: [
        {
            label: 'Código',
            nome: 'id',
            colunaEntidade:''
        },
        {
            label: 'Título',
            nome: 'titulo',
            colunaEntidade:''
        },
        {
            label: 'Autor',
            nome: 'autor',
            colunaEntidade:''
        },
        {
            label: 'ISBN',
            nome: 'isbn',
            colunaEntidade:''
        },
        {
            label: 'Publicação',
            nome: 'dataPublicacao',
            tipo: 'DATA',
            colunaEntidade:''
        },
        {
            label: 'Categoria',
            nome: 'categoria',
            tipo: 'CATEGORIA',
            colunaEntidade:''
        },
    ],
    pathApi: 'v1/livro'
};
