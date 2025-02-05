package ru.otus.atm.exceptions;

public class ATMIllegalArgumentException extends RuntimeException {

    private static final String MESSAGE = "Сумма для снятия должны быть кратна 100 рублям.";

    public ATMIllegalArgumentException() {
        super(MESSAGE);
    }
}
