package itmo.anasteshap.controllers;

import java.util.Scanner;

public class TimeController {
    private final DataController data;
    private final Scanner scanner = new Scanner(System.in);

    public TimeController(DataController data) {
        this.data = data;
    }

    public void rewindTime(Void unused) {
        System.out.print("enter a countOfDays - ");
        var countOfDays = scanner.nextInt();
        data.getCentralBank().getRewindClock().rewindTime(countOfDays);
        System.out.println("+++ successfully +++");
    }

    public void dateNow(Void unused) {
        System.out.println(data.getCentralBank().getRewindClock().currentTime());
        System.out.println("+++ successfully +++");
    }

    public void exit(Void unused) {
        System.exit(0);
    }
}
