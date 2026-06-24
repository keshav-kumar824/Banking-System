abstract class Account {

    private String accountNumber;
    private String ownerName;
    private String cnic;
    private String phoneNumber;
    private String pin;
    private double balance;
    private boolean isLocked;
    private String accountType;
    private int failedAttempts = 0;

    Account(String accountNumber, String ownerName, String cnic, String phoneNumber,
            String pin, double balance, boolean isLocked, String accountType, int failedAttempts) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.cnic = cnic;
        this.phoneNumber = phoneNumber;
        this.pin = pin;
        this.balance = balance;
        this.isLocked = isLocked;
        this.accountType = accountType;
        this.failedAttempts = failedAttempts;
    }

    public void deposit(double amount) {
        if (isLocked) {
            System.out.println("Account is locked! You cannot deposit.");
            return;
        }
        if (amount > 0) {
            balance += amount;
        } else {
            System.out.println("Amount cannot be negative!");
        }
    }

    public void withDraw(double amount) {
        if (isLocked) {
            System.out.println("Account is locked! You cannot withdraw.");
            return;
        }
        balance -= amount;
    }

    public double getBalance() { return balance; }
    public String getOwnerName() { return ownerName; }
    public String getCnic() { return cnic; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }
    public String getAccountNumber() { return accountNumber; }
    public String getAccountType() { return accountType; }
    public int getFailedAttempts() { return failedAttempts; }

    public boolean authenticatePin(String enteredPin) {
        if (isLocked) {
            System.out.println("Account is locked! Contact admin.");
            return false;
        }
        if (enteredPin.equals(pin)) {
            failedAttempts = 0;
            System.out.println("Access Granted");
            return true;
        }
        failedAttempts++;
        if (failedAttempts >= 3) {
            isLocked = true;
            System.out.println("Account Locked!");
            return false;
        }
        System.out.println("Access not granted. Attempts remaining: " + (3 - failedAttempts));
        return false;
    }

    protected void lockAccount() { isLocked = true; }
    protected void unLockAccount() { 
        isLocked = false;
        failedAttempts = 0;
    }
    public boolean getStatusOfAccount() { return isLocked; }

    abstract void applyRules();

    @Override
    public String toString() {
        return "Account #" + accountNumber +
                " | Owner: " + ownerName +
                " | CNIC: " + cnic +
                " | Phone: " + phoneNumber +
                " | Type: " + accountType +
                " | Balance: " + balance +
                " | Status: " + (isLocked ? "Locked" : "Active");
    }

    public String toFileString() {
        return accountNumber + "," +
               ownerName + "," +
               cnic + "," +
               phoneNumber + "," +
               pin + "," +
               balance + "," +
               isLocked + "," +
               accountType + "," +
               failedAttempts;
    }
}