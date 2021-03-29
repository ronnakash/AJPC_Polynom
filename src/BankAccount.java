import java.util.Objects;

public abstract class BankAccount {
    private String accountNumber;
    private String accountOwnerName;
    private String accountOwnerId;
    private double accountBalance;

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
        String accountDetails = new String("Account Number: ");
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

    public abstract class CheckingAccount extends BankAccount{

        public CheckingAccount(String accountNumber, String accountOwnerName, String accountOwnerId, double accountBalance) {
            super(accountNumber, accountOwnerName, accountOwnerId, accountBalance);
        }

        public void writeCheck(double checkAmount){
            if(checkAmount > accountBalance){
                throw new IllegalBalanceException(checkAmount, accountBalance);
            }
            accountBalance -= checkAmount;
        }
    }

    public class ServiceChargeChecking extends CheckingAccount{
        double accountMonthlyCommissionCharge;
        final double DEFAULT_MONTHLY_COMMISSION = 10;

        public ServiceChargeChecking(String accountNumber, String accountOwnerName, String accountOwnerId, double accountBalance) {
            super(accountNumber, accountOwnerName, accountOwnerId, accountBalance);
            accountMonthlyCommissionCharge = DEFAULT_MONTHLY_COMMISSION; //default
        }
        public ServiceChargeChecking(String accountNumber, String accountOwnerName, String accountOwnerId, double accountBalance, double monthlyChargeAmount) {
            super(accountNumber, accountOwnerName, accountOwnerId, accountBalance);
            accountMonthlyCommissionCharge = monthlyChargeAmount;
        }

        public double getAccountMonthlyCommissionCharge() {
            return accountMonthlyCommissionCharge;
        }

        public void setAccountMonthlyCommissionCharge(double accountMonthlyCommissionCharge) {
            this.accountMonthlyCommissionCharge = accountMonthlyCommissionCharge;
        }

        @Override
        public void monthlyUpdate(){
            accountBalance -= accountMonthlyCommissionCharge;
        }
    }



    public class NoServiceChargeChecking extends CheckingAccount{
        double accountMinimumBalance;
        final double DEFAULT_MINIMUM_BALANCE = 100;
        
        public NoServiceChargeChecking(String accountNumber, String accountOwnerName, String accountOwnerId, double accountBalance) {
            super(accountNumber, accountOwnerName, accountOwnerId, accountBalance);
            accountMinimumBalance = DEFAULT_MINIMUM_BALANCE;
        }
        public NoServiceChargeChecking(String accountNumber, String accountOwnerName, String accountOwnerId, double accountBalance, double minimumBalance) {
            super(accountNumber, accountOwnerName, accountOwnerId, accountBalance);
            accountMinimumBalance = minimumBalance;
        }

        public double getAccountMinimumBalance() {
            return accountMinimumBalance;
        }

        public void setAccountMinimumBalance(double accountMinimumBalance) {
            this.accountMinimumBalance = accountMinimumBalance;
        }

        @Override
        public void monthlyUpdate() {}

        public void withdrawFromAccount(double withdrawAmount) throws IllegalBalanceException{
            if(withdrawAmount > accountBalance + accountMinimumBalance){
                throw new IllegalBalanceException(withdrawAmount, accountBalance);
            }
            accountBalance -= withdrawAmount;
        }

        @Override
        public String toString() { //TODO: make sure not recursive!
            String temp = this.toString();
            return temp.concat("Account Minimum Balance: " + accountMinimumBalance);
        }
    }


}
