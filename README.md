<div align="center">

# 🖨️ PrintXpress

**A native Android digital printing app for print services across Sri Lanka**

![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white)
![Language](https://img.shields.io/badge/Kotlin-100%25-7F52FF?logo=kotlin&logoColor=white)
![UI](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?logo=jetpackcompose&logoColor=white)
![Database](https://img.shields.io/badge/Database-Room-4F46E5)
![Architecture](https://img.shields.io/badge/Architecture-MVVM-10B981)
![Course](https://img.shields.io/badge/Module-CSE5011-F97316)

</div>

---

## 📖 Overview

**PrintXpress** lets customers browse print products, configure them with live pricing, upload artwork, choose pickup or delivery, and track their orders from confirmation to completion, all from a single mobile app.

It is built with **Kotlin**, **Jetpack Compose**, **Room Database**, and a clean **MVVM** architecture, and was developed for the **CSE5011 – Mobile Application Development** module.

---

## ✨ Key Features

- 🔐 **Secure authentication**: registration, login, and profile management with SHA-256 password hashing
- 🛍️ **Product catalog**: category tabs (Business Cards, Flyers, Posters, Banners, Stickers, T-Shirts), search, and promotions
- ⚙️ **Product configuration**: real-time price calculation, paper/material and size options, artwork upload, and order notes
- 🚚 **Flexible fulfillment**: Store Pickup (free) or Island-wide Home Delivery (LKR 450) with address validation and date selection
- 📦 **Order tracking**: visual 5-step stepper: *Confirmed → Processing → Printing → Ready → Completed*
- 🔔 **Notifications**: automatic alerts on registration, order confirmation, and status changes
- 🎨 **Design resources**: pre-press guidelines (PDF / 300 DPI), CMYK vs RGB guide, bleed and safe margins, support hotline
- 🌗 **Light & Dark themes**: toggle from the Profile & Settings screen

---

## 🎨 Design System

| Element | Value |
|---|---|
| **Primary** | Electric Indigo `#4F46E5` / `#6366F1` |
| **Accent** | Radiant Mint `#10B981` · Sunset Tangerine `#F97316` |
| **Light background** | Slate 50 `#F8FAFC` |
| **Dark background** | Obsidian 950 `#0B0F19` |
| **Shape language** | 16–20dp rounded surfaces |
| **UX details** | Color-coded status badges, sticky checkout bar, visual production stepper |

---

## 🧰 Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose |
| Architecture | MVVM |
| Local storage | Room Database (with KSP) |
| Async | Kotlin Coroutines & Flow |
| Build | Gradle 8.9 · Android Gradle Plugin 8.7.3 |
| Testing | JUnit |

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** (latest stable recommended)
- **JDK** bundled with Android Studio
- An emulator (e.g. **Pixel 8, API 34+**) or a physical Android device with USB debugging enabled

### Installation

1. Clone or download the repository.
2. Open **Android Studio** and choose **File → Open**.
3. Select the project folder .
4. Wait for **Gradle sync** to finish.
5. Select an emulator or connected device.
6. Click **Run 'app'** ▶️.

### Running Tests

```bash
./gradlew test          # macOS / Linux
gradlew.bat test        # Windows
```

---

## 🔑 Test Credentials

Use these accounts for demos, grading, and testing:

| Field | Value |
|---|---|
| **Email** | `testcustomer@example.com` |
| **Password** | `TestPassword123` |
| **Name** | Test Customer |
| **Phone** | `0771234567` |

> 💡 **Tip:** On the Login screen, tap **"⚡ Fill Test Customer"** for one-tap login.

---

## 🗄️ Database Schema

The app uses a normalized relational schema with **8 tables**:

| Table | Purpose |
|---|---|
| `users` | Customer accounts and credentials |
| `addresses` | Saved delivery addresses |
| `categories` | Product categories |
| `products` | Catalog items and pricing options |
| `orders` | Order headers, status, and fulfillment details |
| `order_items` | Line items belonging to each order |
| `designs` | Uploaded artwork references |
| `notifications` | User notifications |

---

## 📋 Business Rules & Validation

| Rule | Details |
|---|---|
| **Phone number** | Sri Lankan mobile format `07XXXXXXXX` |
| **Email** | Must match a valid email format |
| **Quantity** | Must be greater than 0 |
| **Delivery address** | Mandatory when Home Delivery is selected |
| **Cancellation / rescheduling** | Allowed **only before printing begins** (`CONFIRMED` or `PROCESSING`) |

---

## 🗺️ Assessment Task Mapping

| Task | Feature | Implementation |
|---|---|---|
| **A** | Android & Kotlin architecture | Jetpack Compose, Room, Coroutines, Flow, MVVM |
| **B** | Normalized relational schema | 8 normalized Room entities |
| **C.1** | Authentication & profile | SHA-256 hashing, profile edit, address management |
| **C.2** | Product catalog & home | Category tabs, search, promotions |
| **C.3** | Product configuration | Live pricing, material/size options, artwork upload, notes counter |
| **C.4** | Checkout & fulfillment | Pickup vs delivery, address validation, date selector |
| **C.5** | Order tracking | 5-step visual progress stepper |
| **C.6** | Design resources | Pre-press specs, CMYK vs RGB, bleed/safe margins, support hotline |
| **D.5** | Form & input validation | Phone regex, email format, quantity, address checks |
| **D.7** | Order status & cancellation | Cancellation only before printing starts |
| **D.8** | Notifications | Triggered on registration, order confirmation, status changes |
| **E** | Unit tests | JUnit tests for validators and cancellation rules |

---

## 📂 Project Structure

```text
Mad/
├── build.gradle.kts                # Project-level Gradle build file
├── settings.gradle.kts             # Settings script (includes :app)
├── gradle.properties               # JVM & AndroidX configuration
├── gradlew / gradlew.bat           # Gradle wrapper scripts
├── gradle/wrapper/                 # Gradle wrapper binaries (v8.9)
└── app/
    ├── build.gradle.kts            # App module build script (Compose, Room, KSP)
    ├── proguard-rules.pro          # ProGuard rules
    └── src/
        ├── test/java/lk/printxpress/
        │   ├── ValidatorsTest.kt   # Validation unit tests (Task E)
        │   └── OrderStatusTest.kt  # Cancellation rule tests (Tasks D.7 & E.1)
        └── main/
            ├── AndroidManifest.xml # Manifest with permissions & icons
            ├── res/                # Colors, themes, adaptive launcher icons
            └── java/lk/printxpress/
                ├── PrintXpressApp.kt
                ├── MainActivity.kt
                ├── data/           # Room database, 8 entities, 6 DAOs, repositories
                ├── ui/             # Theme, components, navigation, 8 Compose screens
                ├── viewmodel/      # Auth, Product, Order, Notification ViewModels
                └── utils/          # Validators, SecurityUtils (SHA-256), Formatters (LKR)
```

---

## 🏗️ Architecture

```text
┌──────────────┐    ┌────────────────┐    ┌──────────────┐    ┌──────────────┐
│  Compose UI  │ ─▶ │   ViewModel    │ ─▶ │  Repository  │ ─▶ │  Room (DAO)  │
│   (ui/)      │ ◀─ │ (StateFlow)    │ ◀─ │   (data/)    │ ◀─ │   SQLite     │
└──────────────┘    └────────────────┘    └──────────────┘    └──────────────┘
```

UI state flows down from the database via Kotlin Flow, and user actions flow up through ViewModels, keeping screens stateless and testable.

---



## 👤 Author

**Name:** _Ruzaik Siyadh_

---

## 📄 License

This project was developed for academic purposes. All rights reserved by the author unless stated otherwise.
