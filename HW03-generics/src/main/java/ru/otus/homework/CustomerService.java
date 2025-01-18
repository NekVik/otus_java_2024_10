package ru.otus.homework;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    private final NavigableMap<Customer, String> map = new TreeMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        return Map.entry(
                new Customer(map.firstEntry().getKey()), map.firstEntry().getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        var higherEntry = map.higherEntry(customer);
        return higherEntry != null ? Map.entry(new Customer(higherEntry.getKey()), higherEntry.getValue()) : null;
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }
}
