import java.util.ArrayList;

public class User {
    private Account account;
    private ArrayList<Account> allAccounts = new ArrayList<>();
    private ArrayList<Transaction> transactionHistory = new ArrayList<>();
    private FileManager fm;

    public User(Account account, ArrayList<Account> allAccounts, FileManager fm) {
        this.account = account;
        this.allAccounts = allAccounts;
        this.fm = fm;
    }

    public boolean login(String pin) {
        return account.authenticatePin(pin);
    }

    public void deposit(double amount) {
        account.deposit(amount);
        Transaction t = new Transaction(account.getAccountNumber(), "Deposit", amount);
        transactionHistory.add(t);
        fm.saveTransaction(t);
        fm.updateAccount(account);
    }

    public void withDraw(double amount) {
        double balanceBefore = account.getBalance();
        account.withDraw(amount);
        if (account.getBalance() < balanceBefore) {
            Transaction t = new Transaction(account.getAccountNumber(), "Withdraw", amount);
            transactionHistory.add(t);
            fm.saveTransaction(t);
            fm.updateAccount(account);
        }
    }

    public void transfer(String targetedAccount, double amount) {
        Account target = null;
        for (int i = 0; i < allAccounts.size(); i++) {
            if (allAccounts.get(i).getAccountNumber().equals(targetedAccount)) {
                target = allAccounts.get(i);
                break;
            }
        }
        if (target == null) {
            System.out.println("Account not found!");
            return;
        }
        if (target == account) {
            System.out.println("Cannot Transfer to yourself! Enter a valid account");
            return;
        }
        double balanceBefore = account.getBalance();
        account.withDraw(amount);
        if (account.getBalance() < balanceBefore) {
            target.deposit(amount);
            Transaction t = new Transaction(account.getAccountNumber(), "Transfer", amount);
            transactionHistory.add(t);
            fm.saveTransaction(t);
            fm.updateAccount(account);
            fm.updateAccount(target);
            System.out.println("Amount: " + amount + " Successfully transferred.");
        }
    }

    public void viewHistory() {
        for (int i = 0; i < transactionHistory.size(); i++) {
            System.out.println(transactionHistory.get(i));
        }
    }

    public void changePin(String oldPin, String newPin) {
        if (account.authenticatePin(oldPin)) {
            account.setPin(newPin);
            fm.updateAccount(account);
            System.out.println("Pin Changed Successfully");
        } else {
            System.out.println("Incorrect old Pin");
        }
    }
}