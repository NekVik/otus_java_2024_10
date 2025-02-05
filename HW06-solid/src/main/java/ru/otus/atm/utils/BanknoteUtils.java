package ru.otus.atm.utils;

import java.util.List;
import ru.otus.atm.data.Banknote;

public final class BanknoteUtils {

    private BanknoteUtils() {
    }

    public static int getBanknoteSum(List<Banknote> banknotes) {
        return banknotes.stream().mapToInt(it -> it.getCount() * it.getNominal().getMultiplier()).sum();
    }
}
