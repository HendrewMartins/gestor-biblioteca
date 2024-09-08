import { CategoriaLivro } from "./enums/categoria-livro";

export interface Livro {
    id?: number;
    titulo?: String;
    autor?: String;
    dataPublicacao?: Date;
    isbn?: String;
    categoria?: CategoriaLivro;
}