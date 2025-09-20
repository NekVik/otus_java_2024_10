package ru.otus.dto;

public class ClientRequest {

    private final int firstValue;
    private final int lastValue;

    public ClientRequest(int firstValue, int lastValue) {
        this.firstValue = firstValue;
        this.lastValue = lastValue;
    }

    public int getFirstValue() {
        return firstValue;
    }

    public int getLastValue() {
        return lastValue;
    }
}
