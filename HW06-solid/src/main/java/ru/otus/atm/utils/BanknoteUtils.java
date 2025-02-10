package ru.otus.atm.utils;

import java.util.List;
import ru.otus.atm.data.BanknoteCell;

public final class BanknoteUtils {

    private BanknoteUtils() {
    }

    public static int getBanknoteSum(List<BanknoteCell> banknoteCells) {
        return banknoteCells.stream().mapToInt(it -> it.getCount() * it.getNominal().getMultiplier()).sum();
    }
}
