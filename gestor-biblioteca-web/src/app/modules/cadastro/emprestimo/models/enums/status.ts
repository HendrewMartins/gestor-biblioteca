export enum Status {
    ATIVO = "Ativo",
    BAIXADO = "Baixado",
}

export interface LabelValue {
    label: string;
    value: string;
}

export const StatusLabelValue = [
    { label: 'Ativo', value: 'ATIVO' },
    { label: 'Baixado', value: 'BAIXADO' }
  ];
