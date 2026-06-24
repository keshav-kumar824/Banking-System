import java.time.LocalDateTime;

public class Transaction {
    private String accountNumber;
    private String type;
    private double amount;
    private String dateTime;

    Transaction(String accountNumber , String type , double amount ){
        this.type=type;
        this.accountNumber=accountNumber;
        this.amount=amount;
        this.dateTime=LocalDateTime.now().toString();
    }
    public String getType(){
        return type;
    }
    public String getAccountNumber(){
        return this.accountNumber;
    }
    public double getAmount(){
        return amount;
    }
    public String getDateTime(){
        return dateTime;
    }
    @Override
    public String toString(){
        return "Account Number: " + accountNumber + " | Type: " + type + " | Amount: " + amount + " | Date time: " + dateTime;
    }

    public String toFileString() {
    return accountNumber + "," + type + "," + amount + "," + dateTime;
    }
}