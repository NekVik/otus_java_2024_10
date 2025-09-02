package ru.otus;

import com.google.common.base.Joiner;

@SuppressWarnings("java:S106")
public class HelloOtus {

    public static void main(String[] args) {

        Joiner joiner = Joiner.on(" ");

        System.out.println(joiner.join("Hello", "Otus"));
    }
}
