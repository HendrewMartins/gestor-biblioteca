import { NgModule } from '@angular/core';
import { TelefonePipe } from './telefone-pipe';

@NgModule({
  declarations: [TelefonePipe],
  exports: [TelefonePipe]
})
export class TelefonePipeModule { }
