import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PesquisaComponent } from '../../pesquisa/pesquisa.component';
import { PesquisaResolveService } from '../../pesquisa/services/pesquisa-resolve.service';
import { PESQUISA_EMPRESTIMO_CONFIG } from './core/emprestimo-pesquisa-config';
import { EmprestimoComponent } from './emprestimo.component';

const routes: Routes = [
  {
    path: '', redirectTo: 'pesquisa', pathMatch: 'full'
  },
  {
    path: 'pesquisa', component: PesquisaComponent,
    data: PESQUISA_EMPRESTIMO_CONFIG,
    resolve: {
      registros: PesquisaResolveService
    }
  },
  {
    path: 'novo', component: EmprestimoComponent
  },
  {
    path: ':id', component: EmprestimoComponent
  },
  {
    path: ':id/:delete', component: EmprestimoComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmprestimoRoutingModule { }
