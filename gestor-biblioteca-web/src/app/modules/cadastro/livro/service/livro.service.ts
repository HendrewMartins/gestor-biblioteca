import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Api } from '../../../../services/api';
import { Livro } from '../models/livro';

@Injectable()
export class LivroService extends Api<Livro> {

    constructor(
        public override http: HttpClient,
    ) {
        super(http, 'v1/livro');
    }
}
