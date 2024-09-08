import { NgModule } from '@angular/core';

import { ErroPipe } from './erro.pipe';


@NgModule({
  declarations: [ErroPipe],
  exports: [ErroPipe]
})
export class ErroModule { }
