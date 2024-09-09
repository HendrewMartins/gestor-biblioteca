import { Pipe, PipeTransform } from '@angular/core';
import { StatusLabelValue } from '../../cadastro/emprestimo/models/enums/status';

@Pipe({
  name: 'status'
})
export class StatusPipe implements PipeTransform {

  transform(value: string): string {
    const status = StatusLabelValue.find(cat => cat.value === value);
    return status ? status.label : value;
  }

}
