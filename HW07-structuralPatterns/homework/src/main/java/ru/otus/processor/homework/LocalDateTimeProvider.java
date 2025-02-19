package ru.otus.processor.homework;

import java.time.LocalDateTime;

public interface LocalDateTimeProvider {

    default LocalDateTime now() {
        return LocalDateTime.now();
    }
}
