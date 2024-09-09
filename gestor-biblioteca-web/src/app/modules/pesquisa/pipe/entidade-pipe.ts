import { Pipe, PipeTransform } from '@angular/core';
import { StatusLabelValue } from '../../cadastro/emprestimo/models/enums/status';

@Pipe({
    name: 'entidade'
})
export class EntidadePipe implements PipeTransform {

    transform(value: string, propriedade: string): string {
        return value ? value[propriedade] : '';
    }

}
