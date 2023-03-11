package itmo.anasteshap.entities;

import itmo.anasteshap.models.accounts.*;
import itmo.anasteshap.models.accounts.commands.Withdraw;
import itmo.anasteshap.models.dateTimeProvider.Clock;
import lombok.Getter;
import lombok.NonNull;
import itmo.anasteshap.models.Amount;
import itmo.anasteshap.models.Percent;
import itmo.anasteshap.models.accounts.commands.Income;
import itmo.anasteshap.models.accounts.configurations.BankConfiguration;
import itmo.anasteshap.models.accounts.configurations.DepositPercent;
import itmo.anasteshap.models.observer.Observer;
import itmo.anasteshap.models.transaction.Transaction;

import java.math.BigDecimal;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Bank {
    private final List<Account> accounts = new ArrayList<>();
    private final List<Observer<String>> subscribers = new ArrayList<>();
    private final BankConfiguration bankConfiguration;
    private final Clock clock;
    @Getter
    private final UUID id = UUID.randomUUID();
    @Getter
    private final String name;

    public Bank(@NonNull String name, @NonNull Clock clock, @NonNull BankConfiguration bankConfiguration) {
        this.name = name;
        this.clock = clock;
        this.bankConfiguration = bankConfiguration;
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    /**
     * Find account by id
     *
     * @param accountId id of account
     * @return account
     */
    public Account getAccount(UUID accountId) {
        var account = this.accounts.stream().filter(x -> x.getId().equals(accountId)).findFirst(); // по-другому бы как-то
        if (account.isEmpty()) {
            throw new RuntimeException();
        }

        return account.get();
    }

    /**
     * Subscribe subscriber for subscription
     *
     * @param observer subscriber
     */
    public void subscribe(@NonNull Observer<String> observer) {
        if (this.subscribers.contains(observer)) {
            throw new RuntimeException();
        }
        this.subscribers.add(observer);
    }

    /**
     * Unsubscribe subscriber for subscription
     *
     * @param observer subscriber
     */
    public void unsubscribe(@NonNull Observer<String> observer) {
        if (!this.subscribers.remove(observer)) {
            throw new RuntimeException();
        }
    }

    /**
     * Create credit account
     *
     * @param client client
     * @return new credit account
     */
    public Account createCreditAccount(Client client) {
        Account account = new CreditAccount(client, this.bankConfiguration);
        this.accounts.add(account);
        return account;
    }

    /**
     * Create debit account
     *
     * @param client client
     * @return new debit account
     */
    public Account createDebitAccount(Client client) {
        Account account = new DebitAccount(this.clock, client, this.bankConfiguration);
        this.accounts.add(account);
        return account;
    }

    /**
     * Create deposit account
     *
     * @param client client
     * @return new deposit account
     */
    public Account createDepositAccount(Client client, Period periodInDays) {
        Account account = new DepositAccount(this.clock, client, this.bankConfiguration, periodInDays);
        this.accounts.add(account);
        return account;
    }

    /**
     * Change debit percent in bank configuration
     *
     * @param percent new percent
     */
    public void changeDebitPercent(Percent percent) {
        this.bankConfiguration.getDebitAccount().setPercent(percent);
        notify(BankAccountTypes.Debit, "New debit percent: " + percent.value().toString());
    }

    /**
     * Change deposit percent in bank configuration
     *
     * @param depositPercents new percents
     */
    public void changeDepositPercent(List<DepositPercent> depositPercents) {
        this.bankConfiguration.getDepositAccount().setPercents(depositPercents);
        var percents = new StringBuilder();
        depositPercents.forEach(x -> percents.append(String.format("%s - %s: %s\n",
                x.getLeftBorder(),
                x.getRightBorder(),
                x.getPercent().value().multiply(BigDecimal.valueOf(100)))));
        notify(BankAccountTypes.Deposit, "New deposit percents:\n " + percents);
    }

    /**
     * Change credit commission in bank configuration
     *
     * @param commission new commission
     */
    public void changeCreditCommission(Amount commission) {
        this.bankConfiguration.getCreditAccount().setCommission(commission);
        notify(BankAccountTypes.Credit, "New credit commission: " + commission);
    }

    /**
     * Change credit limit in bank configuration
     *
     * @param creditLimit new credit limit
     */
    public void changeCreditLimit(Amount creditLimit) {
        this.bankConfiguration.getCreditAccount().setLimit(creditLimit);
        notify(BankAccountTypes.Credit, "New credit limit: " + creditLimit);
    }

    /**
     * Change limit for dubious client in bank configuration
     *
     * @param limitForDubiousClient new limit for dubious client
     */
    public void changeLimitForDubiousClient(Amount limitForDubiousClient) {
        this.bankConfiguration.setLimitForDubiousClient(limitForDubiousClient);
        String data = "New limit for dubious client: " + limitForDubiousClient;
        notify(BankAccountTypes.Credit, data);
        notify(BankAccountTypes.Debit, data);
        notify(BankAccountTypes.Deposit, data);
    }

    /**
     * Income sum to account
     *
     * @param accountId id of account
     * @param sum       sum for income
     * @return new income transaction
     */
    public Transaction income(UUID accountId, Amount sum) {
        var account = this.accounts.stream()
                .filter(x -> x.getId().equals(accountId))
                .findFirst();
        if (account.isEmpty()) {
            throw new RuntimeException();
        }
        var transaction = new Transaction(new Income(account.get(), sum));
        transaction.doTransaction();
        account.get().saveChanges(transaction);
        return transaction;
    }

    /**
     * Withdraw sum to account
     *
     * @param accountId id of account
     * @param sum       sum for withdraw
     * @return new withdraw transaction
     */
    public Transaction withdraw(UUID accountId, Amount sum) {
        var account = this.accounts.stream()
                .filter(x -> x.getId().equals(accountId))
                .findFirst();
        if (account.isEmpty()) {
            throw new RuntimeException();
        }

        var transaction = new Transaction(new Withdraw(account.get(), sum));
        transaction.doTransaction();
        account.get().saveChanges(transaction);
        return transaction;
    }

    /**
     * Send message of changes to bank accounts
     *
     * @param selectType type of bank account
     * @param data       message of changes
     */
    private void notify(BankAccountTypes selectType, String data) {
        this.accounts.stream()
                .filter(acc -> acc.getType().equals(selectType) && this.subscribers.contains(acc.getClient()))
                .distinct()
                .map(Account::getClient)
                .forEach(x -> x.update(data));
    }
}
