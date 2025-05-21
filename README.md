# Unify - A Kotlin Multiplatform ERP Solution

Unify is a comprehensive Enterprise Resource Planning (ERP) application built with Kotlin Multiplatform, designed to streamline business processes across Android, iOS, and Desktop (Windows, macOS, Linux). The project leverages Compose Multiplatform for a consistent, modern UI and uses Supabase for backend services.

## ✨ Core Features

- **Platform Agnostic:**  
  📱 Android; 🍎 iOS; 💻 Desktop
- **Shared UI/UX:** Native-feel interface with [Jetpack Compose](https://developer.android.com/jetpack/compose) and [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/).
- **Unified Business Logic:** Core ERP features (user management, data processing, business rules) in shared Kotlin code.
- **Modular ERP Components:**
  -
- [x] User Authentication (Supabase Auth)
- [] Inventory Management
- [] Order Processing
- [] CRM
- [] Financial Modules
- [] Reporting & Analytics
- [] Real-time data sync with Supabase Realtime
- **Adaptive UI:** Responsive layouts using Compose Material 3 Adaptive components.
- **Offline Capabilities:** Access and modify critical data offline (using Room/SQLite).

## 🛠️ Tech Stack & Key Libraries

- **Core:**
  - Kotlin Multiplatform 
  - Compose Multiplatform 
  - Kotlin Coroutines
  - Kotlinx Serialization 
- **Backend:**
  - Supabase Auth, Database, Storage, Realtime, Functions
- **Dependency Injection:**
  - Koin 
- **Networking:**
  - Ktor Client
- **Image Loading:**
  - Coil 
- **Navigation:**
  - Jetpack Navigation Compose 
  - Compose Material 3 Adaptive Navigation 
- **Persistence:**
  - Jetpack DataStore 
  - Jetpack Room 
- **Build & Config:**
  - BuildKonfig 
  - Compose Hot Reload
- **Logging:**
  - Napier 
- **Testing:**
  - kotlin.test
  - AssertK 
  - Turbine 

## 🚀 Getting Started

### Prerequisites

- [Android Studio](https://developer.android.com/studio)
- For iOS:
  - macOS
  - [Xcode](https://developer.apple.com/xcode/)
- [Supabase Account](https://supabase.com/)

### Configuration

1. **Clone the repository**
2. **Supabase Setup:**
  - Create a project on Supabase
  - Get your `Project URL` and `anon public` key from Project Settings > API
3. **Local Configuration:**
  - Create a `local.properties` file in the root directory
  - Add your Supabase credentials (do not commit this file)
  - Credentials are accessed via BuildKonfig

### Build & Run

#### Android

- Open in Android Studio
- Select the `androidApp` run configuration
- Run on emulator or device

### iOS

To run the application on iPhone device/simulator:

- Open `iosApp/iosApp.xcproject` in Xcode and run standard configuration
- Or
  use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile)
  for Android Studio

#### Desktop

- Open in Android Studio or IntelliJ IDEA
- Select the `desktopApp` run configuration
- Run, or use Gradle:  
  `./gradlew :composeApp:run`

## 📂 Project Structure

- `/composeApp`: Shared KMP code (business logic, UI, data, Supabase)
  - `commonMain`, `androidMain`, `iosMain`, `desktopMain`
- `/androidApp`: Android shell
- `/iosApp`: iOS shell (Xcode project)
- `/desktopApp`: Desktop shell (or entry in `composeApp/desktopMain`)

## 🧪 Testing

- **Unit Tests:** In `commonTest`, `androidTest`, `iosTest`, etc.
- **UI Tests:** [Add details if available]
- Run tests via IDE or Gradle

## 📄 License

This project is licensed under the MIT License.