import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DataValidator } from '../../../validators/data-validator';
import { EmprestimoService } from './service/emprestimo.service';
import { StatusLabelValue } from './models/enums/status';
import { Usuario } from '../usuario/models/usuario';
import { UsuarioService } from '../usuario/service/usuario.service';
import { LivroService } from '../livro/service/livro.service';
import { Livro } from '../livro/models/livro';
import { Emprestimo } from './models/emprestimo';
import { MatDialog } from '@angular/material/dialog';
import { LivrosDialogComponent } from './modal/modal-recomendacao.component';

@Component({
    selector: 'app-emprestimo',
    templateUrl: './emprestimo.component.html',
    styleUrls: ['./emprestimo.component.scss'],
    providers: [EmprestimoService, UsuarioService, LivroService]
})
export class EmprestimoComponent implements OnInit {

    public form!: FormGroup;
    public statusLabel = StatusLabelValue;
    public listaUsuario: Usuario[] = [];
    public listaLivro: Livro[] = [];

    public usuarioSelecionado: Boolean = false;
    constructor(
        private fb: FormBuilder,
        public http: HttpClient,
        public service: EmprestimoService,
        public usuarioService: UsuarioService,
        public livroService: LivroService,
        public dialog: MatDialog
    ) {
        this.criarForm();
        this.buscarLivros();
        this.buscarUsuarios();
        this.status.reset('ATIVO');
        this.dataDevolucao.disable();
    }

    ngOnInit() {
        this.status.valueChanges.subscribe(this.onChangeStatus.bind(this));
        this.usuario.valueChanges.subscribe(value => this.usuarioSelecionado = (value && value.id));
    }
    public criarForm(): void {
        this.form = this.fb.group({
            livro: [null, Validators.required],
            usuario: [null, Validators.required],
            dataEmprestimo: [null],
            dataDevolucao: [null],
            status: [null, Validators.required],
        });
        this.dataEmprestimo.setValidators(DataValidator.validar);
        this.dataDevolucao.setValidators(DataValidator.validar);
    }

    public get livro() {
        return this.form.get('livro') as FormGroup;
    }
    public get usuario() {
        return this.form.get('usuario') as FormGroup;
    }
    public get dataEmprestimo() {
        return this.form.get('dataEmprestimo') as FormGroup;
    }

    public get dataDevolucao() {
        return this.form.get('dataDevolucao') as FormGroup;
    }
    public get status() {
        return this.form.get('status') as FormGroup;
    }

    public onChangeStatus(value) {
        if (value === 'ATIVO') {
            this.dataDevolucao.reset();
            this.dataDevolucao.disable();
        } else {
            this.dataDevolucao.enable();
        }

        this.dataDevolucao.updateValueAndValidity();
    }

    public buscarUsuarios() {
        this.usuarioService.lerTodos().subscribe((itens: any) => {
            this.listaUsuario = itens;
        })
    }

    public buscarLivros() {
        this.livroService.lerTodos().subscribe((itens: any) => {
            this.listaLivro = itens;
        })
    }

    public carregouRegistro(registro: Emprestimo) {
    }

    public buscarRecomendacao() {
        const usuarioId = this.usuario.value?.id;
        if(!usuarioId) return;
        this.service.buscarRecomendacao(usuarioId).subscribe(item => this.abrirDialogLivros(item));
    }

    compare(o1: any, o2: any): boolean {
        return o1 && o2 ? o1.id === o2.id : o1 === o2;
    }

    abrirDialogLivros(listaDeLivros: any) {
        const dialogRef = this.dialog.open(LivrosDialogComponent, {
            width: '580px',
            height: '500px',
            data: listaDeLivros
        });
        dialogRef.componentInstance.onLivroSelecionado.subscribe((livro: Livro) => {
           this.livro.setValue(livro);
        });
    }

}

