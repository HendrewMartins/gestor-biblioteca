import { TipoColuna } from './tipo-coluna';

export interface ColunaConfig {
    nome: string;
    label: string;
    width?: string;
    tipo?: string;
    colunaEntidade: string;
}
