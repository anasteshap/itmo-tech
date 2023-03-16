package itmo.anasteshap;

import itmo.anasteshap.chainOfResponsibility.ComponentChain;
import itmo.anasteshap.chainOfResponsibility.ContainerChain;
import itmo.anasteshap.controllers.*;
import itmo.anasteshap.models.dateTimeProvider.RewindClock;
import itmo.anasteshap.services.CentralBankImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class AppManager {
    private final Scanner scanner = new Scanner(System.in);
    private final DataController data;
    private final ContainerChain app = new ContainerChain("app");

    public AppManager() {
        this.data = new DataController(new CentralBankImpl(new RewindClock()));
    }

    public void run() {
        init();
        menu();
        while (true) {
            String console = scanner.nextLine();
            if (console.equals("--menu")) {
                menu();
                continue;
            }

            try {
                var strings = new ArrayList<>(Arrays.asList(console.split("\\s")));

                if (!strings.iterator().hasNext()) {
                    throw new RuntimeException("wrong command");
                }

                app.process(strings.listIterator());
            } catch (Exception e) {
                System.out.format("try again\n%s\n", e.getMessage());
                System.out.println("write for help:\n\tapp --menu");
            }
        }
    }

    private void menu() {
        System.out.println("\n---commands---");

        System.out.println("app bank create");
        System.out.println("app bank showAll");
        System.out.println("app bank changeConfig");
        System.out.println();
        System.out.println("app client create");
        System.out.println("app client current addInfo");
        System.out.println("app client current showInfo");
        System.out.println("app client current change");
        System.out.println("app client showAll");
        System.out.println();
        System.out.println("app account create credit");
        System.out.println("app account create debit");
        System.out.println("app account create deposit");
        System.out.println("app account addMoney");
        System.out.println("app account withdrawMoney");
        System.out.println("app account transferMoney");
        System.out.println("app account cancelTransaction");
        System.out.println("app account showAll");
        System.out.println("app account showAllTransactionsInAccount");
        System.out.println("app account checkBalance");
        System.out.println();
        System.out.println("app rewindTime");
        System.out.println("app dateNow");
        System.out.println("app exit");
        System.out.println("enter --menu for help");
        System.out.println();
    }

    private void init() {
        var controller1 = new BankController(data);
        var controller2 = new ClientController(data);
        var controller3 = new AccountController(data);
        var controller4 = new TransactionController(data);
        var controller5 = new TimeController(data);

        var bankContainer = new ContainerChain("bank");
        bankContainer
                .addSubChain(new ComponentChain(controller1::create, "create"))
                .addNext(new ComponentChain(controller1::showAll, "showAll"))
                .addNext(new ComponentChain(controller1::changeConfig, "changeConfig"));

        var clientCurrentContainer = new ContainerChain("current");
        clientCurrentContainer
                .addSubChain(new ComponentChain(controller2::addCurrentClientInfo, "addInfo"))
                .addNext(new ComponentChain(controller2::showCurrentClientInfo, "showInfo"))
                .addNext(new ComponentChain(controller2::changeCurrent, "change"));

        var clientContainer = new ContainerChain("client");
        clientContainer
                .addSubChain(new ComponentChain(controller2::create, "create"))
                .addNext(clientCurrentContainer)
                .addNext(new ComponentChain(controller2::showAll, "showAll"));

        var accountCreateContainer = new ContainerChain("create");
        accountCreateContainer
                .addSubChain(new ComponentChain(controller3::createCredit, "credit"))
                .addNext(new ComponentChain(controller3::createDebit, "debit"))
                .addNext(new ComponentChain(controller3::createDeposit, "deposit"));

        var accountContainer = new ContainerChain("account");
        accountContainer
                .addSubChain(accountCreateContainer)
                .addNext(new ComponentChain(controller4::addMoney, "addMoney"))
                .addNext(new ComponentChain(controller4::withdrawMoney, "withdrawMoney"))
                .addNext(new ComponentChain(controller4::transferMoney, "transferMoney"))
                .addNext(new ComponentChain(controller4::cancelTransaction, "cancelTransaction"))
                .addNext(new ComponentChain(controller3::showAll, "showAll"))
                .addNext(new ComponentChain(controller4::showAllTransactionsInAccount, "showAllTransactionsInAccount"))
                .addNext(new ComponentChain(controller3::checkBalance, "checkBalance"));

        this.app
                .addSubChain(bankContainer)
                .addNext(clientContainer)
                .addNext(accountContainer)
                .addNext(new ComponentChain(controller5::rewindTime, "rewindTime"))
                .addNext(new ComponentChain(controller5::dateNow, "dateNow"))
                .addNext(new ComponentChain(controller5::exit, "exit"));
    }
}
