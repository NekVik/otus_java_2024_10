package ru.otus.persistence.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.otus.persistence.data.Client;

public interface ClientRepository extends ListCrudRepository<Client, Long> {}
