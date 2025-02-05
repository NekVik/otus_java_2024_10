package ru.otus.atm.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import ru.otus.atm.exceptions.ATMIllegalArgumentException;
import ru.otus.atm.exceptions.ATMNotEnoughException;
import ru.otus.atm.utils.BanknoteUtils;

public class ATM {

    private final Map<Nominal, Banknote> slots;
    private int balance;

    public ATM(List<Banknote> slots) {

        this.balance = BanknoteUtils.getBanknoteSum(slots);

        this.slots = new TreeMap<>(Comparator.comparingInt(Nominal::getMultiplier).reversed());

        slots.forEach(banknote -> this.slots.put(banknote.getNominal(), banknote));
    }

    public void addBanknote(Nominal nominal, int count) {
        if (slots.containsKey(nominal)) {

            var banknote = slots.get(nominal);
            banknote.add(count);

            slots.put(nominal, banknote);
        } else {
            slots.put(nominal, new Banknote(nominal, count));
        }
        balance += nominal.getMultiplier() * count;
    }

    public boolean canWithDraw(int sum) {

        if (sum > balance) {
            throw new ATMNotEnoughException();
        }

        if (sum % 100 != 0) {
            throw new ATMIllegalArgumentException();
        }

        return true;
    }

    public List<Banknote> getBanknotes(int sum) {

        var result = new ArrayList<Banknote>();
        var resultSum = sum;

        for (var banknote : slots.values()) {

            var countBanknote = banknote.getCountInSum(resultSum);

            if (countBanknote > 0) {
                result.add(new Banknote(banknote.getNominal(), countBanknote));
                resultSum -= banknote.getNominal().getMultiplier() * countBanknote;
            }

            if (resultSum == 0) {
                break;
            }

        }
        balance -= sum;

        return result;
    }

    public int getCountByNominal(Nominal nominal) {
        return slots.get(nominal).getCount();
    }
}
