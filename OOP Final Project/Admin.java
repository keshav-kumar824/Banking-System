public class Admin {
    private String adminId;
    private String adminPin;
    private FileManager fm;

    public Admin(String adminId, String adminPin) {
        this.adminId = adminId;
        this.adminPin = adminPin;
        this.fm = new FileManager();
    }

    public boolean login(String pin) {
        return adminPin.equals(pin);
    }

    public void viewAllAccounts() {
        for (Account a : fm.loadAccounts()) {
            System.out.println(a);
        }
    }

    public Account searchAccount(String id) {
        for (Account a : fm.loadAccounts()) {
            if (a.getAccountNumber().equals(id)) {
                return a;
            }
        }
        return null;
    }

    public void freezeAccount(String id) {
        Account a = searchAccount(id);
        if (a != null) {
            a.lockAccount();
            fm.updateAccount(a);
        }
    }

    public void unfreezeAccount(String id) {
    Account a = searchAccount(id);
    if (a != null) {
        a.unLockAccount();
        fm.updateAccount(a);
        System.out.println("Account unfrozen.");
    } else {
        System.out.println("Account not found.");
    }
}
    public void deleteAccount(String id) {
        fm.deleteAccount(id);
    }

    public void viewAllTransactions() {
        for (Transaction t : fm.loadTransactions()) {
            System.out.println(t);
        }
    }
}