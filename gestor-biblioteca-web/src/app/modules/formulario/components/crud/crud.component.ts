import { Component, OnInit, ChangeDetectionStrategy, Input, OnDestroy, AfterViewInit } from '@angular/core';
import { FormGroup, FormsModule, NgForm, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../material.module';
import { Api } from '../../../../services/api';

@Component({
    selector: 'app-crud',
    templateUrl: './crud.component.html',
    styleUrls: ['./crud.component.scss'],
    changeDetection: ChangeDetectionStrategy.Default,
    standalone: true,
    imports: [
        MaterialModule,
        CommonModule,
        ReactiveFormsModule,
        FormsModule,
    ],
})
export class CrudComponent implements OnInit, OnDestroy {

    @Input() public form: FormGroup;

    @Input() public titulo: string;

    @Input() public api: Api<any>;

    public registroId: number = 0;

    private subscription!: Subscription;

    constructor(
        private route: ActivatedRoute,
        private router: Router
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.ler(params['id']);
            if (params['delete'] === 'delete') {
                this.deletar();
            }
        });
    }

    private ler(id: number) {
        if (id) {
            this.registroId = id;
            this.api.ler(id).subscribe((registro) => {
                if (registro) {
                    this.form.patchValue(registro);
                }
            }, error => {
                console.error(error);
                alert('Não foi possível encontrar  o registro ' + id);
                this.abrirNovoRegistro();
            });
        }
    }

    public salvar() {
        if (this.form.valid) {
            if (this.registroId) {
                this.alterar();
            } else {
                this.criar();
            }
        }
    }

    private alterar() {
        const value = this.form.value;
        value.id = this.registroId;
        this.api.alterar(value, this.registroId).subscribe(registro => {
            if (registro) {
                this.form.patchValue(registro);
                alert(`Registro ${this.registroId} foi salvo com sucesso`);
            }
        }, error => {
            console.error(error);
            alert('Erro ao alterar o registro');
        });
    }

    private criar() {
        this.api.criar(this.form.value).subscribe((registro) => {
            alert(`O registro com código ${registro.id} foi salvo com sucesso!`);
            this.limpar();

        }, error => {
            console.error(error);
            alert('Ocorreu um erro ao salvar o registro registro');
        });
    }

    public limpar() {
        if (this.registroId) {
            this.abrirNovoRegistro();
        } else {
            this.form.reset();
        }
    }

    private abrirNovoRegistro() {
        this.router.navigate(['novo'], { relativeTo: this.route.parent });
    }

    public deletar() {
        this.api.deletar(this.registroId).subscribe(() => {
            alert('Registro deletado com sucesso!');
            this.limpar();
        }, error => {
            console.error(error);
            alert('Ocorreu uma falha ao deletar o registro');
        });
    }

    public voltarPesquisa() {
        this.router.navigate(['pesquisa'], { relativeTo: this.route.parent });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
