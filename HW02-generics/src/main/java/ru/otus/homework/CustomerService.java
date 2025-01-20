package ru.otus.homework;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    private final NavigableMap<Customer, String> map;

    public CustomerService() {
        // не стал делать передачу компаратора в конструктор, чтобы не менять тесты
        map = new TreeMap<>(new CustomerScoreComparator());
    }

    public Map.Entry<Customer, String> getSmallest() {
        var firstEntry = map.firstEntry();
        return firstEntry != null ? Map.entry(new Customer(firstEntry.getKey()), firstEntry.getValue()) : null;
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        var higherEntry = map.higherEntry(customer);
        return higherEntry != null ? Map.entry(new Customer(higherEntry.getKey()), higherEntry.getValue()) : null;
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }
}
