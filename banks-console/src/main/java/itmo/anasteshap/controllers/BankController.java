package itmo.anasteshap.controllers;

import itmo.anasteshap.models.Amount;
import itmo.anasteshap.models.Percent;
import itmo.anasteshap.models.accounts.configurations.DepositPercent;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankController {
    private final DataController data;
    private final Scanner scanner = new Scanner(System.in);

    public BankController(@NonNull DataController data) {
        this.data = data;
    }

    public void create(Void unused) {
        System.out.print("enter a bank name - ");
        String bankName = scanner.next();

        System.out.print("enter a bank debitYearPercent - ");
        Percent debitPercent = new Percent(scanner.nextBigDecimal());

        System.out.println("enter a bank depositPercents");
        List<DepositPercent> depositPercents = makeDepositPercents();

        System.out.print("enter a creditCommission - ");
        Amount creditCommission = new Amount(scanner.nextBigDecimal());

        System.out.print("enter a creditLimit - ");
        Amount creditLimit = new Amount(scanner.nextBigDecimal());

        System.out.print("enter a limitForDubiousClient - ");
        Amount limitForDubiousClient = new Amount(scanner.nextBigDecimal());

        data.getCentralBank().createBank(bankName, debitPercent, depositPercents, creditCommission, creditLimit, limitForDubiousClient);
        System.out.println("+++ successfully +++");
    }

    public void showAll(Void unused) {
        var banks = data.getCentralBank().getBanks();
        if (banks.isEmpty()) {
            System.out.println("no registered bank");
        }
        banks.forEach(x -> System.out.format("name: %s\nid: %s\n\n", x.getName(), x.getId()));
    }

    public void changeConfig(Void unused) {
        System.out.print("bank name - ");
        var bankName = scanner.next();
        var bank = data.getCentralBank().getBankByName(bankName);

        System.out.print("do you want to change debitPercent? (y/n) - ");
        if (scanner.next().equals("y")) {
            System.out.print("new debitPercent - ");
            bank.changeDebitPercent(new Percent(scanner.nextBigDecimal()));
        }

        System.out.print("do you want to change depositPercents? (y/n) - ");
        if (scanner.next().equals("y")) {
            System.out.println("new depositPercents");
            bank.changeDepositPercent(makeDepositPercents());
        }

        System.out.print("do you want to change a creditCommission? (y/n) - ");
        if (scanner.next().equals("y")) {
            System.out.print("new creditCommission - ");
            bank.changeCreditCommission(new Amount(scanner.nextBigDecimal()));
        }

        System.out.print("do you want to change a creditLimit? (y/n) - ");
        if (scanner.next().equals("y")) {
            System.out.print("new creditLimit - ");
            bank.changeCreditLimit(new Amount(scanner.nextBigDecimal()));
        }

        System.out.print("do you want to change a limitForDubiousClient? (y/n) - ");
        if (scanner.next().equals("y")) {
            System.out.print("new limitForDubiousClient - ");
            bank.changeLimitForDubiousClient(new Amount(scanner.nextBigDecimal()));
        }
        System.out.println("+++ successfully +++");
    }

    private List<DepositPercent> makeDepositPercents() {
        List<DepositPercent> depositPercents = new ArrayList<>();
        while (true) {
            System.out.print("enter a bank depositPercent - ");
            Percent percent = new Percent(scanner.nextBigDecimal());

            System.out.print("enter a leftBorder for depositPercent - ");
            Amount leftBorder = new Amount(scanner.nextBigDecimal());

            System.out.print("enter a rightBorder for depositPercent - ");
            Amount rightBorder = new Amount(scanner.nextBigDecimal());

            if (rightBorder.getValue().compareTo(BigDecimal.ZERO) == 0) {
                depositPercents.add(new DepositPercent(percent, leftBorder));
                break;
            }
            depositPercents.add(new DepositPercent(percent, leftBorder, rightBorder));
        }
        return depositPercents;
    }
}
