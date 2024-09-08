import { AbstractControl, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';


export class EmailValidator {
    private static readonly EMAIL_PATTERN: RegExp = /^[a-zA-Z0-9_+&*-]+(?:\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,7}$/;

    static validar(): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            const validacao = Validators.pattern(this.EMAIL_PATTERN);
            const tamanho = Validators.maxLength(100);
            const requerido =  Validators.required;

            if (validacao(control) !== null) {
                return validacao(control) !== null ? {
                    codigo: 'Email Inválido'
                } : null;
            } else if (tamanho(control) !== null) {
                return tamanho(control) !== null ? {
                    codigo: 'Quantide Máxima de 100 Caracteres'
                } : null;
            } else if (requerido(control) !== null) {
                return requerido(control) !== null ? {
                    codigo: 'Campo obrigatório.'
                } : null;
            }
            return null;
        };
    }
}
