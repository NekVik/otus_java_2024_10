package ru.otus.atm.data;

import java.util.Objects;
import java.util.stream.IntStream;

public class Banknote {

    private final Nominal nominal;
    private int count;

    public Banknote(Nominal nominal) {
        this.nominal = nominal;
    }

    public Banknote(Nominal nominal, int count) {
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

    public void withDraw(int count) {

        if (count > this.count) {
            throw new IllegalArgumentException("Недостаточно банкнот для снятия");
        }

        this.count -= count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Banknote that = (Banknote) o;
        return nominal == that.nominal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nominal);
    }
}
