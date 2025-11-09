# Fullstack Mino - File Storage & Media Management Application

A simple fullstack project demonstrating a file storage and media management system using Spring Boot (Backend), React (Frontend), PostgreSQL, MinIO, and NGINX.

## 🔹 Project Overview

This application allows users to:
- Upload media files (images, videos, etc.) through the frontend.
- Store media content securely in a MinIO bucket.
- Persist metadata (file URL and ID) in PostgreSQL.
- Retrieve and display media files in the frontend via API calls.

### 🎥 Demo
[Watch the demo](demo.mp4)



## 🔹 Architecture
![Web application demo showing the file upload workflow and resulting gallery. In a browser window a user cursor clicks an upload control, a progress indicator appears, and a new media thumbnail is added to a gallery grid. Visible interface elements include an upload button, thumbnail grid, and filenames or metadata near thumbnails. The environment is a desktop browser running a local development demo. Tone is neutral and functional, demonstrating successful upload and immediate display.](arch.png)

Frontend <--> NGINX <--> Backend <--> [PostgreSQL, MinIO]

- **Frontend**: Sends GET /medias to fetch media metadata and POST /upload to upload new files.
- **NGINX**: Acts as a reverse proxy between frontend and backend.
- **Backend**:
    - Generates file URLs using `ServletUriComponentsBuilder`.
    - Saves metadata (url_file, id) to PostgreSQL.
    - Saves actual file content to a predefined MinIO bucket.
- **PostgreSQL**: Stores media metadata (URL, ID).
- **MinIO**: Stores actual media content.

## 🔹 Key Features
- **File Upload**: Upload files from frontend to backend and store in MinIO.
- **Metadata Management**: Maintain media URLs and IDs in PostgreSQL.
- **File Retrieval**: Fetch list of media with URLs to display on frontend.
- **Containerized Environment**: Easily deploy backend, frontend, and MinIO using Docker Compose.
- **NGINX Proxy**: Serve frontend and route API calls to backend seamlessly.

## 🔹 Technologies Used
- **Backend**: Java, Spring Boot, Maven
- **Frontend**: React, Axios
- **Database**: PostgreSQL
- **Object Storage**: MinIO
- **Reverse Proxy**: NGINX
- **Containerization**: Docker, Docker Compose

## 🔹 File URL Generation

The backend generates file URLs dynamically:
```java
String url_file = ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path("/api/files/")
        .path(fileName)
        .toUriString();
```

## 🚀 How to Run

### Prerequisites
- Docker
- Docker Compose

### Steps
1. Clone the repository:
     ```bash
     git clone [https://github.com/ouerghi01/fullstack_mino.git](https://github.com/ouerghi01/fullstack_mino.git)
     cd fullstack_mino
     ```
     (Note: This URL is based on your profile. Update it if the repo is elsewhere.)
     
2. Build and start all services using Docker Compose:
     ```bash
     docker-compose up --build -d
     ```
     (The -d flag runs the containers in detached mode.)

3. Access the frontend in your browser:
     ```
     http://localhost:4200
     ```

Use the application to upload files and view stored media.

### Stopping the Application
To stop and remove the containers, run:
```bash
docker-compose down
```

## 🔹 Project Structure
```
fullstack_mino/
├─ backend/         # Spring Boot application
├─ frontend/        # React frontend
├─ docker-compose.yml # Container orchestration
├─ nginx.conf       # NGINX configuration
```



## 🔹 Author
**Mohamed Aziz Ouerghi**  
[LinkedIn](https://www.linkedin.com/in/mohamed-aziz-ouerghi/)  
[GitHub](https://github.com/ouerghi01)