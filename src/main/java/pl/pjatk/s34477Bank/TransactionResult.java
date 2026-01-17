package pl.pjatk.s34477Bank;

public class TransactionResult {
    private Status status;
    private String message;
    private double newBalance;

    public TransactionResult(Status status, double newBalance) {
        this.status = status;
        this.message = message;
        this.newBalance = newBalance;
    }
    public Status getStatus() {
        return status;
    }
    public double getNewBalance() {
        return newBalance;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public void setNewBalance(double newBalance) {
        this.newBalance = newBalance;
    }
}
