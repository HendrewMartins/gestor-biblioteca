import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UsuarioService } from './service/usuario.service';
import { DataValidator } from '../../../validators/data-validator';
import { EmailValidator } from '../../../validators/email-validator';

@Component({
    selector: 'app-usuario',
    templateUrl: './usuario.component.html',
    styleUrls: ['./usuario.component.scss'],
    providers: [UsuarioService]
})
export class UsuarioComponent implements OnInit {

    public form!: FormGroup;

    constructor(
        private fb: FormBuilder,
        public http: HttpClient,
        public service: UsuarioService
    ) {
        this.criarForm();
    }

    ngOnInit() {
    }

    public criarForm(): void {
        this.form = this.fb.group({
            nome: [null, Validators.compose([
                Validators.required,
                Validators.minLength(3),
                Validators.maxLength(100)
            ])],
            email: [null],
            dataCadastro: [null],
            telefone: [null, Validators.compose([
                Validators.required,
                Validators.minLength(10),
                Validators.maxLength(11)
            ])],
        });
        this.email.setValidators(EmailValidator.validar());
        this.dataCadastro.setValidators(DataValidator.validar);
    }

    public get nome() {
        return this.form.get('nome') as FormGroup;
    }
    public get email() {
        return this.form.get('email') as FormGroup;
    }
    public get dataCadastro() {
        return this.form.get('dataCadastro') as FormGroup;
    }

    public get telefone() {
        return this.form.get('telefone') as FormGroup;
    }

}

