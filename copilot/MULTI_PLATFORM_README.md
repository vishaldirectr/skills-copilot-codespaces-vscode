# 🏦 Digital Bank - Multi-Platform Application

A comprehensive banking application supporting **Console**, **Web**, **iOS**, and **Android** platforms with a unified REST API backend.

## 🚀 **Architecture Overview**

```
┌─────────────────┬─────────────────┬─────────────────┐
│   Console App   │    Web App      │   Mobile Apps   │
│    (Java)       │   (HTML/JS)     │ (React Native)  │
└─────────────────┴─────────────────┴─────────────────┘
                           │
                  ┌─────────────────┐
                  │   REST API      │
                  │ (Spring Boot)   │
                  └─────────────────┘
                           │
                  ┌─────────────────┐
                  │   MySQL DB      │
                  │   (Database)    │
                  └─────────────────┘
```

## 📱 **Platform Support**

### 1. **Console Application** (Java)
- **Location**: `/src/main/java/`
- **Features**: Complete banking operations via console interface
- **Build**: `javac` + `jar` packaging
- **Run**: `java -jar BankApplication.jar`

### 2. **Web Application** (HTML5/JavaScript)
- **Location**: `/web-app/`
- **Features**: Modern responsive web interface
- **Technology**: Bootstrap 5, Vanilla JavaScript
- **Access**: `http://localhost:8080` (after starting backend)

### 3. **Mobile Application** (React Native)
- **Location**: `/mobile-app/`
- **Platforms**: iOS & Android
- **Technology**: React Native, React Navigation
- **Build**: `npx react-native run-android/ios`

### 4. **REST API Backend** (Spring Boot)
- **Location**: `/backend/`
- **Technology**: Spring Boot 3.2, MySQL, JPA
- **Port**: `8080`
- **Documentation**: `http://localhost:8080/swagger-ui`

## 🔧 **Quick Start Guide**

### **1. Database Setup**
```sql
-- Create database
CREATE DATABASE bank_db;

-- Run the database setup script
mysql -u root -p bank_db < database_setup.sql
```

### **2. Backend API (Spring Boot)**
```bash
cd backend
mvn spring-boot:run
# API will be available at http://localhost:8080
```

### **3. Console Application**
```bash
# H2 Database Version (Ready to run!)
javac -cp "h2-2.2.224.jar" -d build src/main/java/com/wipro/bank/H2DatabaseTest.java src/main/java/com/wipro/bank/util/H2DatabaseUtil.java
java -cp "h2-2.2.224.jar;build" com.wipro.bank.H2DatabaseTest

# MySQL Version (requires MySQL setup)
javac -cp mysql-connector-j-8.0.33.jar -d build src/main/java/com/wipro/bank/*.java src/main/java/com/wipro/bank/*/*.java
java -cp "mysql-connector-j-8.0.33.jar;build" com.wipro.bank.BankApplication
```

### **4. Web Application**
```bash
# Simply open web-app/index.html in a browser
# Or serve with a local server:
cd web-app
python -m http.server 3000
# Access at http://localhost:3000
```

### **5. Mobile Application**
```bash
cd mobile-app
npm install

# For Android
npx react-native run-android

# For iOS (macOS only)
npx react-native run-ios
```

## 📊 **Features Comparison**

| Feature | Console | Web | Mobile | API |
|---------|---------|-----|--------|-----|
| Customer Creation | ✅ | ✅ | ✅ | ✅ |
| Fund Transfer | ✅ | ✅ | ✅ | ✅ |
| Reports (Paginated) | ✅ | ✅ | ✅ | ✅ |
| Real-time Updates | ❌ | ✅ | ✅ | ✅ |
| Responsive Design | N/A | ✅ | ✅ | N/A |
| Offline Capability | ✅ | ❌ | Partial | ❌ |
| Push Notifications | ❌ | ❌ | ✅ | ❌ |

## 🛠 **Development Setup**

### **Prerequisites**
- **Java 8+** (for console app)
- **Java 17+** (for Spring Boot API)
- **Node.js 16+** (for mobile app)
- **MySQL 8.0+**
- **Android Studio** (for Android development)
- **Xcode** (for iOS development, macOS only)

### **Project Structure**
```
wipro-bank/
├── src/main/java/               # Console Application (Java)
│   └── com/wipro/bank/
├── backend/                     # REST API (Spring Boot)
│   ├── src/main/java/
│   └── pom.xml
├── web-app/                     # Web Application
│   ├── index.html
│   └── app.js
├── mobile-app/                  # React Native App
│   ├── src/
│   └── package.json
├── database_setup.sql           # Database schema
├── README.md                    # This file
└── .github/
    └── copilot-instructions.md  # Development guidelines
```

## 🔐 **Security Features**

### **Data Validation**
- **Input Sanitization**: All platforms validate user inputs
- **SQL Injection Prevention**: Prepared statements throughout
- **XSS Protection**: Web app sanitizes all outputs
- **Type Safety**: Strong typing in all platforms

### **API Security**
- **CORS Configuration**: Controlled cross-origin requests
- **Input Validation**: Bean validation on all endpoints
- **Error Handling**: No sensitive data in error responses
- **HTTPS Ready**: SSL/TLS configuration available

### **Database Security**
- **Connection Pooling**: Secure connection management
- **Constraints**: Foreign keys, check constraints
- **Encryption Ready**: Database encryption configurable

## 📱 **Mobile App Features**

### **iOS & Android Support**
- **Single Codebase**: React Native for both platforms
- **Native Performance**: Optimized for mobile devices
- **Responsive Design**: Adapts to different screen sizes
- **Touch Interactions**: Native touch gestures

### **Mobile-Specific Features**
- **Biometric Authentication**: Face ID, Touch ID, Fingerprint
- **Push Notifications**: Transaction alerts
- **Offline Storage**: Cache critical data
- **Camera Integration**: Document scanning

## 🌐 **Web App Features**

### **Modern Web Standards**
- **Responsive Design**: Mobile-first approach
- **Progressive Enhancement**: Works on all browsers
- **Fast Loading**: Optimized assets and code
- **Accessibility**: WCAG 2.1 compliant

### **User Experience**
- **Intuitive Interface**: Clean, modern design
- **Real-time Feedback**: Instant validation and updates
- **Toast Notifications**: User-friendly messages
- **Loading States**: Clear progress indicators

## 🔄 **API Endpoints**

### **Customer Management**
```
POST   /api/customers           # Create customer
GET    /api/customers/{id}      # Get customer
GET    /api/customers/health    # Health check
```

### **Fund Transfers**
```
POST   /api/transfers           # Transfer funds
GET    /api/transfers/history/{accountNo}  # Get history
GET    /api/transfers/health    # Health check
```

### **Reports**
```
GET    /api/reports/accounts    # Paginated account report
GET    /api/reports/summary     # Summary statistics
GET    /api/reports/health      # Health check
```

### **API Documentation**
- **Swagger UI**: `http://localhost:8080/swagger-ui`
- **OpenAPI Spec**: `http://localhost:8080/api-docs`

## 🚀 **Deployment Options**

### **Console Application**
```bash
# Create JAR
jar cfm BankApplication.jar MANIFEST.MF -C build com

# Deploy anywhere with Java
java -jar BankApplication.jar
```

### **Web Application**
```bash
# Static hosting (Netlify, Vercel, GitHub Pages)
# Simply upload web-app/ folder

# Or serve with any web server
nginx -s serve web-app/
```

### **Mobile Applications**
```bash
# Android APK
cd mobile-app/android
./gradlew assembleRelease

# iOS App Store
# Build through Xcode
```

### **Backend API**
```bash
# Docker deployment
docker build -t wipro-bank-api .
docker run -p 8080:8080 wipro-bank-api

# Cloud deployment (AWS, Azure, GCP)
# Deploy as Spring Boot application
```

## 📈 **Performance Optimizations**

### **Database**
- **Connection Pooling**: HikariCP for optimal connections
- **Indexing**: Strategic database indexes
- **Query Optimization**: Efficient SQL queries
- **Pagination**: Limit data transfer

### **API**
- **Caching**: Response caching where appropriate
- **Compression**: GZIP compression enabled
- **Rate Limiting**: Prevent API abuse
- **Load Balancing**: Horizontal scaling ready

### **Frontend**
- **Code Splitting**: Lazy loading of components
- **Asset Optimization**: Minified CSS/JS
- **Image Optimization**: Responsive images
- **CDN Integration**: Fast static asset delivery

## 🧪 **Testing Strategy**

### **Unit Testing**
- **Java**: JUnit 5 for console and API
- **JavaScript**: Jest for web app
- **React Native**: Jest + React Native Testing Library

### **Integration Testing**
- **API Testing**: Spring Boot Test
- **Database Testing**: Testcontainers
- **End-to-End**: Selenium for web, Detox for mobile

### **Test Coverage**
- **Target**: 80%+ code coverage
- **Automated**: CI/CD pipeline integration
- **Quality Gates**: SonarQube integration

## 📚 **Documentation**

### **Technical Documentation**
- **API Docs**: Swagger/OpenAPI 3.0
- **Code Comments**: Comprehensive JavaDoc/JSDoc
- **Database Schema**: ERD diagrams
- **Architecture**: System design documents

### **User Documentation**
- **User Guides**: Step-by-step instructions
- **Screenshots**: Visual guides for all platforms
- **Video Tutorials**: Platform-specific demos
- **FAQ**: Common questions and solutions

## 🛠 **Build & CI/CD**

### **Build Scripts**
- **Console**: `build.bat/sh`
- **API**: Maven (`mvn clean package`)
- **Web**: Simple static files
- **Mobile**: React Native CLI

### **Continuous Integration**
```yaml
# Example GitHub Actions workflow
name: Multi-Platform Build
on: [push, pull_request]
jobs:
  build-console:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Setup Java
        uses: actions/setup-java@v3
        with:
          java-version: '17'
      - name: Build Console App
        run: ./build.sh
  
  build-api:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Setup Java
        uses: actions/setup-java@v3
        with:
          java-version: '17'
      - name: Build API
        run: cd backend && mvn clean package
  
  build-mobile:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Setup Node.js
        uses: actions/setup-node@v3
        with:
          node-version: '18'
      - name: Build Mobile App
        run: cd mobile-app && npm ci && npm run build:android
```

## 🤝 **Contributing**

### **Development Guidelines**
1. **Follow coding standards** defined in `.github/copilot-instructions.md`
2. **Write tests** for all new features
3. **Update documentation** for API changes
4. **Test on all platforms** before submitting

### **Code Quality**
- **SonarQube**: Automated code quality checks
- **ESLint**: JavaScript/TypeScript linting
- **Prettier**: Code formatting
- **Spotless**: Java code formatting

## 📞 **Support & Contact**

- **Technical Issues**: Create GitHub issues
- **Feature Requests**: Use GitHub discussions
- **Security Issues**: Email security team
- **General Questions**: Contact development team

## 📄 **License**

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 🎯 **Next Steps**

1. **Set up your development environment**
2. **Choose your platform** (Console, Web, or Mobile)
3. **Start the backend API** for full functionality
4. **Follow the quick start guide** for your chosen platform
5. **Explore the features** and customize as needed

**Happy Banking! 🏦✨**
