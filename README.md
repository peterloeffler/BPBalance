# BPBalance

BPBalance is a simple Android app to view your BitPanda EUR wallet balance and debit card transactions.

## Features

- Secure API key storage (encrypted with AES-256)
- View EUR wallet balance
- View debit card transactions (latest first)
- Pull-to-refresh to reload data
- Material Design 3 UI

## Screenshots

| API Key Input | Balance & Transactions |
|---------------|------------------------|
| ![API Key](https://placehold.co/300x600/185ADB/FFFFFF?text=API+Key+Input) | ![Balance](https://placehold.co/300x600/185ADB/FFFFFF?text=Balance+%26+Transactions) |

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose + Material 3
- **Networking**: OkHttp + Kotlinx Serialization
- **Architecture**: MVVM
- **Target SDK**: 35 (Android 15+)
- **Min SDK**: 26 (Android 8.0)

## Building

### Prerequisites

- Android SDK 35
- Java 17+

### Build Debug APK

```bash
./gradlew assembleDebug
```

The APK will be at `app/build/outputs/apk/debug/app-debug.apk`

## API Key

You need a BitPanda API key to use this app. Get it from your BitPanda account settings.

The API key is stored securely on your device using Android's EncryptedSharedPreferences.

## Privacy

This app:
- Only communicates with BitPanda's official API
- Stores your API key locally (encrypted)
- Does not collect any analytics or data
- Works entirely offline after the API key is stored

## License

GNU General Public License v3.0
