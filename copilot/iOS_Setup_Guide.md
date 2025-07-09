# 🍎 iOS Setup Guide - Digital Bank Application

This guide will help you set up and run the Digital Bank application on iOS devices.

## 📋 Prerequisites

### System Requirements:
- **macOS** (required for iOS development)
- **Xcode 14+** (download from Mac App Store)
- **iOS 13.0+** target device or simulator
- **Node.js 16+** and **npm**
- **CocoaPods** (iOS dependency manager)

### Optional:
- **Apple Developer Account** (for device testing)
- **Physical iOS device** (iPhone/iPad)

---

## 🚀 Quick Setup Steps

### 1. Install Required Tools

```bash
# Install Node.js (if not already installed)
# Download from: https://nodejs.org/

# Install CocoaPods
sudo gem install cocoapods

# Install React Native CLI
npm install -g @react-native-community/cli

# Verify installations
node --version
npm --version
pod --version
npx react-native --version
```

### 2. Setup the Mobile App

```bash
# Navigate to the mobile app directory
cd mobile-app

# Install dependencies
npm install

# Install iOS dependencies
cd ios
pod install
cd ..
```

### 3. Start Metro Server

```bash
# In the mobile-app directory
npm start
# or
npx react-native start
```

### 4. Run on iOS

#### Option A: iOS Simulator (Easiest)
```bash
# Run on iOS simulator
npx react-native run-ios

# Or specify simulator
npx react-native run-ios --simulator="iPhone 15"
```

#### Option B: Physical iOS Device
```bash
# Connect your iPhone via USB
# Trust the computer on your device

# Run on connected device
npx react-native run-ios --device
```

---

## 🔧 Detailed Setup Instructions

### Step 1: Install Xcode

1. Open **Mac App Store**
2. Search for **"Xcode"**
3. Click **"Get"** or **"Install"**
4. Wait for installation (can take 30+ minutes)
5. Open Xcode and accept license agreements
6. Install additional components when prompted

### Step 2: Setup Xcode Command Line Tools

```bash
# Install Xcode command line tools
xcode-select --install

# Verify installation
xcode-select -p
```

### Step 3: Setup React Native Development Environment

```bash
# Install Watchman (file watching service)
brew install watchman

# Install React Native CLI globally
npm install -g @react-native-community/cli

# Verify React Native setup
npx react-native doctor
```

### Step 4: Configure iOS Project

```bash
# Navigate to project directory
cd /path/to/your/project/mobile-app

# Install JavaScript dependencies
npm install

# Navigate to iOS directory
cd ios

# Install native iOS dependencies
pod install

# Go back to mobile-app directory
cd ..
```

### Step 5: Open iOS Project in Xcode

```bash
# Open the workspace file (NOT .xcodeproj)
open ios/WiproBankApp.xcworkspace
```

### Step 6: Configure Signing & Teams (For Device Testing)

1. In Xcode, select the project in navigator
2. Select the **"WiproBankApp"** target
3. Go to **"Signing & Capabilities"** tab
4. Select your **Apple Developer Team**
5. Xcode will automatically generate provisioning profile

---

## 📱 Running Options

### Option 1: iOS Simulator

```bash
# List available simulators
xcrun simctl list devices

# Run on default simulator
npx react-native run-ios

# Run on specific simulator
npx react-native run-ios --simulator="iPhone 15 Pro"
npx react-native run-ios --simulator="iPad Pro (12.9-inch)"
```

### Option 2: Physical Device

```bash
# Connect iPhone via USB cable
# Unlock device and tap "Trust This Computer"

# Build and run on device
npx react-native run-ios --device

# Or specify device by name
npx react-native run-ios --device="Your iPhone Name"
```

### Option 3: Build for App Store

```bash
# Create release build
npx react-native run-ios --configuration Release

# Or build through Xcode:
# Product → Archive → Distribute App
```

---

## 🌐 Alternative: Web App on iOS

If you don't have macOS or want a quicker solution:

### Mobile Safari (Easiest Option)

1. **Start the web server** (on any computer):
   ```bash
   cd web-app
   python -m http.server 3000
   ```

2. **Find your computer's IP address**:
   ```bash
   # On Windows
   ipconfig
   
   # On macOS/Linux
   ifconfig
   ```

3. **Access from iPhone**:
   - Open Safari on iOS
   - Go to: `http://YOUR_COMPUTER_IP:3000`
   - Example: `http://192.168.1.100:3000`

4. **Add to Home Screen**:
   - Tap the share button in Safari
   - Select "Add to Home Screen"
   - The app will behave like a native app!

---

## 🔍 Troubleshooting

### Common Issues:

#### 1. "No bundle URL present"
```bash
# Reset Metro cache
npx react-native start --reset-cache

# Clean and rebuild
cd ios
xcodebuild clean
cd ..
npx react-native run-ios
```

#### 2. "CocoaPods not found"
```bash
# Install CocoaPods
sudo gem install cocoapods

# Reinstall pods
cd ios
pod deintegrate
pod install
cd ..
```

#### 3. "Unable to find a destination"
```bash
# Check available simulators
xcrun simctl list devices

# Install more simulators through Xcode:
# Xcode → Window → Devices and Simulators → Simulators → +
```

#### 4. Build fails with signing errors
```bash
# In Xcode:
# 1. Select project → Target → Signing & Capabilities
# 2. Uncheck "Automatically manage signing"
# 3. Check "Automatically manage signing" again
# 4. Select your development team
```

#### 5. Metro server not starting
```bash
# Kill existing Metro processes
npx react-native start --reset-cache

# Or manually kill processes
lsof -ti:8081 | xargs kill -9
```

---

## 📁 Project Structure for iOS

```
mobile-app/
├── ios/                          # iOS native code
│   ├── WiproBankApp.xcworkspace  # Open this in Xcode
│   ├── WiproBankApp.xcodeproj
│   ├── WiproBankApp/
│   │   ├── Info.plist           # App configuration
│   │   ├── AppDelegate.h
│   │   └── AppDelegate.m
│   └── Podfile                  # iOS dependencies
├── src/                         # React Native source code
│   ├── components/              # UI components
│   ├── screens/                 # App screens
│   ├── services/                # API services
│   └── navigation/              # App navigation
├── package.json                 # Node dependencies
└── README.md                    # Mobile app docs
```

---

## 🎯 Banking App Features on iOS

Once running on iOS, you'll have access to:

### Core Banking Features:
- ✅ **Customer Management** - Create and view customers
- ✅ **Account Operations** - View account details and balances
- ✅ **Fund Transfers** - Transfer money between accounts
- ✅ **Transaction History** - View transaction records
- ✅ **Reports** - Generate account and transaction reports

### iOS-Specific Features:
- 🔒 **Touch ID / Face ID** - Biometric authentication
- 📱 **Native Navigation** - iOS-style navigation
- 🎨 **iOS Design System** - Native iOS look and feel
- 📲 **Push Notifications** - Transaction alerts
- 💾 **Offline Storage** - Cache data locally
- 📷 **Camera Integration** - Document scanning

---

## 🔐 Security Features

### Authentication:
- Biometric authentication (Touch ID/Face ID)
- PIN-based authentication
- Session management

### Data Protection:
- Encrypted local storage
- Secure API communication
- Certificate pinning

---

## 🚀 Quick Start Commands

Save these commands for easy access:

```bash
# Complete iOS setup (run once)
cd mobile-app
npm install
cd ios && pod install && cd ..

# Daily development (run each time)
npm start                    # Start Metro server
npx react-native run-ios   # Run on simulator

# Device testing
npx react-native run-ios --device

# Clean rebuild (if issues)
npx react-native start --reset-cache
cd ios && xcodebuild clean && cd ..
npx react-native run-ios
```

---

## 📞 Support & Next Steps

### If You Need Help:
1. **Check React Native docs**: https://reactnative.dev/docs/environment-setup
2. **iOS-specific setup**: https://reactnative.dev/docs/environment-setup?guide=native&platform=ios
3. **Xcode documentation**: https://developer.apple.com/xcode/

### Next Steps After Setup:
1. **Customize the app** - Modify colors, logos, app name
2. **Add features** - Implement additional banking features
3. **Test thoroughly** - Test on different iOS versions
4. **Prepare for App Store** - Follow Apple's guidelines

---

## 🎉 Success!

Once you see the Wipro Bank app running on your iOS device/simulator, you'll have a fully functional mobile banking application!

The app will connect to your backend API and provide all banking features with a native iOS experience.

**Happy iOS Development! 📱✨**
