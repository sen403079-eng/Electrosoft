# 📱 Electrosoft - Microsoft 365 Type Document Management Platform

A comprehensive cross-platform document management application supporting all major document formats with full read and editing capabilities.

## ✨ Features

### 📄 Document Support
- **Microsoft Office**: .docx, .xlsx, .pptx, .doc, .xls, .ppt
- **OpenDocument**: .odt, .ods, .odp
- **Other Formats**: .pdf, .txt, .rtf, .csv, .json, .xml
- **Images**: .jpg, .png, .gif, .bmp, .svg, .webp

### 🔧 Core Capabilities
✅ Full read and edit functionality  
✅ Real-time cloud synchronization  
✅ Document versioning & history  
✅ User authentication & authorization  
✅ Document sharing with permissions  
✅ OCR & text extraction  
✅ Format conversion  
✅ Document compression  
✅ Comments & collaboration  
✅ Offline mode support  

### 🖥️ Platform Support
- **Android**: Kotlin with Jetpack Compose UI
- **Windows 10**: .NET MAUI native integration
- **Desktop**: Electron cross-platform
- **Backend**: Node.js REST API + Python processing service

## 🏗️ Project Structure

```
electrosoft/
├── backend/
│   ├── node-server/              # Express.js REST API
│   │   ├── src/
│   │   │   ├── models/           # Database models
│   │   │   ├── routes/           # API endpoints
│   │   │   ├── middleware/       # Auth & validation
│   │   │   └── server.js         # Main server
│   │   ├── Dockerfile
│   │   └── package.json
│   └── python-service/           # Document processing
│       ├── src/
│       │   ├── app.py            # Flask application
│       │   └── document_processor.py
│       ├── Dockerfile
│       └── requirements.txt
├── mobile/
│   └── android-kotlin/           # Android application
│       ├── app/
│       │   └── src/main/java/com/electrosoft/documentapp/
│       └── build.gradle
├── desktop/
│   ├── electron-app/             # Electron wrapper
│   └── maui-windows/             # .NET MAUI Windows 10
├── docker-compose.yml
├── nginx.conf
└── README.md
```

## 🚀 Quick Start

### Prerequisites
- Docker & Docker Compose
- Node.js 18+ (for local development)
- Python 3.11+ (for local development)
- Kotlin & Android SDK (for Android development)
- .NET 8.0 (for Windows development)

### Using Docker Compose (Recommended)

```bash
# Clone repository
git clone https://github.com/sen403079-eng/Electrosoft.git
cd Electrosoft

# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

Services will be available at:
- **API Server**: http://localhost:5000
- **Python Service**: http://localhost:5001
- **MongoDB**: localhost:27017
- **Redis**: localhost:6379

### Local Development Setup

#### Node.js Backend

```bash
cd backend/node-server
npm install
cp .env.example .env
npm run dev
```

#### Python Service

```bash
cd backend/python-service
pip install -r requirements.txt
cp .env.example .env
python src/app.py
```

#### Android Development

```bash
cd mobile/android-kotlin
./gradlew build
# Open in Android Studio
```

## 📚 API Documentation

### Authentication Endpoints

#### Register
```http
POST /api/v1/auth/register
Content-Type: application/json

{
  "username": "user123",
  "email": "user@example.com",
  "password": "password123",
  "confirmPassword": "password123"
}
```

#### Login
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

### Document Endpoints

#### Upload Document
```http
POST /api/v1/documents/upload
Authorization: Bearer {token}
Content-Type: multipart/form-data

file: [binary]
description: "My Document"
```

#### Get All Documents
```http
GET /api/v1/documents?status=active&page=1&limit=10
Authorization: Bearer {token}
```

#### Get Document
```http
GET /api/v1/documents/{documentId}
Authorization: Bearer {token}
```

#### Share Document
```http
POST /api/v1/documents/{documentId}/share
Authorization: Bearer {token}
Content-Type: application/json

{
  "userId": "user_id",
  "permission": "view"
}
```

#### Download Document
```http
GET /api/v1/documents/{documentId}/download
Authorization: Bearer {token}
```

### Document Processing Endpoints

#### Convert Document
```http
POST /api/v1/convert
Content-Type: multipart/form-data

file: [binary]
format: "pdf"
```

#### Extract Text (OCR)
```http
POST /api/v1/extract-text
Content-Type: multipart/form-data

file: [binary]
```

#### Get Preview
```http
POST /api/v1/preview
Content-Type: multipart/form-data

file: [binary]
page: 1
```

#### Get Metadata
```http
POST /api/v1/get-metadata
Content-Type: multipart/form-data

file: [binary]
```

## 🔐 Security Features

- ✅ JWT token-based authentication
- ✅ Password hashing with bcryptjs
- ✅ Rate limiting (100 req/15 min)
- ✅ CORS protection
- ✅ Helmet security headers
- ✅ Account lockout after failed attempts
- ✅ Two-factor authentication ready
- ✅ Role-based access control (RBAC)

## 💾 Database Schema

### Users Collection
- `username`: string
- `email`: string (unique)
- `password`: hashed string
- `role`: 'user' | 'admin' | 'moderator'
- `storageQuota`: number (default: 10GB)
- `storageUsed`: number
- `isActive`: boolean
- `twoFactorEnabled`: boolean
- `timestamps`: createdAt, updatedAt

### Documents Collection
- `filename`: string
- `originalName`: string
- `fileType`: string (enum)
- `owner`: ObjectId (User reference)
- `fileSize`: number
- `filePath`: string
- `textContent`: string (OCR results)
- `versions`: array of version objects
- `sharedWith`: array of share objects
- `comments`: array of comment objects
- `metadata`: object
- `timestamps`: createdAt, updatedAt

## 🔄 Real-Time Sync

Uses Redis pub/sub for:
- Document updates
- Collaborative edits
- User presence
- Comment notifications

## 📱 Platform Features

### Android (Kotlin)
- Native Android UI with Jetpack Compose
- Room database for offline storage
- Retrofit HTTP client
- Document preview & editing
- Background sync
- Material Design 3

### Windows 10 (.NET MAUI)
- Native Windows 10 integration
- XAML UI
- Local file system integration
- Touch & pen support
- Windows taskbar integration

### Desktop (Electron)
- Cross-platform (Windows, macOS, Linux)
- React-based UI
- Drag & drop support
- System tray integration
- Auto-update capability

## 🧪 Testing

```bash
# Run Node.js tests
cd backend/node-server
npm test

# Run Android tests
cd mobile/android-kotlin
./gradlew test
```

## 📊 Storage & Performance

- **Max File Size**: 500MB
- **Default Storage Quota**: 10GB per user
- **Redis Cache**: 1-hour TTL for documents
- **Database Indexes**: Optimized queries
- **CDN Ready**: Static asset delivery

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

## 📝 Environment Variables

### Node.js Server
```env
PORT=5000
NODE_ENV=development
JWT_SECRET=your_jwt_secret
MONGODB_URI=mongodb://localhost:27017/electrosoft
REDIS_URL=redis://localhost:6379
CORS_ORIGIN=http://localhost:3000
```

### Python Service
```env
PORT=5001
DEBUG=False
UPLOAD_FOLDER=/tmp/electrosoft-uploads
TEMP_DIR=/tmp/electrosoft
```

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙋 Support

For support, email support@electrosoft.dev or open an issue on GitHub.

## 🗺️ Roadmap

- [ ] Real-time collaborative editing
- [ ] Advanced encryption for sensitive documents
- [ ] Machine learning-based document classification
- [ ] Video & audio support
- [ ] Integration with cloud storage (OneDrive, Google Drive, Dropbox)
- [ ] Advanced permission management
- [ ] Document workflow automation
- [ ] Mobile app signing & publishing

---

**Built with ❤️ by Electrosoft Team**
