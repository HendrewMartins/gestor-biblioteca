import { PesquisaConfig } from "../../../pesquisa/models/pesquisa-config";
import { TipoColuna } from "../../../pesquisa/models/tipo-coluna";


export const PESQUISA_USUARIO_CONFIG: PesquisaConfig = {
    colunas: [
        {
            label: 'Código',
            nome: 'id'
        },
        {
            label: 'Nome',
            nome: 'nome'
        },
        {
            label: 'Email',
            nome: 'email'
        },
        {
            label: 'Data de Cadastro',
            nome: 'dataCadastro',
            tipo: 'DATA'
        },
        {
            label: 'Telefone',
            nome: 'telefone',
            tipo: 'TELEFONE'
        },
    ],
    pathApi: 'v1/usuario'
};
