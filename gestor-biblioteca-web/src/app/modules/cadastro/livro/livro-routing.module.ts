import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PesquisaComponent } from '../../pesquisa/pesquisa.component';
import { PesquisaResolveService } from '../../pesquisa/services/pesquisa-resolve.service';
import { PESQUISA_LIVRO_CONFIG } from './core/livro-pesquisa-config';
import { LivroComponent } from './livro.component';

const routes: Routes = [
  {
    path: '', redirectTo: 'pesquisa', pathMatch: 'full'
  },
  {
    path: 'pesquisa', component: PesquisaComponent,
    data: PESQUISA_LIVRO_CONFIG,
    resolve: {
      registros: PesquisaResolveService
    }
  },
  {
    path: 'novo', component: LivroComponent
  },
  {
    path: ':id', component: LivroComponent
  },
  {
    path: ':id/:delete', component: LivroComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LivroRoutingModule { }
