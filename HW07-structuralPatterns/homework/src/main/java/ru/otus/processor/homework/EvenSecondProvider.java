package ru.otus.processor.homework;

import java.time.LocalDateTime;

public interface EvenSecondProvider {

    default boolean isEvenSecond() {
        return LocalDateTime.now().getSecond() % 2 == 0;
    }
}
