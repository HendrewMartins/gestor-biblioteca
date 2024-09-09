import { Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';

export const routes: Routes = [
    { path: '', redirectTo: 'admin', pathMatch: 'full' },
    {
        path: 'admin', component: AdminComponent,

        children: [
            { path: 'usuario', loadChildren: () => import('./modules/cadastro/usuario/usuario.module').then(m => m.UsuarioModule) },
            { path: 'livro', loadChildren: () => import('./modules/cadastro/livro/livro.module').then(m => m.LivroModule) },
            { path: 'emprestimo', loadChildren: () => import('./modules/cadastro/emprestimo/emprestimo.module').then(m => m.EmprestimoModule) },
        ]
    }
];
