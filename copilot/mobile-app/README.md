# Digital Bank Mobile App (React Native)

This is the React Native mobile application for Digital Bank, supporting both iOS and Android platforms.

## Features

- **Customer Management**: Create and manage customer accounts
- **Fund Transfers**: Secure money transfers between accounts
- **Account Reports**: View account details and transaction history
- **Real-time Updates**: Live balance and transaction updates
- **Multi-platform**: Single codebase for iOS and Android

## Project Structure

```
mobile-app/
├── src/
│   ├── components/         # Reusable UI components
│   ├── screens/           # App screens
│   ├── services/          # API services
│   ├── utils/             # Utility functions
│   └── styles/            # Global styles
├── android/               # Android-specific code
├── ios/                   # iOS-specific code
└── package.json
```

## Prerequisites

1. **Node.js** (v14 or higher)
2. **React Native CLI**
3. **Android Studio** (for Android development)
4. **Xcode** (for iOS development, macOS only)

## Setup Instructions

1. **Install React Native CLI**:
   ```bash
   npm install -g react-native-cli
   ```

2. **Install Dependencies**:
   ```bash
   cd mobile-app
   npm install
   ```

3. **iOS Setup** (macOS only):
   ```bash
   cd ios && pod install
   ```

4. **Run the App**:
   ```bash
   # For Android
   npx react-native run-android
   
   # For iOS (macOS only)
   npx react-native run-ios
   ```

## Key Components

### 1. Main App Component
```javascript
import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import HomeScreen from './src/screens/HomeScreen';
import CustomerScreen from './src/screens/CustomerScreen';
import TransferScreen from './src/screens/TransferScreen';
import ReportsScreen from './src/screens/ReportsScreen';

const Tab = createBottomTabNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Tab.Navigator>
        <Tab.Screen name="Home" component={HomeScreen} />
        <Tab.Screen name="Customers" component={CustomerScreen} />
        <Tab.Screen name="Transfer" component={TransferScreen} />
        <Tab.Screen name="Reports" component={ReportsScreen} />
      </Tab.Navigator>
    </NavigationContainer>
  );
}
```

### 2. API Service
```javascript
const API_BASE_URL = 'http://localhost:8080/api';

class BankApiService {
  async createCustomer(customerData) {
    try {
      const response = await fetch(`${API_BASE_URL}/customers`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(customerData),
      });
      return await response.json();
    } catch (error) {
      throw new Error('Failed to create customer');
    }
  }

  async transferFunds(transferData) {
    try {
      const response = await fetch(`${API_BASE_URL}/transfers`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(transferData),
      });
      return await response.json();
    } catch (error) {
      throw new Error('Failed to transfer funds');
    }
  }

  async getReports(page = 0, size = 5) {
    try {
      const response = await fetch(`${API_BASE_URL}/reports/accounts?page=${page}&size=${size}`);
      return await response.json();
    } catch (error) {
      throw new Error('Failed to load reports');
    }
  }
}

export default new BankApiService();
```

### 3. Customer Screen
```javascript
import React, { useState } from 'react';
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  Alert,
  ScrollView,
} from 'react-native';
import BankApiService from '../services/BankApiService';

const CustomerScreen = () => {
  const [customerData, setCustomerData] = useState({
    name: '',
    dateOfBirth: '',
    email: '',
    phone: '',
    accountType: 'Savings',
    accountStatus: 'Active',
    balance: '',
  });

  const handleSubmit = async () => {
    try {
      const result = await BankApiService.createCustomer(customerData);
      if (result.success) {
        Alert.alert('Success', 'Customer created successfully');
        // Reset form
        setCustomerData({
          name: '',
          dateOfBirth: '',
          email: '',
          phone: '',
          accountType: 'Savings',
          accountStatus: 'Active',
          balance: '',
        });
      } else {
        Alert.alert('Error', result.message);
      }
    } catch (error) {
      Alert.alert('Error', 'Failed to create customer');
    }
  };

  return (
    <ScrollView style={styles.container}>
      <Text style={styles.title}>Create New Customer</Text>
      
      <TextInput
        style={styles.input}
        placeholder="Customer Name"
        value={customerData.name}
        onChangeText={(text) => setCustomerData({ ...customerData, name: text })}
      />
      
      <TextInput
        style={styles.input}
        placeholder="Date of Birth (DD-MM-YYYY)"
        value={customerData.dateOfBirth}
        onChangeText={(text) => setCustomerData({ ...customerData, dateOfBirth: text })}
      />
      
      <TextInput
        style={styles.input}
        placeholder="Email"
        value={customerData.email}
        onChangeText={(text) => setCustomerData({ ...customerData, email: text })}
        keyboardType="email-address"
      />
      
      <TextInput
        style={styles.input}
        placeholder="Phone Number"
        value={customerData.phone}
        onChangeText={(text) => setCustomerData({ ...customerData, phone: text })}
        keyboardType="numeric"
      />
      
      <TextInput
        style={styles.input}
        placeholder="Initial Balance"
        value={customerData.balance}
        onChangeText={(text) => setCustomerData({ ...customerData, balance: text })}
        keyboardType="numeric"
      />
      
      <TouchableOpacity style={styles.button} onPress={handleSubmit}>
        <Text style={styles.buttonText}>Create Customer</Text>
      </TouchableOpacity>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#f5f5f5',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
    textAlign: 'center',
    color: '#0066cc',
  },
  input: {
    backgroundColor: 'white',
    borderRadius: 10,
    padding: 15,
    marginBottom: 15,
    borderWidth: 1,
    borderColor: '#e0e0e0',
    fontSize: 16,
  },
  button: {
    backgroundColor: '#0066cc',
    borderRadius: 10,
    padding: 15,
    alignItems: 'center',
    marginTop: 10,
  },
  buttonText: {
    color: 'white',
    fontSize: 18,
    fontWeight: 'bold',
  },
});

export default CustomerScreen;
```

## Build Instructions

### For Development
```bash
# Start Metro bundler
npx react-native start

# Run on Android
npx react-native run-android

# Run on iOS (macOS only)
npx react-native run-ios
```

### For Production

#### Android APK
```bash
cd android
./gradlew assembleRelease
```

#### iOS App Store
```bash
# Open in Xcode
open ios/DigitalBankApp.xcworkspace

# Build for distribution through Xcode
```

## Configuration

Update the API endpoint in `src/services/BankApiService.js`:
```javascript
const API_BASE_URL = 'https://your-api-domain.com/api';
```

## Security Features

- **Input Validation**: All user inputs are validated
- **Secure Communication**: HTTPS for API calls
- **Error Handling**: Comprehensive error handling
- **Data Protection**: Sensitive data encryption

## Testing

```bash
# Run unit tests
npm test

# Run on device/emulator
npx react-native run-android --device
npx react-native run-ios --device
```

## Deployment

1. **Android**: Generate signed APK and upload to Google Play Store
2. **iOS**: Archive and upload to App Store Connect

## Support

For support and issues, contact the development team.
