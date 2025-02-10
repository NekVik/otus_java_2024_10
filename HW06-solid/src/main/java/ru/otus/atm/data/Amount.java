package ru.otus.atm.data;

import java.util.UUID;

public class Amount {

    private final UUID id;
    private int balance;

    public Amount(int value) {
        this.id = UUID.randomUUID();
        this.balance = value;
    }

    public Amount(UUID id, int value) {
        this.id = id;
        this.balance = value;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public UUID getId() {
        return id;
    }

}
