package ru.otus.atm.data;

import java.util.stream.IntStream;

public class BanknoteCell {

    private final Nominal nominal;
    private int count;

    public BanknoteCell(Nominal nominal) {
        this.nominal = nominal;
    }

    public BanknoteCell(Nominal nominal, int count) {
        this.nominal = nominal;
        this.count = count;
    }

    public Nominal getNominal() {
        return nominal;
    }

    public int getCount() {
        return count;
    }

    public void add(int count) {
        this.count += count;
    }

    public int getCountInSum (int sum) {
        return IntStream.range(1, count + 1)
                .filter(i -> i * nominal.getMultiplier() <= sum)
                .max()
                .orElse(0);
    }
}
