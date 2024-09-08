import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ColunaConfig } from './models/coluna-config';
import { PesquisaConfig } from './models/pesquisa-config';
import { CommonModule, DatePipe } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { TelefonePipeModule } from './pipe/telefon-pipe.module';

@Component({
    selector: 'app-pesquisa',
    templateUrl: 'pesquisa.component.html',
    providers: [PesquisaComponent],
    styleUrls: ['pesquisa.component.scss'],
    standalone: true,
    imports: [
        CommonModule,
        MatTableModule,
        MatCardModule,
        MatMenuModule,
        MatButtonModule,
        MatIconModule,
        DatePipe,
        TelefonePipeModule
    ]
})

export class PesquisaComponent implements OnInit {

    public displayedColumns: any = [];

    public colunas: ColunaConfig[];

    public dataSource: any = [];

    constructor(
        private route: ActivatedRoute,
        private router: Router,
    ) {
        const config: PesquisaConfig = this.route.snapshot.data as any;
        this.displayedColumns = [...config.colunas.map(col => col.nome), 'action'];
        this.colunas = config.colunas;
        this.dataSource = config.registros;
    }

    ngOnInit() { }

    public editar(value: any) {
        this.router.navigate([value.id], { relativeTo: this.route.parent });
    }

    public cadastrar() {
        this.router.navigate(['novo'], { relativeTo: this.route.parent });
    }

    public excluir(value: any) {
        this.router.navigate([value.id, 'delete'], { relativeTo: this.route.parent });
    }

}
