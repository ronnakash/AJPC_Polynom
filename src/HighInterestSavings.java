public class HighInterestSavings extends SavingsAccount {
    protected double accountMinimumBalance;
    final double DEFAULT_MINIMUM_BALANCE = 1000;

    public HighInterestSavings(String accountNumber, String accountOwnerName, String accountOwnerId, double accountBalance) {
        super(accountNumber, accountOwnerName, accountOwnerId, accountBalance);
        this.accountMinimumBalance = DEFAULT_MINIMUM_BALANCE;
    }

    public HighInterestSavings(String accountNumber, String accountOwnerName, String accountOwnerId, double accountBalance, double accountMinimumBalance) {
        super(accountNumber, accountOwnerName, accountOwnerId, accountBalance);
        this.accountMinimumBalance = accountMinimumBalance;
    }

    public double getAccountMinimumBalance() {
        return accountMinimumBalance;
    }

    public void setAccountMinimumBalance(double accountMinimumBalance) {
        this.accountMinimumBalance = accountMinimumBalance;
    }

    public void withdrawFromAccount(double withdrawAmount) throws IllegalBalanceException {
        try {
            if (withdrawAmount > accountBalance - accountMinimumBalance) {
                throw new IllegalBalanceException(accountBalance ,withdrawAmount, accountMinimumBalance);
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
        HighInterestSavings that = (HighInterestSavings) o;
        return Double.compare(that.accountMinimumBalance, accountMinimumBalance) == 0;
    }

}
