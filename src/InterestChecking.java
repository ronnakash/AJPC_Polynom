import java.util.Objects;

public class InterestChecking extends NoServiceChargeChecking {
    protected int accountInterestRate;
    final int DEFAULT_INTEREST_RATE = 1;

    public InterestChecking(String accountNumber, String accountOwnerName, String accountOwnerId, double accountBalance) {
        super(accountNumber, accountOwnerName, accountOwnerId, accountBalance);
        accountInterestRate = DEFAULT_INTEREST_RATE;
    }

    public InterestChecking(String accountNumber, String accountOwnerName, String accountOwnerId, double accountBalance, int accountInterestRate) {
        super(accountNumber, accountOwnerName, accountOwnerId, accountBalance);
        this.accountInterestRate = accountInterestRate;
    }

    public int getAccountInterestRate() {
        return accountInterestRate;
    }

    public void setAccountInterestRate(int accountInterestRate) {
        this.accountInterestRate = accountInterestRate;
    }

    @Override
    public void monthlyUpdate() {
        accountBalance += calculateMonthlyInterest();
    }

    public double calculateMonthlyInterest() {
        return (accountInterestRate / 100.0) * accountBalance;
    }

    @Override
    public String toString() {
        return super.toString().concat("\n\tAccount Interest Rate: " + accountInterestRate + "%");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        InterestChecking that = (InterestChecking) o;
        return accountInterestRate == that.accountInterestRate && DEFAULT_INTEREST_RATE == that.DEFAULT_INTEREST_RATE;
    }

}