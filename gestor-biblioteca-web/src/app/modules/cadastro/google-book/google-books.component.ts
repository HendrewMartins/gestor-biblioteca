import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { Book } from './models/book';
import { GoogleBookService } from './service/google-book.service';
import { FormControl } from '@angular/forms';

@Component({
    selector: 'app-google-book',
    templateUrl: './google-books.component.html',
    styleUrls: ['./google-books.component.scss'],
    providers: [GoogleBookService]
})
export class GoogleBookComponent implements OnInit {
    public books: Book[] = [];

    displayedColumns: string[] = ['title', 'authors', 'publishedDate', 'category', 'isbn', 'action'];

    searchControl = new FormControl();

    constructor(
        public service: GoogleBookService
    ) {
    }
    ngOnInit(): void {
    }

    public onSelectBook(book: Book) {
        this.service.addBook(book).subscribe(item => {
            alert(`O registro com código foi salvo com sucesso!`);
        }, error => {
            alert('Ocorreu um erro ao salvar o registro registro: ' + (error?.error?.message ? error.error.message : ''));
        });
    }

    public onSearch() {
        if (!this.searchControl.value) return;
        this.service.search(this.searchControl.value).subscribe(res => {
            this.books = res;
        },error => {
            alert('Ocorreu um erro ao buscar registro: ' + (error?.error?.message ? error.error.message : ''));
        });
    }
}
