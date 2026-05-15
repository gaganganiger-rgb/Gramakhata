# 📒 Grama-Khata — Digital Village Ledger

**Student:** Zoya Muskaan | **USN:** 1GC22CS153

---

## 🚀 How to Open in Android Studio

1. **Extract** the ZIP file anywhere on your computer
2. **Open Android Studio** → Click **"Open"** (NOT "New Project")
3. Navigate to the extracted `GramaKhata` folder → Click **OK**
4. Wait for **Gradle Sync** to complete (first time takes 2–5 minutes, needs internet)
5. Connect your phone or start an emulator (API 24+)
6. Click ▶ **Run**

> ⚠️ **Important:** Android Studio will auto-detect your SDK path. If it asks, point it to your Android SDK folder.

---

## 📱 App Features

| Feature | Status |
|---------|--------|
| Add/Edit Customer with photo | ✅ |
| Give Credit (+) entry | ✅ |
| Receive Payment (-) entry | ✅ |
| Real-time Net Balance | ✅ |
| Due Dashboard (sorted) | ✅ |
| WhatsApp/SMS Reminder | ✅ |
| Daily Collection Report | ✅ |
| Search customers | ✅ |
| Offline (Room DB) | ✅ |
| Transaction history | ✅ |

---

## 🗂 Project Structure

```
app/src/main/java/com/gramakhata/app/
├── data/
│   ├── db/         → Room Database, DAOs
│   ├── model/      → Customer, Transaction entities
│   └── repository/ → KhataRepository
├── ui/
│   ├── MainActivity.kt        → Due Dashboard
│   ├── SplashActivity.kt      → Splash screen
│   └── customers/
│       ├── AddCustomerActivity.kt
│       ├── CustomerDetailActivity.kt
│       ├── CustomerAdapter.kt
│       └── TransactionAdapter.kt
└── viewmodel/
    └── KhataViewModel.kt
```

---

## 🛠 Tech Stack

- **Language:** Kotlin
- **Database:** Room DB (offline-first)
- **Architecture:** MVVM + LiveData + ViewModel
- **UI:** Material Design 3, CardView, RecyclerView
- **Min SDK:** API 24 (Android 7.0)

---

*Grama-Khata • April 2026 • Empowering Village Commerce Through Technology*
