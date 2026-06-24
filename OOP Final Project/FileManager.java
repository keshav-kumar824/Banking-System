import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class FileManager {

    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";

    public void saveAccount(Account a) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE, true))) {
            writer.write(a.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving account: " + e.getMessage());
        }
    }

    public List<Account> loadAccounts() {
        List<Account> accounts = new ArrayList<>();
        File file = new File(ACCOUNTS_FILE);
        if (!file.exists()) return accounts;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                Account a = parseAccount(line);
                if (a != null) accounts.add(a);
            }
        } catch (IOException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }

        return accounts;
    }

    private Account parseAccount(String line) {
        String[] p = line.split(",");

      if (p[0].equals("SAVING")) {
    return new SavingAccount(
            Double.parseDouble(p[10]),   // interestRate
            p[1],                        // accNo
            p[2],                        // name
            p[3],                        // cnic
            p[4],                        // phone
            p[5],                        // pin
            Double.parseDouble(p[6]),    // balance
            Boolean.parseBoolean(p[7]), // locked
            p[8],                        // type
            Integer.parseInt(p[9]),      // failedAttempts
            Integer.parseInt(p[11]),     // withdrawlCount
            LocalDate.parse(p[12])       // lastResetDate
    );
}

        if (p[0].equals("CURRENT")) {
            return new CurrentAccount(
                    p[1],                        // accNo
                    p[2],                        // name
                    p[3],                        // cnic
                    p[4],                        // phone
                    p[5],                        // pin
                    Double.parseDouble(p[6]),    // balance
                    Boolean.parseBoolean(p[7]),  // locked
                    p[8],                        // type
                    Integer.parseInt(p[9])       // failedAttempts
            );
        }

        return null;
    }

    public void saveTransaction(Transaction t) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE, true))) {
            writer.write(t.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    public List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(TRANSACTIONS_FILE);
        if (!file.exists()) return transactions;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] p = line.split(",");
                Transaction t = new Transaction(p[0], p[1], Double.parseDouble(p[2]));
                transactions.add(t);
            }
        } catch (IOException e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }

        return transactions;
    }

    public void deleteAccount(String id) {
        List<Account> accounts = loadAccounts();
        accounts.removeIf(a -> a.getAccountNumber().equals(id));
        rewriteAccounts(accounts);
    }

    public void updateAccount(Account updated) {
        List<Account> accounts = loadAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equals(updated.getAccountNumber())) {
                accounts.set(i, updated);
                break;
            }
        }
        rewriteAccounts(accounts);
    }

    private void rewriteAccounts(List<Account> accounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE, false))) {
            for (Account a : accounts) {
                writer.write(a.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error rewriting accounts: " + e.getMessage());
        }
    }
}