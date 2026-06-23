# 🏦 Banking Management System

A fully-featured **console-based Banking Management System** built in Java as a final project for the Object-Oriented Programming course. The system simulates a real-world banking environment where multiple users can securely manage their bank accounts and administrators can monitor and control all accounts in the system.

---

## 📌 Features

### 🔐 Entry / Authentication
- Main menu with 4 options: Admin Login, User Login, Create Account, Exit
- Encrypted PIN authentication
- Account lockout after 3 consecutive failed login attempts

### 👤 Account Creation
- Enter name, CNIC, phone number
- Choose account type: **Savings** or **Current**
- Auto-generated unique Account Number
- Set a secure PIN

### 💳 Account Types

| Feature | Savings Account | Current Account |
|---------|----------------|-----------------|
| Interest | ✅ Yes | ❌ No |
| Minimum Balance | ✅ Cannot go below 0 | ❌ N/A |
| Overdraft | ❌ No | ✅ Up to -5000 limit |

### 👨‍💼 User Dashboard
- Check Balance
- Deposit Money
- Withdraw Money
- Transfer Money (to another account number)
- View Transaction History (with date & time)
- Change PIN
- Logout

### 🛡️ Admin Dashboard
- View all accounts and balances
- Search account by name or account number
- Freeze / Unfreeze an account
- Delete an account
- View all transactions across the system

### 🗄️ Data Storage
- All accounts saved to `accounts.txt`
- All transactions saved to `transactions.txt`
- Data persists across sessions (loaded on startup, saved on every change)

---

## 🧠 OOP Concepts Applied

| Principle | How It's Used |
|-----------|--------------|
| **Encapsulation** | Private fields (balance, PIN, account number) with public methods |
| **Inheritance** | `SavingsAccount` and `CurrentAccount` extend abstract `Account` class |
| **Polymorphism** | `withdraw()` and `applyRules()` behave differently per account type |
| **Abstraction** | Users see simple menu options; internal logic is hidden |

---

## 📂 Project Structure

```
banking-system/
│
├── Main.java               # Entry point & main menu
├── Account.java            # Abstract base class
├── SavingsAccount.java     # Savings account with interest
├── CurrentAccount.java     # Current account with overdraft
├── User.java               # User operations & dashboard
├── Admin.java              # Admin dashboard & controls
├── Transaction.java        # Transaction model & logging
├── FileManager.java        # File read/write operations
└── data/
    ├── accounts.txt        # Persistent account storage
    └── transactions.txt    # Persistent transaction history
```

---

## 🛠️ Tech Stack

- **Language:** Java (JDK 8+)
- **IDE:** IntelliJ IDEA
- **Type:** Console-based application
- **Libraries:** Java Standard Library only
  - `java.io` — File reading & writing
  - `java.util` — ArrayLists & Scanners
  - `java.time` — Transaction date/time stamps

---

## 🚀 Getting Started

### Prerequisites
- Java JDK 8 or higher installed
- Any IDE (IntelliJ IDEA recommended) or terminal

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/banking-system.git
   ```

2. **Navigate to the project folder**
   ```bash
   cd banking-system
   ```

3. **Compile the project**
   ```bash
   javac Main.java
   ```

4. **Run the project**
   ```bash
   java Main
   ```

---

## 🗂️ Class Design (UML Summary)

```
             «abstract»
              Account
             /       \
  SavingsAccount   CurrentAccount
       |                  |
      User             Admin
       |
  Transaction ──► FileManager
```

---

## ✅ Validation & Error Handling

- Numeric fields (PIN, amount) reject alphabetic/special characters
- Amounts must be greater than zero
- PIN locked after 3 failed attempts
- Invalid menu selections re-display menu without crashing
- Insufficient balance caught before processing
- Missing data files auto-created on first run
- Duplicate account numbers prevented via auto-generation

---

## 📅 Development Timeline

| Stage | Tasks | Duration |
|-------|-------|----------|
| Planning & Design | Features, UML, proposal | Week 1 |
| Class Implementation | All classes & inheritance | Week 2 |
| Core Features | Login, transactions, file handling | Week 3 |
| Admin Module & Testing | Admin dashboard, bug fixes | Week 4 |
| Documentation & Review | Comments, report, cleanup | Week 5 |

---

## 👨‍💻 Author

**Your Name**  
📧 keshavlund62@gmail.com

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
