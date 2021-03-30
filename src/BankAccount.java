import java.util.Objects;

public abstract class BankAccount {
    protected String accountNumber;
    protected String accountOwnerName;
    protected String accountOwnerId;
    protected double accountBalance;

    public BankAccount(String accountNumber, String accountOwnerName, String accountOwnerId, double accountBalance) {
        this.accountNumber = accountNumber;
        this.accountOwnerName = accountOwnerName;
        this.accountOwnerId = accountOwnerId;
        this.accountBalance = accountBalance;
    }

    public class IllegalBalanceException extends RuntimeException{
        double accountBalance;
        double withdrawalAmount;

        public IllegalBalanceException(double accountBalance, double withdrawalAmount) {
            super("cannot withdraw the amount " + withdrawalAmount + " from account with balance of "+accountBalance);
            this.accountBalance = accountBalance;
            this.withdrawalAmount = withdrawalAmount;
        }
    }

    public abstract void monthlyUpdate();


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountOwnerName() {
        return accountOwnerName;
    }

    public void setAccountOwnerName(String accountOwnerName) {
        this.accountOwnerName = accountOwnerName;
    }

    public String getAccountOwnerId() {
        return accountOwnerId;
    }

    public void setAccountOwnerId(String accountOwnerId) {
        this.accountOwnerId = accountOwnerId;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Double.compare(that.accountBalance, accountBalance) == 0 && Objects.equals(accountNumber, that.accountNumber) && Objects.equals(accountOwnerName, that.accountOwnerName) && Objects.equals(accountOwnerId, that.accountOwnerId);
    }

    @Override
    public String toString() {
        String accountDetails = "Account Number: ";
        accountDetails = accountDetails.concat(accountNumber).concat("\n Owner Name: ").concat(accountOwnerName).concat("\t Owner id: ").concat(accountOwnerId).concat("\n Amount: ").concat(String.valueOf(accountBalance));
        return accountDetails;
    }

    public void depositToAccount(double depositAmount){
        accountBalance += depositAmount;
    }

    public void withdrawFromAccount(double withdrawAmount) throws IllegalBalanceException{
        if(withdrawAmount > accountBalance){
            throw new IllegalBalanceException(withdrawAmount, accountBalance);
        }
        accountBalance -= withdrawAmount;
    }



}
