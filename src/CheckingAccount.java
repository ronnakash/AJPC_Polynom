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