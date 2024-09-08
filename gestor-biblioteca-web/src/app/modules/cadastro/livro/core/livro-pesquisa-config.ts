import { PesquisaConfig } from "../../../pesquisa/models/pesquisa-config";


export const PESQUISA_LIVRO_CONFIG: PesquisaConfig = {
    colunas: [
        {
            label: 'Código',
            nome: 'id'
        },
        {
            label: 'Título',
            nome: 'titulo'
        },
        {
            label: 'Autor',
            nome: 'autor'
        },
        {
            label: 'ISBN',
            nome: 'isbn',
        },
        {
            label: 'Publicação',
            nome: 'dataPublicacao',
            tipo: 'DATA'
        },
        {
            label: 'Categoria',
            nome: 'categoria',
            tipo: 'CATEGORIA'
        },
    ],
    pathApi: 'v1/livro'
};
