package ru.otus.persistence.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.otus.persistence.data.Address;

public interface AddressRepository extends ListCrudRepository<Address, Long> {}
