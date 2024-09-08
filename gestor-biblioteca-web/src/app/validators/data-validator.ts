import { AbstractControl, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import moment from 'moment';


export class DataValidator {

    static validar(): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            const requerido =  Validators.required;

            if (requerido(control) !== null) {
                return requerido(control) !== null ? {
                    codigo: 'Campo obrigatório.'
                } : null;
            } else {
                const dataValue = control.value;
                const isAfterToday = moment(dataValue).isAfter(moment());
                if(isAfterToday) {
                    return  { codigo: 'A data é maior que a data atual.'}
                }

            }
           
            return null;
        };
    }
}
