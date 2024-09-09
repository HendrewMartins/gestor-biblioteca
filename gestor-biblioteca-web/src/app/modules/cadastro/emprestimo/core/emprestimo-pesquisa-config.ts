import { PesquisaConfig } from "../../../pesquisa/models/pesquisa-config";


export const PESQUISA_EMPRESTIMO_CONFIG: PesquisaConfig = {
    colunas: [
        {
            label: 'Código',
            nome: 'id',
            colunaEntidade:''
        },
        {
            label: 'Livro',
            nome: 'livro',
            tipo: 'ENTIDADE',
            colunaEntidade: "titulo"
        },
        {
            label: 'Usuario',
            nome: 'usuario',
            tipo: 'ENTIDADE',
            colunaEntidade: "nome"
        },
        {
            label: 'Emprestimo',
            nome: 'dataEmprestimo',
            tipo: 'DATA',
            colunaEntidade:''
        },
        {
            label: 'Devolução',
            nome: 'dataDevolucao',
            tipo: 'DATA',
            colunaEntidade:''
        },
        {
            label: 'Situação',
            nome: 'status',
            tipo: 'STATUS',
            colunaEntidade:''
        },
    ],
    pathApi: 'v1/emprestimo'
};
