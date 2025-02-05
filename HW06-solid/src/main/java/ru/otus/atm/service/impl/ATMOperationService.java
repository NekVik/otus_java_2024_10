package ru.otus.atm.service.impl;

import java.util.List;
import ru.otus.atm.data.ATM;
import ru.otus.atm.data.Banknote;
import ru.otus.atm.data.Nominal;

public class ATMOperationService {

    private final ATM atm;

    public ATMOperationService(ATM atm) {
        this.atm = atm;
    }

    public void addBanknotes (List<Banknote> banknotes){
        banknotes.forEach(banknote -> atm.addBanknote(banknote.getNominal(), banknote.getCount()));
    }

    public List<Banknote> getBanknotes(int sum) {
        return atm.getBanknotes(sum);
    }

    public boolean canWithDraw(int sum) {
        return atm.canWithDraw(sum);
    }

    public int getCountByNominal(Nominal nominal) {
        return atm.getCountByNominal(nominal);
    }

}
