package ru.otus.webserver.web.model;

public class ClientDto {

    private final long id;
    private final String name;
    private final String address;
    private final String phones;

    public ClientDto(long id, String name, String address, String phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhones() {
        return phones;
    }

}
