package ru.otus.atm.exceptions;

public class AmountNotEnoughException extends RuntimeException {

    private static final String MESSAGE = "Недостаточно средств на счёте";

    public AmountNotEnoughException() {
        super(MESSAGE);
    }
}
