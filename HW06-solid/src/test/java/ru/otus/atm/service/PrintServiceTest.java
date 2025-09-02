package ru.otus.atm.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.atm.data.Amount;
import ru.otus.atm.service.impl.AmountOperationService;
import ru.otus.atm.service.impl.PrintServiceImpl;

class PrintServiceTest {

    private PrintService printService;

    @BeforeEach
    void setUp() {
        var amountOperationService = new AmountOperationService(new Amount(3000));
        printService = new PrintServiceImpl(amountOperationService);
    }

    @Test
    void print() {
        printService.print();
        assertTrue(true);
    }
}
