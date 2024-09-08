import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'telefone'
})
export class TelefonePipe implements PipeTransform {

    public temporaria: any;

    transform(value: any): any {
        if (!value) return '';
        return value.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3')
                .replace(/(\d{2})(\d{4})(\d{4})/, '($1) $2-$3');
    }

}