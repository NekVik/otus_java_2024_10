package ru.otus.atm.service;

import java.util.List;
import ru.otus.atm.data.BanknoteCell;

public interface OperationService {

    void addSum(List<BanknoteCell> banknoteCells);

    List<BanknoteCell> withDraw(int sum);

}
