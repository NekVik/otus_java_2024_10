package ru.otus.atm.service.impl;

import java.util.Collections;
import java.util.List;
import ru.otus.atm.data.BanknoteCell;
import ru.otus.atm.service.OperationService;

public class OperationServiceImpl implements OperationService {

    private final AmountOperationService amountOperationService;
    private final ATMOperationService atmOperationService;

    public OperationServiceImpl(
            AmountOperationService amountOperationService, ATMOperationService atmOperationService) {
        this.amountOperationService = amountOperationService;
        this.atmOperationService = atmOperationService;
    }

    @Override
    public void addSum(List<BanknoteCell> banknoteCells) {
        amountOperationService.addSum(banknoteCells);
        atmOperationService.addBanknotes(banknoteCells);
    }

    @Override
    public List<BanknoteCell> withDraw(int sum) {

        if (!atmOperationService.canWithDraw(sum)) {
            return Collections.emptyList();
        }

        amountOperationService.withDraw(sum);
        return atmOperationService.getBanknotes(sum);
    }
}
