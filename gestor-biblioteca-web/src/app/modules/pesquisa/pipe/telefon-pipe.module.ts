import { NgModule } from '@angular/core';
import { TelefonePipe } from './telefone-pipe';
import { CategoriaPipe } from './categoria-pipe';

@NgModule({
  declarations: [TelefonePipe, CategoriaPipe],
  exports: [TelefonePipe,  CategoriaPipe]
})
export class TelefonePipeModule { }
