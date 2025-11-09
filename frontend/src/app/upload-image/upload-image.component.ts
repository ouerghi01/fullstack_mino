import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

interface MediaDto {
  id: number;
  urlFile: string;
}

@Component({
  selector: 'app-upload-image',
  imports: [CommonModule],
  templateUrl: './upload-image.component.html',
  styleUrls: ['./upload-image.component.css']
})
export class UploadImageComponent {
  selectedFile: File | null = null;
  medias: MediaDto[] = [];
 uploadUrl = 'http://localhost:8080/api/files/upload';
getMediasUrl = 'http://localhost:8080/api/medias';


  loading = false;
  message = '';

  constructor(private http: HttpClient) {
    this.getMedias();
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  uploadImage() {
    if (!this.selectedFile) {
      this.message = 'Please select an image first.';
      return;
    }

    const formData = new FormData();
    formData.append('file', this.selectedFile); // match your backend field name

    this.loading = true;
    this.http.post(this.uploadUrl, formData).subscribe({
      next: () => {
        this.message = 'Image uploaded successfully!';
        this.loading = false;
        this.getMedias(); // refresh the list after upload
      },
      error: (err) => {
        console.error(err);
        this.message = 'Upload failed.';
        this.loading = false;
      }
    });
  }

  getMedias() {
    this.http.get<MediaDto[]>(this.getMediasUrl).subscribe({
      next: (data) => this.medias = data,
      error: (err) => console.error(err)
    });
  }
}
