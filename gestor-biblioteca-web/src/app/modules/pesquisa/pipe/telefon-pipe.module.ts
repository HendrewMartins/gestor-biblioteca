import { NgModule } from '@angular/core';
import { TelefonePipe } from './telefone-pipe';
import { CategoriaPipe } from './categoria-pipe';
import { StatusPipe } from './status-pipe';
import { EntidadePipe } from './entidade-pipe';

@NgModule({
  declarations: [TelefonePipe, CategoriaPipe, StatusPipe, EntidadePipe],
  exports: [TelefonePipe,  CategoriaPipe, StatusPipe, EntidadePipe]
})
export class TelefonePipeModule { }
