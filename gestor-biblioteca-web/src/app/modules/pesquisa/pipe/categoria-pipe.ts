import { Pipe, PipeTransform } from '@angular/core';
import { CategoriaLivroLabelValue } from '../../cadastro/livro/models/enums/categoria-livro';

@Pipe({
  name: 'categoria'
})
export class CategoriaPipe implements PipeTransform {

  transform(value: string): string {
    const categoria = CategoriaLivroLabelValue.find(cat => cat.value === value);
    return categoria ? categoria.label : value; 
  }

}
