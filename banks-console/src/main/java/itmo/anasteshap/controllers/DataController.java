package itmo.anasteshap.controllers;

import itmo.anasteshap.entities.Client;
import itmo.anasteshap.interfaces.CentralBank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class DataController {
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private Client currentClient;
    @Getter
    private final CentralBank centralBank;

    public DataController(@NonNull CentralBank centralBank) {
        this.centralBank = centralBank;
    }

    public void changeCurrentClient(@NonNull Client client) {
        this.currentClient = client;
    }
}
