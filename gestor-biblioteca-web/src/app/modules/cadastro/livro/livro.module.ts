import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsuarioRoutingModule } from './livro-routing.module';
import { LivroComponent } from './livro.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../../material.module';
import { CrudComponent } from "../../formulario/components/crud/crud.component";
import { ErroModule } from '../../formulario/pipes/erro.module';

@NgModule({
  declarations: [LivroComponent],
  imports: [
    CommonModule,
    UsuarioRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    CrudComponent,
    ErroModule,
]
})
export class LivroModule { }
