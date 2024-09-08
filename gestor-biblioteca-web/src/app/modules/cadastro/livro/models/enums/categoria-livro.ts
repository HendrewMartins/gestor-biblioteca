export enum CategoriaLivro {
    ROMANCE = "Romance",
    FICCAO = "Ficção",
    NAO_FICCAO = "Não-ficção",
    FANTASIA = "Fantasia",
    CIENTIFICO = "Científico",
    BIOGRAFIA = "Biografia",
    HISTORIA = "História",
    AUTOAJUDA = "Autoajuda",
    MISTERIO = "Mistério",
    TERROR = "Terror",
    COMPUTADOR = "Computador"
}

export interface LabelValue {
    label: string;
    value: string;
}

export const CategoriaLivroLabelValue = [
    { label: 'Romance', value: 'ROMANCE' },
    { label: 'Ficção', value: 'FICCAO' },
    { label: 'Não-ficção', value: 'NAO_FICCAO' },
    { label: 'Fantasia', value: 'FANTASIA' },
    { label: 'Científico', value: 'CIENTIFICO' },
    { label: 'Biografia', value: 'BIOGRAFIA' },
    { label: 'História', value: 'HISTORIA' },
    { label: 'Autoajuda', value: 'AUTOAJUDA' },
    { label: 'Mistério', value: 'MISTERIO' },
    { label: 'Terror', value: 'TERROR' },
    { label: 'Computador', value: 'COMPUTADOR' }
  ];
