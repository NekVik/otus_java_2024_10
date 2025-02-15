package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class EvenSecondProcessor implements Processor {

    private final EvenSecondProvider secondProvider;

    public EvenSecondProcessor(EvenSecondProvider secondProvider) {
        this.secondProvider = secondProvider;
    }

    @Override
    public Message process(Message message) {
        if (secondProvider.isEvenSecond()) {
            throw new EvenSecondException();
        }
        return message;
    }
}
