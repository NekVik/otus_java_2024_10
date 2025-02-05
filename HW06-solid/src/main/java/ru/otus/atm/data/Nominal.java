package ru.otus.atm.data;

public enum Nominal {

    NOMINAL_100(100),
    NOMINAL_200(200),
    NOMINAL_500(500),
    NOMINAL_1000(1000),
    NOMINAL_2000(2000),
    NOMINAL_5000(5000);

    private final int multiplier;

    Nominal(int i) {
        this.multiplier = i;
    }

    public int getMultiplier() {
        return multiplier;
    }

}
