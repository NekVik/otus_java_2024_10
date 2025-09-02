package ru.otus.atm;

import java.util.List;
import ru.otus.atm.data.ATM;
import ru.otus.atm.data.BanknoteCell;
import ru.otus.atm.data.Nominal;

public final class ATMHelper {

    private ATMHelper() {}

    public static ATM initATM() {

        var slots = List.of(
                new BanknoteCell(Nominal.NOMINAL_100, 7),
                new BanknoteCell(Nominal.NOMINAL_200, 2),
                new BanknoteCell(Nominal.NOMINAL_500, 3),
                new BanknoteCell(Nominal.NOMINAL_1000, 1));

        return new ATM(slots);
    }
    ;
}
