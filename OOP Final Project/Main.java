import java.util.*;
import java.time.LocalDate;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static FileManager fm = new FileManager();
    static Admin admin = new Admin("ADMIN01", "admin123");

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n========================");
            System.out.println("   BANKING SYSTEM MENU  ");
            System.out.println("========================");
            System.out.println("1. User Login");
            System.out.println("2. Create Account");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.println("------------------------");
            System.out.print("Enter choice: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    userLoginMenu();
                    break;
                case 2:
                    createAccountMenu();
                    break;
                case 3:
                    adminMenu();
                    break;
                case 4:
                    System.out.println("System Closed.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static int readInt() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            return -1;
        }
    }

    static double readDouble() {
        try {
            return Double.parseDouble(sc.nextLine().trim());
        } catch (Exception e) {
            return -1;
        }
    }

    static boolean isValidCNIC(String cnic) {
        return cnic.matches("\\d{13}");
    }

    static boolean isValidPhone(String phone) {
        return phone.matches("\\d{11}");
    }

    static String readValidPin() {
        while (true) {
            String pin = sc.nextLine().trim();
            if (pin.matches("\\d{4}")) {
                return pin;
            }
            System.out.print("Invalid PIN. Must be exactly 4 digits (numbers only). Try again: ");
        }
    }

    static void userLoginMenu() {

        System.out.println("\n--- USER LOGIN ---");
        System.out.print("Enter Account Number: ");
        String accNum = sc.nextLine().trim();

        List<Account> all = fm.loadAccounts();
        Account found = null;

        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getAccountNumber().equals(accNum)) {
                found = all.get(i);
                break;
            }
        }

        if (found == null) {
            System.out.println("Account not found.");
            return;
        }

        if (found.getStatusOfAccount()) {
            System.out.println("Account is locked.");
            return;
        }

        System.out.print("Enter PIN: ");
        String pin = readValidPin();

        User user = new User(found, new ArrayList<Account>(all), fm);
        boolean loginSuccess = user.login(pin);

        fm.updateAccount(found);

        if (!loginSuccess) {
            System.out.println("Login failed.");
            return;
        }

        if (found.getStatusOfAccount()) {
            System.out.println("Account is locked.");
            return;
        }

        userOperationsMenu(user, found);
    }

    static void userOperationsMenu(User user, Account account) {

        while (true) {

            System.out.println("\n========================");
            System.out.println("       USER MENU        ");
            System.out.println("========================");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. View Balance");
            System.out.println("5. Transaction History");
            System.out.println("6. Change PIN");

            if (account instanceof SavingAccount) {
                System.out.println("7. Apply Interest");
            }

            System.out.println("0. Logout");
            System.out.println("------------------------");
            System.out.print("Enter choice: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter amount: ");
                    double d = readDouble();
                    if (d > 0) user.deposit(d);
                    else System.out.println("Invalid amount.");
                    break;

                case 2:
                    System.out.print("Enter amount: ");
                    double w = readDouble();
                    if (w > 0) user.withDraw(w);
                    else System.out.println("Invalid amount.");
                    break;

                case 3:
                    System.out.print("Target account: ");
                    String target = sc.nextLine().trim();
                    System.out.print("Enter amount: ");
                    double t = readDouble();
                    if (t > 0) user.transfer(target, t);
                    else System.out.println("Invalid amount.");
                    break;

                case 4:
                    System.out.println("Balance: " + account.getBalance());
                    break;

                case 5:
                    user.viewHistory();
                    break;

                case 6:
                    System.out.print("Old PIN: ");
                    String oldPin = readValidPin();
                    System.out.print("New PIN: ");
                    String newPin = readValidPin();
                    user.changePin(oldPin, newPin);
                    break;

                case 7:
                    if (account instanceof SavingAccount) {
                        ((SavingAccount) account).applyInterest();
                        fm.updateAccount(account);
                    }
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static void createAccountMenu() {

        System.out.println("\n--- CREATE ACCOUNT ---");

        System.out.print("Enter Owner Name: ");
        String name = sc.nextLine().trim();

        String cnic;
        while (true) {
            System.out.print("Enter CNIC (13 digits): ");
            cnic = sc.nextLine().trim();
            if (isValidCNIC(cnic)) break;
            System.out.println("Invalid CNIC. Must be exactly 13 digits (numbers only).");
        }

        String phone;
        while (true) {
            System.out.print("Enter Phone (11 digits): ");
            phone = sc.nextLine().trim();
            if (isValidPhone(phone)) break;
            System.out.println("Invalid Phone. Must be exactly 11 digits (numbers only).");
        }

        System.out.print("Enter PIN (4 digits): ");
        String pin = readValidPin();

        double balance;
        while (true) {
            System.out.print("Enter Initial Deposit: ");
            balance = readDouble();
            if (balance >= 0) break;
            System.out.println("Invalid amount. Must be 0 or greater.");
        }

        String accNum = "ACC" + System.currentTimeMillis();

        System.out.println("Account Type:");
        System.out.println("1. Saving");
        System.out.println("2. Current");
        System.out.print("Enter choice: ");

        int type = readInt();

        Account newAcc;

        if (type == 1) {
            newAcc = new SavingAccount(
                    12.0,
                    accNum,
                    name,
                    cnic,
                    phone,
                    pin,
                    balance,
                    false,
                    "SAVING",
                    0,
                    0,
                    LocalDate.now()
            );
        } else {
            newAcc = new CurrentAccount(
                    accNum,
                    name,
                    cnic,
                    phone,
                    pin,
                    balance,
                    false,
                    "CURRENT",
                    0
            );
        }

        fm.saveAccount(newAcc);
        System.out.println("Account Created Successfully!");
        System.out.println("Account Number: " + accNum);
    }

    static void adminMenu() {

        System.out.print("Enter Admin PIN: ");
        String pin = sc.nextLine().trim();

        if (!admin.login(pin)) {
            System.out.println("Invalid Admin credentials.");
            return;
        }

        while (true) {

            System.out.println("\n========================");
            System.out.println("       ADMIN MENU       ");
            System.out.println("========================");
            System.out.println("1. View All Accounts");
            System.out.println("2. Search Account");
            System.out.println("3. Freeze Account");
            System.out.println("4. Unfreeze Account");
            System.out.println("5. Delete Account");
            System.out.println("6. View All Transactions");
            System.out.println("0. Logout");
            System.out.println("------------------------");
            System.out.print("Enter choice: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    admin.viewAllAccounts();
                    break;

                case 2:
                    System.out.print("Enter Account Number: ");
                    String id = sc.nextLine().trim();
                    System.out.println(admin.searchAccount(id));
                    break;

                case 3:
                    System.out.print("Enter Account Number: ");
                    admin.freezeAccount(sc.nextLine().trim());
                    break;

                case 4:
                    System.out.print("Enter Account Number: ");
                    admin.unfreezeAccount(sc.nextLine().trim());
                    break;

                case 5:
                    System.out.print("Enter Account Number: ");
                    admin.deleteAccount(sc.nextLine().trim());
                    break;

                case 6:
                    admin.viewAllTransactions();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}