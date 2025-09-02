package ru.otus.atm.service.impl;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.atm.data.Amount;
import ru.otus.atm.data.BanknoteCell;
import ru.otus.atm.data.Nominal;
import ru.otus.atm.exceptions.AmountNotEnoughException;

class AmountOperationServiceTest {

    AmountOperationService service;

    @BeforeEach
    void setUp() {
        service = new AmountOperationService(new Amount(3000));
    }

    @Test
    void correctAddSum() {
        Assertions.assertEquals(3000, service.getBalance());

        var banknotes = List.of(new BanknoteCell(Nominal.NOMINAL_100, 2));

        service.addSum(banknotes);
        Assertions.assertEquals(3200, service.getBalance());

        var banknotes2 = List.of(new BanknoteCell(Nominal.NOMINAL_2000, 1));
        service.addSum(banknotes2);
        Assertions.assertEquals(5200, service.getBalance());
    }

    @Test
    void withDraw() {
        Assertions.assertEquals(3000, service.getBalance());
        service.withDraw(1000);
        Assertions.assertEquals(2000, service.getBalance());
    }

    @Test
    void throwAmountNotEnoughException() {
        Assertions.assertEquals(3000, service.getBalance());
        Assertions.assertThrows(AmountNotEnoughException.class, () -> service.withDraw(4000));
    }
}
