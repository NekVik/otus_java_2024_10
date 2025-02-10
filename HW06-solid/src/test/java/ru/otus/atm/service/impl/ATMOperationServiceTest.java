package ru.otus.atm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.atm.ATMHelper;
import ru.otus.atm.data.BanknoteCell;
import ru.otus.atm.data.Nominal;
import ru.otus.atm.exceptions.ATMIllegalArgumentException;
import ru.otus.atm.exceptions.ATMNotEnoughException;
import ru.otus.atm.utils.BanknoteUtils;

class ATMOperationServiceTest {

    ATMOperationService service;

    @BeforeEach
    void setUp() {
        service = new ATMOperationService(ATMHelper.initATM());
    }

    @Test
    void addBanknotes() {
        service.addBanknotes(List.of(
            new BanknoteCell(Nominal.NOMINAL_5000, 1),
            new BanknoteCell(Nominal.NOMINAL_100, 2)
        ));
        var countBy5000 = service.getCountByNominal(Nominal.NOMINAL_5000);
        assertEquals(1, countBy5000);
        var countBy100 = service.getCountByNominal(Nominal.NOMINAL_100);
        assertEquals(9, countBy100);
    }

    @Test
    void getBanknotes() {
        var banknotes = service.getBanknotes(3100);
        assertEquals(4, banknotes.size());
        assertEquals(Nominal.NOMINAL_500, banknotes.get(1).getNominal());
        assertEquals(3100, BanknoteUtils.getBanknoteSum(banknotes));
    }

    @Test
    void canWithDraw() {
        assertTrue(service.canWithDraw(1700));
    }

    @Test
    void canWithDrawIllegalArgumentError() {
        assertThrows(ATMIllegalArgumentException.class, () -> service.canWithDraw(1555));
    }

    @Test
    void canWithDrawError() {
        assertThrows(ATMNotEnoughException.class, () -> service.canWithDraw(15000));
    }

}