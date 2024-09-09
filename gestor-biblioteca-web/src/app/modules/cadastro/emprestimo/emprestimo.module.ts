import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../../material.module';
import { CrudComponent } from "../../formulario/components/crud/crud.component";
import { ErroModule } from '../../formulario/pipes/erro.module';
import { EmprestimoComponent } from './emprestimo.component';
import { EmprestimoRoutingModule } from './emprestimo-routing.module';
import { LivrosDialogComponent } from './modal/modal-recomendacao.component';
import { TelefonePipeModule } from '../../pesquisa/pipe/telefon-pipe.module';

@NgModule({
  declarations: [EmprestimoComponent, LivrosDialogComponent],
  imports: [
    CommonModule,
    EmprestimoRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    CrudComponent,
    ErroModule,
    TelefonePipeModule,
]
})
export class EmprestimoModule { }
