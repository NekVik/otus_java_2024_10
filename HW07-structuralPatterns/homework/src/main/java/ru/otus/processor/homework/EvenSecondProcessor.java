package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class EvenSecondProcessor implements Processor {

    private final LocalDateTimeProvider secondProvider;

    public EvenSecondProcessor(LocalDateTimeProvider secondProvider) {
        this.secondProvider = secondProvider;
    }

    @Override
    public Message process(Message message) {
        if (secondProvider.now().getSecond() % 2 == 0) {
            throw new EvenSecondException();
        }
        return message;
    }
}
