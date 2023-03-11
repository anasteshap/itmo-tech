package itmo.anasteshap.controllers;

import itmo.anasteshap.entities.Bank;
import itmo.anasteshap.models.Amount;
import itmo.anasteshap.models.transaction.Transaction;
import lombok.NonNull;

import java.util.Scanner;
import java.util.UUID;

public class TransactionController {
    private final DataController data;
    private final Scanner scanner = new Scanner(System.in);

    public TransactionController(@NonNull DataController data) {
        this.data = data;
    }

    public void addMoney(Void unused) {
        addOrWithdrawMoney("add");
    }

    public void withdrawMoney(Void unused) {
        addOrWithdrawMoney("withdraw");
    }

    public void transferMoney(Void unused) {
        System.out.print("enter a fromBankName - ");
        Bank fromBank = data.getCentralBank().getBankByName(scanner.next());
        System.out.print("enter a fromAccountId - ");
        UUID fromAccountId = UUID.fromString(scanner.next());
        fromBank.getAccount(fromAccountId);

        System.out.print("enter a toBankName - ");
        Bank toBank = data.getCentralBank().getBankByName(scanner.next());
        System.out.print("enter a toAccountId - ");
        UUID toAccountId = UUID.fromString(scanner.next());
        toBank.getAccount(toAccountId);

        System.out.print("enter a sum to transfer - ");
        Amount sum = new Amount(scanner.nextBigDecimal());
        var transaction = data.getCentralBank()
                .transferMoney(fromBank.getId(), fromAccountId, toBank.getId(), toAccountId, sum);
        System.out.println(transaction.getStatusMessage());
        System.out.println("+++ successfully +++");
    }

    public void cancelTransaction(Void unused) {
        System.out.print("enter a bankName - ");
        Bank bank = this.data.getCentralBank().getBankByName(scanner.next());

        System.out.print("enter a accountId - ");
        UUID accountId = UUID.fromString(scanner.next());
        bank.getAccount(accountId);

        System.out.print("enter a transactionId - ");
        UUID transactionId = UUID.fromString(scanner.next());

        data.getCentralBank().cancelTransaction(bank.getId(), accountId, transactionId);
        var transaction = bank
                .getAccount(accountId)
                .getTransactions()
                .stream().filter(x -> x.getId().equals(transactionId)).findFirst();

        if (transaction.isPresent()) {
            System.out.println(transaction.get().getStatusMessage());
            System.out.println("+++ successfully +++");
        }
    }

    public void showAllTransactionsInAccount(Void unused) {
        System.out.print("enter a bankName - ");
        Bank bank = data.getCentralBank().getBankByName(scanner.next());

        System.out.print("enter a accountId - ");
        UUID accountId = UUID.fromString(scanner.next());
        bank.getAccount(accountId);

        var transactions = bank.getAccount(accountId).getTransactions();
        System.out.format("\n>> bank name: %s\n>> bankId: %s\n\n>> accountId: %s\n", bank.getName(), bank.getId(), accountId);
        transactions.forEach(x -> System.out.format("idTransaction: %s", x.getId()));
        System.out.println("+++ successfully +++");
    }

    private void addOrWithdrawMoney(String action) {
        System.out.print("enter a bankName - ");
        Bank bank = data.getCentralBank().getBankByName(scanner.next());

        System.out.print("enter a accountId - ");
        UUID accountId = UUID.fromString(scanner.next());
        bank.getAccount(accountId);

        System.out.print("enter a sum to " + action + " - ");
        Amount sum = new Amount(scanner.nextBigDecimal());

        if (action.equals("add")) {
            Transaction transaction = data.getCentralBank().withdrawMoney(bank.getId(), accountId, sum);
            System.out.println(transaction.getStatusMessage());
        }

        if (action.equals("withdraw")) {
            Transaction transaction = data.getCentralBank().withdrawMoney(bank.getId(), accountId, sum);
            System.out.println(transaction.getStatusMessage());
        }
        System.out.println("+++ successfully +++");
    }
}
