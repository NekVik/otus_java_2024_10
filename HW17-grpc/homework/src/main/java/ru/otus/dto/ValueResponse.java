package ru.otus.dto;

public class ValueResponse {

    private final int newValue;

    public int getNewValue() {
        return newValue;
    }

    public ValueResponse(int newValue) {
        this.newValue = newValue;
    }
}
