package itmo.anasteshap.services;

import itmo.anasteshap.entities.Bank;
import itmo.anasteshap.entities.Client;
import itmo.anasteshap.exceptions.BankException;
import itmo.anasteshap.exceptions.ClientException;
import itmo.anasteshap.interfaces.CentralBank;
import itmo.anasteshap.models.accounts.Account;
import itmo.anasteshap.models.accounts.commands.Transfer;
import itmo.anasteshap.models.accounts.configurations.*;
import itmo.anasteshap.models.transaction.Transaction;
import lombok.Getter;
import lombok.NonNull;
import itmo.anasteshap.models.Amount;
import itmo.anasteshap.models.Percent;
import itmo.anasteshap.models.dateTimeProvider.RewindClock;

import java.util.*;

public class CentralBankImpl implements CentralBank {
    private final List<Bank> banks = new ArrayList<>();
    private final List<Client> clients = new ArrayList<>();
    @Getter
    private final RewindClock rewindClock;

    public CentralBankImpl(@NonNull RewindClock rewindClock) {
        this.rewindClock = rewindClock;
    }

    @Override
    public Client registerClient(String name, String surname, String address, String passport) {
        var client = Client.builder()
                .name(name)
                .surname(surname)
                .address(address)
                .passport(passport)
                .build();
        clients.add(client);
        return client;
    }

    @Override
    public Optional<Bank> findBankByName(String name) {
        return banks.stream().filter(x -> x.getName().equals(name)).findFirst();
    }

    @Override
    public Bank getBankByName(String name) {
        return findBankByName(name).orElseThrow(() -> BankException.bankDoesNotExist(name));
    }

    @Override
    public Optional<Client> findClientById(UUID id) {
        return clients.stream().filter(x -> x.getId().equals(id)).findFirst();
    }

    @Override
    public Client getClientById(UUID id) {
        return findClientById(id).orElseThrow(() -> ClientException.clientDoesNotExist(id));
    }

    @Override
    public List<Client> getClients() {
        return Collections.unmodifiableList(clients);
    }

    @Override
    public List<Bank> getBanks() {
        return Collections.unmodifiableList(banks);
    }

    @Override
    public Bank createBank(String name, Percent debitPercent, List<DepositPercent> depositPercents, Amount creditCommission, Amount creditLimit, Amount limitForDubiousClient) {
        if (!banks.stream().filter(x -> x.getName().equals(name)).toList().isEmpty()) {
            throw BankException.bankAlreadyExists(name);
        }

        var credit = new CreditAccountConfiguration(creditCommission, creditLimit);
        var debit = new DebitAccountConfiguration(debitPercent);
        var deposit = new DepositAccountConfiguration(depositPercents);
        var bankConfiguration = new BankConfiguration(credit, debit, deposit, limitForDubiousClient);

        var bank = new Bank(name, this.rewindClock, bankConfiguration);
        banks.add(bank);
        return bank;
    }

    @Override
    public Transaction replenishAccount(UUID bankId, UUID accountId, Amount amount) {
        var bank = banks.stream().filter(x -> x.getId().equals(bankId)).findFirst();
        return bank.orElseThrow(() -> BankException.bankDoesNotExist(String.format("[id: %s]", bankId))).income(accountId, amount);
    }

    @Override
    public Transaction withdrawMoney(UUID bankId, UUID accountId, Amount amount) {
        var bank = banks.stream().filter(x -> x.getId().equals(bankId)).findFirst();
        return bank.orElseThrow(() -> BankException.bankDoesNotExist(String.format("[id: %s]", bankId))).withdraw(accountId, amount);
    }

    @Override
    public Transaction transferMoney(UUID bankId1, UUID accountId1, UUID bankId2, UUID accountId2, Amount amount) {
        var bank1 = banks.stream().filter(x -> x.getId().equals(bankId1)).findFirst();
        var bank2 = banks.stream().filter(x -> x.getId().equals(bankId2)).findFirst();

        Account fromAccount = bank1.orElseThrow(() -> BankException.bankDoesNotExist(String.format("[id: %s]", bankId1))).getAccount(accountId1);
        Account toAccount = bank2.orElseThrow(() -> BankException.bankDoesNotExist(String.format("[id: %s]", bankId2))).getAccount(accountId2);

        var transaction = new Transaction(new Transfer(toAccount, fromAccount, amount));
        transaction.doTransaction();
        toAccount.saveChanges(transaction);
        fromAccount.saveChanges(transaction);
        return transaction;
    }

    @Override
    public void cancelTransaction(UUID bankId, UUID accountId, UUID transactionId) {
        var bank = banks.stream().filter(x -> x.getId().equals(bankId)).findFirst();
        bank.orElseThrow(() -> BankException.bankDoesNotExist(String.format("[id: %s]", bankId))).getAccount(accountId).getTransaction(transactionId).undo();
    }
}
