import { HttpClient, HttpParams } from "@angular/common/http";
import { environment } from "../../../../../environments/environment";
import { Book } from "../models/book";
import { map, Observable } from "rxjs";
import { Injectable } from "@angular/core";

@Injectable()
export class GoogleBookService {

    public url: string;

    constructor(
        public http: HttpClient,
    ) {
        this.url = environment.api + '/api/v1/google-books' ;
    }

    public addBook(book: Book): Observable<any> {
        return this.http.post<any>(this.url+'/add', book).pipe(map((item: any) => {
            return item;
        }));
    }

    public search(title: string): Observable<Book[]> {
        const params = new HttpParams().set('title', title);
        return this.http.get<Book[]>(`${this.url}/search`, { params }).pipe(
            map((response: any) => {
                return response; 
            })
        );
    }

}