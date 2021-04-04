import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public abstract class BankAccount {
    protected String accountNumber;
    protected String accountOwnerName;
    protected String accountOwnerId;
    protected double accountBalance;

    public BankAccount(String accountNumber, String accountOwnerName, String accountOwnerId, double accountBalance) {
        this.accountNumber = accountNumber;
        this.accountOwnerName = accountOwnerName;
        this.accountOwnerId = accountOwnerId;
        this.accountBalance = accountBalance;
    }

    public static class IllegalBalanceException extends RuntimeException {
        double accountBalance;
        double withdrawalAmount;
        double minimumBalance;

        public IllegalBalanceException(double accountBalance, double withdrawalAmount) {
            super("cannot withdraw the amount " + withdrawalAmount + " from account with balance of " + accountBalance);
            this.accountBalance = accountBalance;
            this.withdrawalAmount = withdrawalAmount;
            this.minimumBalance = 0.0;
        }

        public IllegalBalanceException(double accountBalance, double withdrawalAmount, double minimumBalance) {
            super("cannot withdraw the amount " + withdrawalAmount + " from account with balance of " + accountBalance + " and minimum balance of " + minimumBalance);
            this.accountBalance = accountBalance;
            this.withdrawalAmount = withdrawalAmount;
            this.minimumBalance = minimumBalance;
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
        return Double.compare(that.accountBalance, accountBalance) == 0 && Objects.equals(accountNumber, that.accountNumber)
                && Objects.equals(accountOwnerName, that.accountOwnerName) && Objects.equals(accountOwnerId, that.accountOwnerId);
    }

    @Override
    public String toString() {
        String accountDetails = "Account Number: ";
        accountDetails = accountDetails.concat(accountNumber).concat("\n\tOwner Name: ").concat(accountOwnerName).concat("\n\tOwner id: ").concat(accountOwnerId).concat("\n\tAmount: ").concat(String.valueOf(accountBalance));
        return accountDetails;
    }

    public void depositToAccount(double depositAmount) {
        accountBalance += depositAmount;
    }

    public void withdrawFromAccount(double withdrawAmount) throws IllegalBalanceException {
        try {
            if (withdrawAmount > accountBalance) {
                throw new IllegalBalanceException(withdrawAmount, accountBalance);
            }
            accountBalance -= withdrawAmount;
        }
        catch (IllegalBalanceException illegalBalanceException) {
            System.out.println(illegalBalanceException.getMessage());
        }
    }

    public static void main(String[] args) throws IllegalBalanceException{
        //list of bank accounts
        List<BankAccount> bankAccounts = new ArrayList<BankAccount>();
        //fill lists with instances of all bank account types
        fillAccountList(bankAccounts);
        //print all bank account details as initialised
        System.out.println("Initial bank accounts:");
        printBankAccountDetails(bankAccounts);
        //do some random actions with accounts
        System.out.println("Doing some actions on each account: ");
        for (int i=0; i<2; i++){
            for (BankAccount bankAccount : bankAccounts)
                doRandomActionOnAccount(bankAccount);
        }
        //monthly update on all accounts (also prints details before and after update)
        System.out.println("Monthly update:");
        monthlyUpdateAllAccounts(bankAccounts);
    }

    public static void fillAccountList(List<BankAccount> bankAccounts) {
        // Accounts with default properties
        bankAccounts.add(new SavingsAccount("11\\112233", "Ron Nakash", "313363384", 500));
        bankAccounts.add(new HighInterestSavings("12\\123456", "Avi Cohen", "12345678", 1000.0));
        bankAccounts.add(new InterestChecking("13\\123456", "Dorit Cohen", "23456789", 2000.0));
        bankAccounts.add(new ServiceChargeChecking("14\\154893", "David Levi", "485632489", 3000.0));
        bankAccounts.add(new NoServiceChargeChecking("15\\424893", "Doron Levi", "78923249", 4000.0));
        // Accounts with non default properties
        bankAccounts.add(new SavingsAccount("31\\148933", "Avi Cohen", "12345678", 5000, 5));
        bankAccounts.add(new HighInterestSavings("12\\123456", "Ron Nakash", "313363384", 300.0, 200.0));
        bankAccounts.add(new InterestChecking("13\\123456", "David Levi", "23456789", 2000.0, 2));
        bankAccounts.add(new ServiceChargeChecking("54\\5464621", "Dorit Cohen", "485632489", 30000.0, 20));
        bankAccounts.add(new NoServiceChargeChecking("67\\424893", "Dor Aviv", "78923249", 40000.0, 2500.0));
    }

    public static void printBankAccountDetails(List<BankAccount> bankAccounts) {
        for (BankAccount bankAccount : bankAccounts)
            System.out.println(bankAccount);
    }

    public static void monthlyUpdateAllAccounts(List<BankAccount> bankAccounts) {
        for (BankAccount bankAccount : bankAccounts) {
            System.out.println(bankAccount);
            bankAccount.monthlyUpdate();
            System.out.println("\t\tAccount balance after update: " + bankAccount.accountBalance);
        }
    }

    public static void doRandomActionOnAccount(BankAccount bankAccount) throws IllegalBalanceException{
        Random rand = new Random();
        int action;
        double amount = rand.nextDouble() * bankAccount.accountBalance * 1.5;
        if (bankAccount instanceof CheckingAccount)
            action = rand.nextInt(3);
        else
            action = rand.nextInt(2);
        switch (action) {
            case 0:
                System.out.println("depositing " + amount + " to account " + bankAccount.accountNumber + " with total " + bankAccount.accountBalance);
                bankAccount.depositToAccount(amount);
                System.out.println("total after deposit: " + bankAccount.accountBalance);
                break;
            case 1:
                System.out.println("withdrawing " + amount + " from account " + bankAccount.accountNumber + " with total " + bankAccount.accountBalance);
                bankAccount.withdrawFromAccount(amount);
                System.out.println("total after withdrawal: " + bankAccount.accountBalance);
                break;
            case 2:
                System.out.println("writing check of sum " + amount + " from account " + bankAccount.accountNumber + " with balance of " + bankAccount.accountBalance);
                ((CheckingAccount) bankAccount).writeCheck(amount);
                System.out.println("total after check written: " + bankAccount.accountBalance);
        }
    }



}
