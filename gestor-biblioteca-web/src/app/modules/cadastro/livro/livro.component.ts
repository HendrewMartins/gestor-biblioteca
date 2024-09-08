import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LivroService } from './service/livro.service';
import { DataValidator } from '../../../validators/data-validator';
import { CategoriaLivroLabelValue } from './models/enums/categoria-livro';

@Component({
    selector: 'app-livro',
    templateUrl: './livro.component.html',
    styleUrls: ['./livro.component.scss'],
    providers: [LivroService]
})
export class LivroComponent implements OnInit {

    public form!: FormGroup;
    public categorias = CategoriaLivroLabelValue;

    constructor(
        private fb: FormBuilder,
        public http: HttpClient,
        public service: LivroService
    ) {
        this.criarForm();
    }

    ngOnInit() {
    }
    public criarForm(): void {
        this.form = this.fb.group({
            titulo: [null, Validators.compose([
                Validators.required,
                Validators.minLength(3),
                Validators.maxLength(100)
            ])],
            autor: [null,  Validators.required],
            dataPublicacao: [null],
            isbn: [null, Validators.required],
            categoria: [null, Validators.required],
        });
        this.dataPublicacao.setValidators(DataValidator.validar);
    }

    public get titulo() {
        return this.form.get('titulo') as FormGroup;
    }
    public get autor() {
        return this.form.get('autor') as FormGroup;
    }
    public get isbn() {
        return this.form.get('isbn') as FormGroup;
    }
    public get dataPublicacao() {
        return this.form.get('dataPublicacao') as FormGroup;
    }

    public get categoria() {
        return this.form.get('categoria') as FormGroup;
    }

}

