package ru.otus.atm.data;

import java.util.Objects;
import java.util.UUID;

public class Amount {

    private final UUID id;
    private int balance;

    public Amount(int value) {
        this.id = UUID.randomUUID();
        this.balance = value;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Amount amount = (Amount) o;
        return Objects.equals(id, amount.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
