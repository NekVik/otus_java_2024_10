package ru.otus.atm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.atm.service.PrintService;

public class PrintServiceImpl implements PrintService {

    private static final Logger logger = LoggerFactory.getLogger(PrintServiceImpl.class);

    private final AmountOperationService service;

    public PrintServiceImpl(AmountOperationService service) {
        this.service = service;
    }

    @Override
    public void print() {
        logger.info("Текущий остаток на счёте: {}", service.getBalance());
    }
}
