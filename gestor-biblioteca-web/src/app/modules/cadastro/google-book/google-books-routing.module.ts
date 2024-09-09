import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GoogleBookComponent } from './google-books.component';

const routes: Routes = [
  {
    path: '', redirectTo: 'buscar', pathMatch: 'full'
  },
  {
    path: 'buscar', component: GoogleBookComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GoogleBookRoutingModule { }
