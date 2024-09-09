import { Livro } from "../../livro/models/livro";
import { Usuario } from "../../usuario/models/usuario";
import { Status } from "./enums/status";

export interface Emprestimo{
    id?: number;
    livro?: Livro;
    usuario?: Usuario;
    dataEmprestimo?: Date;
    dataDevolucao?: Date;
    status?: Status;
}