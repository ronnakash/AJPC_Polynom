import java.util.Objects;

public class NoServiceChargeChecking extends CheckingAccount {
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
    public void monthlyUpdate() {
    }

    public void withdrawFromAccount(double withdrawAmount) throws IllegalBalanceException {
        try {
            if (withdrawAmount > accountBalance - accountMinimumBalance) {
                throw new IllegalBalanceException(accountBalance, withdrawAmount, accountMinimumBalance);
            }
            accountBalance -= withdrawAmount;
        } catch (IllegalBalanceException illegalBalanceException) {
            System.out.println(illegalBalanceException.getMessage());
        }
    }

    @Override
    public String toString() {
        return super.toString().concat("\n\tAccount Minimum Balance: " + accountMinimumBalance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NoServiceChargeChecking that = (NoServiceChargeChecking) o;
        return Double.compare(that.accountMinimumBalance, accountMinimumBalance) == 0 && Double.compare(that.DEFAULT_MINIMUM_BALANCE, DEFAULT_MINIMUM_BALANCE) == 0;
    }

}
