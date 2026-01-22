```markdown
# ExpenseLogger (Android)

ExpenseLogger is a lightweight Android application for tracking personal expenses.  
It is built using modern Android development practices with a focus on clarity, reliability, and incremental extensibility.

---

## Overview

ExpenseLogger allows users to quickly log expenses, view them in a structured list, analyze spending patterns, and export data for external use.  
The application is designed to work fully offline, with future support planned for assisted logging via UPI notifications.

---

## Features

### Core Features
- Add expenses with amount, note, category, and timestamp
- View all logged expenses in a list
- Search expenses by note or amount
- Filter expenses by:
  - All
  - Today
  - This month
- Sort expenses by:
  - Newest first
  - Oldest first
  - Amount (descending)
- Category-wise pie chart visualization
- Monthly summaries and charts
- Export expenses to:
  - CSV
  - Excel
- Share exported files via Android share sheet
- Local persistence using Room database

### Experimental / In Progress
- UPI notification listener infrastructure
- Automatic expense pre-fill from payment notifications (under development)
- User confirmation screen before auto-logging expenses

---

## Tech Stack

- **Language:** Kotlin  
- **UI:** Jetpack Compose (Material 3)  
- **Architecture:** MVVM  
- **Database:** Room  
- **Async & State:** Kotlin Coroutines and Flow  
- **Charts:** Custom Compose-based drawing  
- **File Export:** CSV and Excel via FileProvider  

---

## Project Structure

```

com.example.expenselogger
├── data
│   ├── dao
│   ├── entity
│   ├── repository
│   └── AppDatabase
├── notification
│   └── UpiNotificationListener
├── ui
│   ├── navigation
│   ├── screen
│   ├── theme
│   └── viewmodel
├── util
│   ├── ExportCsvUtil
│   ├── ExportExcelUtil
│   └── ShareUtil
└── MainActivity

````

---

## Setup & Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Ab-hiii/expense-logger-android.git
````

2. Open the project in Android Studio
3. Sync Gradle
4. Run on an emulator or physical device (Android 8.0+ recommended)

---

## Permissions

* No runtime permissions are required for core functionality.
* Notification access is optional and only required for experimental UPI notification handling.

---

## Data Export Notes

* CSV and Excel exports include:

  * Amount
  * Category
  * Note
  * Formatted date and time
* Files are shared securely using Android `FileProvider`.

---

## Roadmap

* Improve UPI notification parsing accuracy
* Add confirmation/edit screen for auto-captured expenses
* Smart auto-categorization
* Monthly budget tracking
* Optional cloud backup
* UI and theming refinements

---

## Design Principles

* Offline-first
* Explicit user control
* Minimal permissions
* Stable core with incremental feature additions

---

## License

This project is currently under a permissive license and may be updated as the project evolves.

```
```
