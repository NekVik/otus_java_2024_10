package ru.otus.atm.exceptions;

public class ATMNotEnoughException extends RuntimeException {

    private static final String MESSAGE = "В банкомате недостаточно средств";

    public ATMNotEnoughException() {
        super(MESSAGE);
    }
}
