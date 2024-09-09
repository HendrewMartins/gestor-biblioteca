import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../../material.module';
import { CrudComponent } from "../../formulario/components/crud/crud.component";
import { ErroModule } from '../../formulario/pipes/erro.module';
import { TelefonePipeModule } from '../../pesquisa/pipe/telefon-pipe.module';
import { GoogleBookComponent } from './google-books.component';
import { GoogleBookRoutingModule } from './google-books-routing.module';

@NgModule({
  declarations: [GoogleBookComponent],
  imports: [
    CommonModule,
    GoogleBookRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    CrudComponent,
    ErroModule,
    TelefonePipeModule,
]
})
export class GoogleBookModule { }
