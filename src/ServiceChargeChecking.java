
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