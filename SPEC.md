# BitPanda EUR Wallet Balance App

## Project Overview
- **Project Name**: BitPandaWallet
- **Type**: Native Android Application
- **Core Functionality**: Fetches BitPanda fiat wallet data via API, filters for EUR wallets, displays balances with pull-to-refresh support

## Technology Stack & Choices
- **Language**: Kotlin 1.9.x
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 35 (Android 15+)
- **Compile SDK**: 35
- **Architecture**: MVVM with Clean Architecture
- **UI Framework**: Jetpack Compose with Material 3
- **Networking**: OkHttp + Retrofit
- **JSON Parsing**: Kotlinx Serialization
- **Async**: Kotlin Coroutines + Flow
- **DI**: Manual (simple app, no Hilt/Koin needed)

## Feature List
1. Fetch fiat wallet data from BitPanda API on app launch
2. Filter response to show only EUR wallets (matching jq filter)
3. Display EUR wallet balance(s) in UI
4. Pull-to-refresh gesture to reload data
5. Loading and error states handling

## API Details
- **Endpoint**: `https://api.bitpanda.com/v1/fiatwallets`
- **Header**: `X-Api-Key: SECRET_KEY`
- **Filter Logic**: `.data[] | select(.attributes.fiat_symbol == "EUR") | .attributes.balance`
