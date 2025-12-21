# Portfolio & Holdings App

A modern Android application built with Jetpack Compose to manage and view stock holdings and portfolio summaries.

## 📱 Features

- **Portfolio Summary**: View total investment, current value, total PNL, and today's PNL.
- **Holdings List**: detailed list of individual stock holdings.

## 🛠 Tech Stack

- **Language**: Kotlin
- **UI Toolkit**: [Jetpack Compose](https://developer.android.com/jetbrains/compose)
- **Architecture**: MVVM (Model-View-ViewModel) + Clean Architecture principles
- **Dependency Injection**: [Hilt](https://dagger.dev/hilt/)
- **Networking**: [Retrofit](https://square.github.io/retrofit/) & [Moshi](https://github.com/square/moshi)
- **Async**: Coroutines & Flow
- **Testing**: 
  - Unit Tests: JUnit, Mockk
  - UI Tests: Compose Testing Rule

## 🏛 Architecture & Principles

This project adheres to **SOLID principles** and **Clean Architecture**:

- **Single Responsibility**: Separate Mappers, UseCases, and Repositories.
- **Open/Closed**: sealed UI states (`HoldingsUiState`) allow extension without modification.
- **Dependency Inversion**: generic `HoldingsRepository` interface injected via Hilt.
- **Layered Design**:
  - `domain`: Pure Kotlin logic (Models, Repository Interfaces, Use Cases).
  - `data`: API implementation, DTOs, Mappers.
  - `ui`: ViewModels, Composable Screens.

## 🚀 Setup & Run

1. **Clone** the repository.
2. Open in **Android Studio**.
3. **Sync Gradle** with project files.
4. Run on an emulator or physical device (Min SDK 23).

## 🧪 Running Tests

- **Unit Tests**: Run `./gradlew testDebugUnitTest`