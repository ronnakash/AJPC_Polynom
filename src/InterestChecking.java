public class InterestChecking extends NoServiceChargeChecking{
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
    public void monthlyUpdate(){
        accountBalance += calculateMonthlyInterest();
    }

    public double calculateMonthlyInterest(){
        return (accountInterestRate/100.0)*accountBalance;
    }

    @Override
    public String toString() {
        return super.toString().concat("Account Minimum Balance: " + accountMinimumBalance).concat("Account Interest Rate: " + accountInterestRate+"%");
    }

}