import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { UploadImageComponent } from './upload-image/upload-image.component';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,UploadImageComponent,    CommonModule,

    HttpClientModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';
}
