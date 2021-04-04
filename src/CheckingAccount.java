public abstract class CheckingAccount extends BankAccount {

    public CheckingAccount(String accountNumber, String accountOwnerName, String accountOwnerId, double accountBalance) {
        super(accountNumber, accountOwnerName, accountOwnerId, accountBalance);
    }

    public void writeCheck(double checkAmount) {
        try {
            if (checkAmount > accountBalance) {
                throw new IllegalBalanceException(accountBalance, checkAmount);
            }
            accountBalance -= checkAmount;
        } catch (IllegalBalanceException illegalBalanceException) {
            System.out.println(illegalBalanceException.getMessage());        }

    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}