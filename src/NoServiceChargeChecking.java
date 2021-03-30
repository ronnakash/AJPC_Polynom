public class NoServiceChargeChecking extends CheckingAccount{
    protected double accountMinimumBalance;
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
            throw new IllegalBalanceException(withdrawAmount, getAccountBalance());
        }
        accountBalance -= withdrawAmount;
    }

    @Override
    public String toString() {
        return super.toString().concat("Account Minimum Balance: " + accountMinimumBalance);
    }
}
