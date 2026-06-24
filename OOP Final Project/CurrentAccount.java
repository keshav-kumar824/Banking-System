
public class CurrentAccount extends Account {

    private final double overDraftLimit = 15000;

    CurrentAccount(String accountNumber, String ownerName, String cnic, String phoneNumber,
                   String pin, double balance, boolean isLocked, String accountType, int failedAttempts) {
        super(accountNumber, ownerName, cnic, phoneNumber, pin, balance, isLocked, accountType, failedAttempts);
    }

    @Override
    public void withDraw(double amount) {
        if (getBalance() - amount >= -overDraftLimit) {
            super.withDraw(amount);
        } else {
            System.out.println("Over Draft Limit Exceeded!");
        }
    }

    @Override
    public void applyRules() {
        if (getBalance() < 0) {
            double overDraftInterest = Math.abs(getBalance()) * 0.015;
            super.withDraw(overDraftInterest);
            System.out.println("OverDraft Interest: " + overDraftInterest);
        }
    }

    public double getOverDraftLimit() {
        return overDraftLimit;
    }

    @Override
    public String toString() {
        return super.toString() + " | Over Draft Limit: " + overDraftLimit;
    }

    @Override
    public String toFileString() {
        return "CURRENT," + super.toFileString();
    }
}
