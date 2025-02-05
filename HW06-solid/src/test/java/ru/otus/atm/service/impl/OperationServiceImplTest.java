package ru.otus.atm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.atm.ATMHelper;
import ru.otus.atm.data.Amount;
import ru.otus.atm.data.Banknote;
import ru.otus.atm.data.Nominal;
import ru.otus.atm.exceptions.ATMIllegalArgumentException;
import ru.otus.atm.exceptions.ATMNotEnoughException;
import ru.otus.atm.service.OperationService;

class OperationServiceImplTest {

    AmountOperationService amountOperationService;
    ATMOperationService atmOperationService;

    OperationService serviceForTest;

    @BeforeEach
    void setUp() {

        amountOperationService = new AmountOperationService(new Amount(3000));
        atmOperationService = new ATMOperationService(ATMHelper.initATM());

        serviceForTest = new OperationServiceImpl(amountOperationService, atmOperationService);
    }

    @Test
    void addSum() {
        var banknotes = List.of(
            new Banknote(Nominal.NOMINAL_500, 1),
            new Banknote(Nominal.NOMINAL_100, 4)
        );
        serviceForTest.addSum(banknotes);

        assertEquals(3900, amountOperationService.getBalance());
        assertEquals(4, atmOperationService.getCountByNominal(Nominal.NOMINAL_500));
        assertEquals(11, atmOperationService.getCountByNominal(Nominal.NOMINAL_100));

        serviceForTest.withDraw(3900);
        assertEquals(0, amountOperationService.getBalance());
        assertEquals(4, atmOperationService.getCountByNominal(Nominal.NOMINAL_500));
    }

    @Test
    void withDraw() {
        var banknotes = serviceForTest.withDraw(1900);
        assertEquals(1100, amountOperationService.getBalance());
    }

    @Test
    void withDrawError() {
        assertThrows(ATMIllegalArgumentException.class, () -> serviceForTest.withDraw(1110));
    }

    @Test
    void withDrawATMError() {
        assertThrows(ATMNotEnoughException.class, () -> serviceForTest.withDraw(15000));
    }

}