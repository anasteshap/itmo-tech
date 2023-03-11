package itmo.anasteshap.interfaces;

import itmo.anasteshap.entities.Bank;
import itmo.anasteshap.entities.Client;
import itmo.anasteshap.models.Amount;
import itmo.anasteshap.models.Percent;
import itmo.anasteshap.models.accounts.configurations.DepositPercent;
import itmo.anasteshap.models.dateTimeProvider.RewindClock;
import itmo.anasteshap.models.transaction.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Central bank to control the work of banks
 */
public interface CentralBank {
    /**
     * Get rewind clock
     *
     * @return rewind clock
     */
    RewindClock getRewindClock();

    /**
     * Register client
     *
     * @param name     name
     * @param surname  surname
     * @param address  address
     * @param passport passport
     * @return new client
     */
    Client registerClient(String name, String surname, String address, String passport);

    /**
     * Find bank by name
     *
     * @param name name of bank
     * @return bank or null, if bank doesn't exist
     */
    Optional<Bank> findBankByName(String name);

    /**
     * Find bank by name
     *
     * @param name name of bank
     * @return bank
     */
    Bank getBankByName(String name);

    /**
     * Find client by id
     *
     * @param id id of client
     * @return client or null, if bank doesn't exist
     */
    Optional<Client> findClientById(UUID id);

    /**
     * Find client by id
     *
     * @param id id of client
     * @return client
     */
    Client getClientById(UUID id);

    List<Client> getClients();

    List<Bank> getBanks();

    /**
     * Create new bank
     *
     * @param name                  of bank
     * @param debitPercent          of bank
     * @param depositPercents       of bank
     * @param creditCommission      of bank
     * @param creditLimit           of bank
     * @param limitForDubiousClient of bank
     * @return new bank
     */
    Bank createBank(String name, Percent debitPercent, List<DepositPercent> depositPercents, Amount creditCommission, Amount creditLimit, Amount limitForDubiousClient);

    /**
     * Replenish account by sum
     *
     * @param bankId    id of bank
     * @param accountId id of account
     * @param amount    sum for replenish
     * @return new replenish transaction
     */
    Transaction replenishAccount(UUID bankId, UUID accountId, Amount amount);

    /**
     * Withdraw account by sum
     *
     * @param bankId    id of bank
     * @param accountId id of account
     * @param amount    sum for withdraw
     * @return new withdraw transaction
     */
    Transaction withdrawMoney(UUID bankId, UUID accountId, Amount amount);

    /**
     * Transfer money from one account to another
     *
     * @param bankId1    id of fromBank
     * @param accountId1 id of fromAccount of transaction
     * @param bankId2    id of toBank
     * @param accountId2 id of toAccount of transaction
     * @param amount     id of toAccount of transaction
     * @return new transfer transaction
     */
    Transaction transferMoney(UUID bankId1, UUID accountId1, UUID bankId2, UUID accountId2, Amount amount);

    /**
     * Cancel transaction
     *
     * @param bankId        id of bank
     * @param accountId     id of account of transaction
     * @param transactionId id of transaction
     */
    void cancelTransaction(UUID bankId, UUID accountId, UUID transactionId);
}
