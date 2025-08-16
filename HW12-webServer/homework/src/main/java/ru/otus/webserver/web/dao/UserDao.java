package ru.otus.webserver.web.dao;

import java.util.Optional;
import ru.otus.webserver.web.model.User;

public interface UserDao {

    Optional<User> findById(long id);

    Optional<User> findRandomUser();

    Optional<User> findByLogin(String login);
}
