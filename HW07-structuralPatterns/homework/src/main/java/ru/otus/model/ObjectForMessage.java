package ru.otus.model;

import java.util.List;

public class ObjectForMessage {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public ObjectForMessage copy() {

        var copy = new ObjectForMessage();
        if (data != null) {
            copy.setData(List.copyOf(data));
        }

        return copy;
    }
}
