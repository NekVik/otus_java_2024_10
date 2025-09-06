package ru.otus.persistence.repository;

import java.util.List;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.persistence.data.Phone;

public interface PhoneRepository extends ListCrudRepository<Phone, Long> {

    @Query("select * from phone where client_id = :clientId")
    List<Phone> findByClientId(@Param("clientId") Long clientId);

    @Modifying
    @Query("delete from phone where client_id = :clientId")
    void deleteAllByClientId(@Param("clientId") Long clientId);
}
