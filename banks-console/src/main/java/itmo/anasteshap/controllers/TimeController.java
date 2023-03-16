package itmo.anasteshap.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        var date = data.getCentralBank().getRewindClock().currentTime();
        System.out.println(date.get(Calendar.DAY_OF_MONTH) + "-" + date.get(Calendar.MONTH) + "-" + date.get(Calendar.YEAR));
        System.out.println("+++ successfully +++");
    }

    public void exit(Void unused) {
        System.exit(0);
    }
}
