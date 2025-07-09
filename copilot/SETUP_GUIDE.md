# Digital Bank Application - Complete Setup Guide

## 🏦 Project Overview
This is a comprehensive multi-platform banking application that has been completely rebranded from "Wipro Bank" to "Digital Bank". The application includes:

- **Java Console Application** - Backend banking system
- **Web Application** - Modern web interface with login system
- **Mobile App** - React Native app for iOS and Android
- **Admin Dashboard** - Separate interface for administrators

## 🔐 Login System Features

### User Authentication
- **User Login**: `user` / `user123`
- **Admin Login**: `admin` / `admin123`
- **Additional Demo Users**:
  - `john` / `john123` (User)
  - `jane` / `jane123` (User)
  - `manager` / `manager123` (Admin)
  - `supervisor` / `super123` (Admin)

### Security Features
- Session management with localStorage
- Role-based access control
- Automatic redirects based on user role
- Protected routes for admin and user areas
- Auto-logout functionality

## 🚀 Quick Start

### Web Application
1. **Start the Web Server**:
   ```bash
   # Option 1: Using the provided script
   start-web-server.bat
   
   # Option 2: Manual start
   cd "d:\Coding pratice\vccode\copilot"
   python -m http.server 3000
   ```

2. **Access the Application**:
   - Login Page: `http://localhost:3000/web-app/login.html`
   - User Dashboard: `http://localhost:3000/web-app/index.html` (after login)
   - Admin Dashboard: `http://localhost:3000/web-app/admin.html` (admin only)

### Java Console Application
1. **Build the Application**:
   ```bash
   # Windows
   build.bat
   
   # Linux/Mac
   ./build.sh
   ```

2. **Run the Application**:
   ```bash
   # Windows
   run.bat
   
   # Linux/Mac
   ./run.sh
   ```

## 📱 Mobile App (React Native)

### iOS Setup
1. **Install Dependencies**:
   ```bash
   cd mobile-app
   npm install
   cd ios
   pod install
   ```

2. **Run on iOS**:
   ```bash
   npx react-native run-ios
   ```

### Android Setup
1. **Run on Android**:
   ```bash
   npx react-native run-android
   ```

## 🖥️ Web Application Features

### User Dashboard
- Customer management
- Account operations
- Fund transfers
- Transaction history
- Reports and analytics

### Admin Dashboard
- System statistics
- User management
- Advanced reporting
- System configuration
- Real-time monitoring

## 🔧 Configuration

### Database Setup
- **MySQL Configuration**: Edit `application.properties`
- **H2 Database**: Available for testing
- **Database Scripts**: Run `database_setup.sql`

### API Configuration
- **Backend API**: Located in `backend/` directory
- **Spring Boot**: Port 8080
- **REST Endpoints**: Full CRUD operations

## 📁 Project Structure

```
digital-bank/
├── src/main/java/com/wipro/bank/          # Java backend (package names unchanged)
├── web-app/                               # Web application
│   ├── login.html                         # Login page
│   ├── index.html                         # User dashboard
│   ├── admin.html                         # Admin dashboard
│   └── app.js                             # Main JavaScript
├── mobile-app/                            # React Native app
├── backend/                               # Spring Boot REST API
├── build/                                 # Compiled Java classes
└── database_setup.sql                     # Database schema
```

## 🎯 Key Changes Made

### Branding Updates
- ✅ Removed all "Wipro" references from UI
- ✅ Updated to "Digital Bank" branding
- ✅ Maintained Java package structure for compatibility

### Authentication System
- ✅ New login page with role selection
- ✅ Session management system
- ✅ Role-based access control
- ✅ Protected routes and redirects

### User Experience
- ✅ Modern, responsive design
- ✅ Separate user and admin interfaces
- ✅ Interactive dashboard with real-time updates
- ✅ Mobile-friendly responsive layout

## 🛠️ Development Tools

### VS Code Tasks
- **Build**: `Ctrl+Shift+P` → "Run Task" → "Build Bank Application"
- **Run**: `Ctrl+Shift+P` → "Run Task" → "Run Bank Application"
- **Clean**: `Ctrl+Shift+P` → "Run Task" → "Clean Build"

### Testing
- **Demo Credentials**: Available in login page
- **Local Testing**: Use provided HTTP server
- **API Testing**: Postman collection available

## 🔒 Security Considerations

- All user inputs are validated
- SQL injection prevention with PreparedStatement
- Session-based authentication
- Role-based access control
- Secure password handling (demo only)

## 📧 Deployment & Distribution

### Email Distribution
- HTML app file: `iPhone13_Banking_App.html`
- Setup instructions: `iPhone13_Setup_Instructions.md`
- Email template: `Email_Template.txt`

### Production Deployment
- Configure production database
- Update API endpoints
- Set up SSL certificates
- Deploy to cloud platform

## 🐛 Troubleshooting

### Common Issues
1. **Port 3000 in use**: Use different port `python -m http.server 8080`
2. **Database connection**: Check MySQL service and credentials
3. **Login issues**: Clear browser localStorage and try again
4. **Build errors**: Ensure Java classpath includes MySQL connector

### Support
- Check console for JavaScript errors
- Verify server is running on correct port
- Ensure all dependencies are installed
- Review browser developer tools

## 🎉 Success!

Your Digital Bank application is now ready with:
- ✅ Complete "Wipro" branding removal
- ✅ Modern login system with user/admin roles
- ✅ Responsive web interface
- ✅ Mobile app support
- ✅ Full authentication and session management

Access your application at: `http://localhost:3000/web-app/login.html`

**Happy Banking! 🏦**
