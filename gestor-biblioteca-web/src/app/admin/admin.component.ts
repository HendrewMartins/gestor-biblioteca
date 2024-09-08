import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../modules/material.module';
import { RouterLink, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss'],
  standalone: true,
  imports: [
    MaterialModule,
    RouterLink,
    RouterOutlet,
    CommonModule
  ],
})
export class AdminComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
