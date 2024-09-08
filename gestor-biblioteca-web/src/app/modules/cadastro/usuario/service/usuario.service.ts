import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Usuario } from '../models/usuario';
import { Api } from '../../../../services/api';

@Injectable()
export class UsuarioService extends Api<Usuario> {

    constructor(
        public override http: HttpClient,
    ) {
        super(http, 'v1/usuario');
    }
}
