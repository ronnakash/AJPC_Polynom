import java.util.Objects;

public class ServiceChargeChecking extends CheckingAccount {
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
    public void monthlyUpdate() {
        accountBalance -= accountMonthlyCommissionCharge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ServiceChargeChecking that = (ServiceChargeChecking) o;
        return Double.compare(that.accountMonthlyCommissionCharge, accountMonthlyCommissionCharge) == 0 && Double.compare(that.DEFAULT_MONTHLY_COMMISSION, DEFAULT_MONTHLY_COMMISSION) == 0;
    }

    @Override
    public String toString() {
        return super.toString().concat("\n\tAccount Monthly Commission Charge: " + accountMonthlyCommissionCharge);
    }
}