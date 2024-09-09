import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Api } from '../../../../services/api';
import { Emprestimo } from '../models/emprestimo';
import { map, Observable } from 'rxjs';
import { Livro } from '../../livro/models/livro';

@Injectable()
export class EmprestimoService extends Api<Emprestimo> {

    constructor(
        public override http: HttpClient,
    ) {
        super(http, 'v1/emprestimo');
    }

    public buscarRecomendacao(usuarioId: number): Observable<Livro[]> {
        return this.http.get<Livro[]>(this.url+'/recomendacao/'+ usuarioId ).pipe(map((item: any) => {
            return item;
        }));
    }
}
