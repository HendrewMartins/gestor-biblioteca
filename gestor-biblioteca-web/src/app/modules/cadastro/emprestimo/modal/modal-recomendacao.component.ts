import { Component, Input, Output, EventEmitter, Inject, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { Livro } from '../../livro/models/livro';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';


@Component({
  selector: 'app-modal-recomendacao',
  templateUrl: './modal-recomendacao.component.html',
  styleUrls: ['./modal-recomendacao.component.scss']
})
export class LivrosDialogComponent implements OnInit, OnChanges {
  dataSource: MatTableDataSource<Livro>;
  @Input() displayDialog: boolean = false;
  @Output() onLivroSelecionado = new EventEmitter<Livro>();

  displayedColumns: string[] = ['titulo', 'autor', 'dataPublicacao', 'categoria', 'action'];

  constructor(
    public dialogRef: MatDialogRef<LivrosDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public livros: Livro[]
  ) {
    this.dataSource = new MatTableDataSource(livros);
  }
  ngOnChanges(changes: SimpleChanges): void {
  }
  ngOnInit(): void {

  }

  fecharDialog() {
    this.dialogRef.close();
  }

  selecionarLivro(livro: Livro) {
    this.onLivroSelecionado.emit(livro);
    this.dialogRef.close();
  }

}
