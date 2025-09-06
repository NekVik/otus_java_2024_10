package ru.otus.service;

import java.util.List;
import java.util.Optional;
import ru.otus.dto.ClientDto;

public interface ClientService {

    void save(ClientDto client);

    Optional<ClientDto> getClient(Long id);

    List<ClientDto> findAll();

    void delete(Long id);
}
