package org.example.bullsandcowsapi.repository;

import org.example.bullsandcowsapi.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserCrudRepository {
    void create(User user);

    void update(User user);

    void delete(User user);

    List<User> findAll();

    User findById(UUID id);

    User findByLogin(String login);

    User findByLoginAndPassword(String login, String password);

    boolean existsByLogin(String login);

    boolean existsById(UUID id);

    public long count();
}
