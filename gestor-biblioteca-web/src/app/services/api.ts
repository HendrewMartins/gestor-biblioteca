import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '../../environments/environment';

export class Api<T> {

    public url: string;

    constructor(
        public http: HttpClient,
        public path: string
    ) {
        this.url = environment.api + '/api/' + path;
    }

    public criar(obj: T): Observable<T> {
        return this.http.post<T>(this.url, obj).pipe(map((item: any) => {
            return item;
        }));
    }

    public ler(id: number): Observable<T> {
        return this.http.get<T>(this.url + '/' + id).pipe(map((item: any) => {
            return item;
        }));
    }

    public alterar(obj: T, id: number): Observable<T> {
        return this.http.put<T>(`${this.url}/${id}`, obj).pipe(map((item: any) => {
            return item;
        }));
    }

    public deletar(id: number): Observable<void> {
        return this.http.delete<void>(`${this.url}/${id}`);
    }

    public lerTodos(): Observable<T> {
        return this.http.get<T>(this.url ).pipe(map((item: any) => {
            return item;
        }));
    }

}
