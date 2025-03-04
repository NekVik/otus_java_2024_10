package ru.otus.atm.service.impl;

import java.util.List;
import ru.otus.atm.data.Amount;
import ru.otus.atm.data.BanknoteCell;
import ru.otus.atm.exceptions.AmountNotEnoughException;
import ru.otus.atm.utils.BanknoteUtils;

public class AmountOperationService {

    private final Amount amount;

    public AmountOperationService(Amount amount) {
        this.amount = amount;
    }

    public void addSum(List<BanknoteCell> banknoteCells) {
        var sum = BanknoteUtils.getBanknoteSum(banknoteCells);
        amount.setBalance(amount.getBalance() + sum);
    }

    public void withDraw(int sum) {

        if (sum > amount.getBalance()) {
            throw new AmountNotEnoughException();
        }

        var newBalance = amount.getBalance() - sum;
        amount.setBalance(newBalance);
    }

    public int getBalance() {
        return amount.getBalance();
    }

}
