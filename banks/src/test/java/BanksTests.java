import itmo.anasteshap.entities.Bank;
import itmo.anasteshap.interfaces.CentralBank;
import itmo.anasteshap.models.Amount;
import itmo.anasteshap.models.Percent;
import itmo.anasteshap.models.accounts.Account;
import itmo.anasteshap.models.accounts.configurations.DepositPercent;
import itmo.anasteshap.models.dateTimeProvider.RewindClock;
import itmo.anasteshap.models.transaction.Transaction;
import itmo.anasteshap.models.transaction.TransactionStates;
import itmo.anasteshap.services.CentralBankImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BanksTests {
    private final CentralBank cb = new CentralBankImpl(new RewindClock());

    @Test
    public void creationOfTransactionTest() {
        var bank = createBank();
        var client = cb.registerClient("Anastasiia", "Pinchuk", null, null);
        Account account1 = bank.createDebitAccount(client);

        Transaction transaction1 = cb.replenishAccount(bank.getId(), account1.getId(), new Amount(BigDecimal.valueOf(100000)));
        assertEquals(0, account1.getBalance().getValue().compareTo(BigDecimal.valueOf(100000)));

        cb.cancelTransaction(bank.getId(), account1.getId(), transaction1.getId());
        assertSame(transaction1.getTransactionState(), TransactionStates.Failed);

        Account account2 = bank.createDebitAccount(client);
        Transaction transaction2 = cb.replenishAccount(bank.getId(), account2.getId(), new Amount(BigDecimal.valueOf(500)));
        assertEquals(0, account2.getBalance().getValue().compareTo(BigDecimal.valueOf(500)));

        cb.cancelTransaction(bank.getId(), account2.getId(), transaction2.getId());
        assertEquals(0, account2.getBalance().getValue().compareTo(BigDecimal.ZERO));
    }

    @Test
    public void chargingCommissionTest() {
        var bank = createBank();
        var client = cb.registerClient("Anastasiia", "Pinchuk", null, null);
        Account account = bank.createDebitAccount(client);

        cb.replenishAccount(bank.getId(), account.getId(), new Amount(BigDecimal.valueOf(100000)));
        cb.getRewindClock().rewindTime(31);
        assertTrue(account.getBalance().getValue().compareTo(BigDecimal.valueOf(100000)) > 0);
    }

    @Test
    public void rewindTimeTest() {
        var date1 = cb.getRewindClock().currentTime();
        cb.getRewindClock().rewindTime(10);
        var date2 = cb.getRewindClock().currentTime();
        assertTrue(date1.before(date2));
        assertEquals(10, date2.get(Calendar.DAY_OF_YEAR) - date1.get(Calendar.DAY_OF_YEAR));
    }

    protected Bank createBank() {
        List<DepositPercent> list = new ArrayList<>();
        list.add(new DepositPercent(new Percent(BigDecimal.valueOf(3)), new Amount(BigDecimal.valueOf(0))));

        return cb.createBank(
                "tink",
                new Percent(BigDecimal.valueOf(3)),
                list,
                new Amount(BigDecimal.valueOf(100)),
                new Amount(BigDecimal.valueOf(2000)),
                new Amount(BigDecimal.valueOf(3000)));
    }
}
