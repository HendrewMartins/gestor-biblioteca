import { PesquisaConfig } from "../../../pesquisa/models/pesquisa-config";
import { TipoColuna } from "../../../pesquisa/models/tipo-coluna";


export const PESQUISA_USUARIO_CONFIG: PesquisaConfig = {
    colunas: [
        {
            label: 'Código',
            nome: 'id',
            colunaEntidade:''
        },
        {
            label: 'Nome',
            nome: 'nome',
            colunaEntidade:''
        },
        {
            label: 'Email',
            nome: 'email',
            colunaEntidade:''
        },
        {
            label: 'Data de Cadastro',
            nome: 'dataCadastro',
            tipo: 'DATA',
            colunaEntidade:''
        },
        {
            label: 'Telefone',
            nome: 'telefone',
            tipo: 'TELEFONE',
            colunaEntidade:''
        },
    ],
    pathApi: 'v1/usuario'
};
