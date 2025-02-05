package ru.otus.atm.service;

import java.util.List;
import ru.otus.atm.data.Banknote;

public interface OperationService {

    void addSum(List<Banknote> banknotes);

    List<Banknote> withDraw(int sum);

}
