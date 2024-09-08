import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'erro'
})
export class ErroPipe implements PipeTransform {

    public temporaria: any;

    transform(value: any): any {
        if (value) {
            if (value['required']) {
                return 'Campo obrigatório.';
            } else if (value['email']) {
                return 'E-mail inválido';
            } else if (value['minlength']) {
                this.temporaria = value.minlength;
                return 'Quantide Minima de ' + this.temporaria.requiredLength
                    + ' Caracteres quantidade digitada ' + this.temporaria.actualLength;
            } else if (value['maxlength']) {
                this.temporaria = value.maxlength;
                return 'Quantide Máxima de ' + this.temporaria.requiredLength
                    + ' Caracteres quantidade digitada ' + this.temporaria.actualLength;
            } else if (value['min']) {
                return 'Quantide Minima';
            } else if (value['max']) {
                return 'Quantide Máxima';
            }          
            else {
                const chaves = Object.keys(value);
                if (chaves.length > 0) {
                    return value[chaves[0]];
                }
            }
        }
        return null;
    }

}















