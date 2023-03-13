package itmo.anasteshap.controllers;


import itmo.anasteshap.entities.Bank;
import itmo.anasteshap.exceptions.AccountException;
import lombok.NonNull;

import java.time.Period;
import java.util.Scanner;
import java.util.UUID;

public class AccountController {
    private final DataController data;
    private final Scanner scanner = new Scanner(System.in);

    public AccountController(@NonNull DataController data) {
        this.data = data;
    }

    public void showAll(Void unused) {
        var banks = data.getCentralBank().getBanks();
        if (banks.isEmpty()) {
            System.out.println("no registered banks and accounts");
        }

        for (Bank bank : banks) {
            if (bank.getAccounts().isEmpty()) {
                System.out.format("----------bank name: %s :((((", bank.getName());
                continue;
            }

            System.out.format("----------bank name: %s", bank.getName());
            System.out.format("----------bank id: %s", bank.getId());
            System.out.println("accounts:");
            bank.getAccounts().forEach(x -> System.out.format("account id: %s\nclient id: %s\nbalance: %s\ntype: %s\n",
                    x.getId(), x.getClient().getId(), x.getBalance(), x.getType()));
        }
    }

    public void createCredit(Void unused) {
        if (data.getCurrentClient() == null) {
            System.out.println("no registered client, create client");
            return;
        }

        getBank().createCreditAccount(data.getCurrentClient());
        System.out.println("+++ successfully +++");
    }

    public void createDebit(Void unused) {
        if (data.getCurrentClient() == null) {
            System.out.println("no registered client, create client");
            return;
        }

        getBank().createDebitAccount(data.getCurrentClient());
        System.out.println("+++ successfully +++");
    }

    public void createDeposit(Void unused) {
        if (data.getCurrentClient() == null) {
            System.out.println("no registered client, create client");
            return;
        }

        System.out.println("enter a timeSpan (count days) - ");
        int periodInDays = scanner.nextInt();

        if (periodInDays == 0) {
            throw AccountException.invalidPeriodOfAccount();
        }

        var bank = getBank();
        bank.createDepositAccount(data.getCurrentClient(), Period.ofDays(periodInDays));
        System.out.println("+++ successfully +++");
    }

    public void checkBalance(Void unused) {
        var bank = getBank();
        System.out.println("enter an accountId - ");
        var accountId = UUID.fromString(scanner.next());
        var account = bank.getAccount(accountId);
        System.out.format("accountId: %s", accountId);
        System.out.format("balance: %s", account.getBalance());
        System.out.println("+++ successfully +++");
    }

    private Bank getBank() {
        System.out.println("enter a bank name - ");
        String bankName = scanner.nextLine();
        return data.getCentralBank().getBankByName(bankName);
    }
}
