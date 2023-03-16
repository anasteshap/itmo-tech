package itmo.anasteshap.controllers;

import joptsimple.internal.Strings;
import lombok.NonNull;

import java.util.Scanner;
import java.util.UUID;

public class ClientController {
    private final DataController data;
    private final Scanner scanner = new Scanner(System.in);

    public ClientController(@NonNull DataController data) {
        this.data = data;
    }

    public void create(Void unused) {
        System.out.print("enter an name - ");
        var name = scanner.nextLine();

        System.out.print("enter an surName - ");
        var surName = scanner.nextLine();

        System.out.print("enter an address - ");
        var address = scanner.nextLine();

        System.out.print("enter an passport - ");
        var passport = scanner.nextLine();

        var client = data.getCentralBank().registerClient(name, surName, address, passport);
        data.changeCurrentClient(client);
        System.out.println("+++ successfully +++");
    }

    public void addCurrentClientInfo(Void unused) {
        if (data.getCurrentClient() == null) {
            System.out.println("no registered client");
            return;
        }

        System.out.print("enter an address - ");
        String address = scanner.nextLine();
        if (!Strings.isNullOrEmpty(address)) {
            data.getCurrentClient().setAddress(address);
        }

        System.out.print("enter an passport - ");
        String passport = scanner.nextLine();
        if (!Strings.isNullOrEmpty(passport)) {
            data.getCurrentClient().setPassport(passport);
        }
        System.out.println("+++ successfully +++");
    }

    public void showCurrentClientInfo(Void unused) {
        if (data.getCurrentClient() == null) {
            System.out.println("no registered client");
            return;
        }

        System.out.format("name: %s\n", data.getCurrentClient().getName());
        System.out.format("surname: %s\n", data.getCurrentClient().getSurname());
        System.out.format("address: %s\n", data.getCurrentClient().getAddress());
        System.out.format("passport: %s\n", data.getCurrentClient().getPassport());
        System.out.println("+++ successfully +++");
    }

    public void changeCurrent(Void unused) {
        System.out.print("enter a new clientId - ");
        UUID id = UUID.fromString(scanner.next());
        data.changeCurrentClient(data.getCentralBank().getClientById(id));
    }

    public void showAll(Void unused) {
        var clients = data.getCentralBank().getClients();
        if (clients.isEmpty()) {
            System.out.println("no registered clients");
        }

        for (var client : clients) {
            System.out.println("---------------------");
            System.out.format("name: %s\n", client.getName());
            System.out.format("surname: %s\n", client.getSurname());
            System.out.format("id: %s\n", client.getId());
        }
    }
}
