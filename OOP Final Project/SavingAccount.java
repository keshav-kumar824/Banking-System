import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class SavingAccount extends Account {

    private double interestRate;
    private final double minimumBalance = 1000;
    private LocalDate lastResetDate = LocalDate.now();
    private int withDrawlCount = 0;

    SavingAccount(double interestRate, String accountNumber, String ownerName, String cnic, String phoneNumber,
                  String pin, double balance, boolean isLocked, String accountType, int failedAttempts,
                  int withDrawlCount, LocalDate lastResetDate) {

        super(accountNumber, ownerName, cnic, phoneNumber, pin, balance, isLocked, accountType, failedAttempts);

        this.interestRate = interestRate;
        this.withDrawlCount = withDrawlCount;
        this.lastResetDate = lastResetDate;
    }

    @Override
    public void withDraw(double amount) {
        if (getBalance() - amount < minimumBalance) {
            System.out.println("Minimum balance violation!");
            return;
        }
        super.withDraw(amount);
        withDrawlCount++;
        applyRules();
    }

    @Override
    public void applyRules() {
        LocalDate today = LocalDate.now();
        long daysPassed = ChronoUnit.DAYS.between(lastResetDate, today);
        if (daysPassed >= 30) {
            withDrawlCount = 0;
            lastResetDate = today;
        }
        if (withDrawlCount >= 6) {
            lockAccount();
            System.out.println("Account Locked, Monthly Withdrawal Limit Reached!");
        }
    }

    public void applyInterest() {
        if (getStatusOfAccount()) {
            System.out.println("Account is Locked");
            return;
        }
        double monthlyInterest = getBalance() * interestRate / 100 / 12;
        deposit(monthlyInterest);
        System.out.println("Interest Applied: " + monthlyInterest);
    }

    public double getInterestRate() { return interestRate; }
    public int getWithdrawalCount() { return withDrawlCount; }
    public LocalDate getLastResetDate() { return lastResetDate; }

    @Override
    public String toString() {
        return super.toString()
                + " | Interest Rate: " + interestRate + "%"
                + " | Minimum Balance: " + minimumBalance
                + " | Monthly Withdrawals: " + withDrawlCount + "/6";
    }

    @Override
    public String toFileString() {
        return "SAVING,"
                + super.toFileString()
                + "," + interestRate
                + "," + withDrawlCount
                + "," + lastResetDate;
    }
}