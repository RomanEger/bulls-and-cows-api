package org.example.bullsandcowsapi.repository;

import org.example.bullsandcowsapi.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserCrudRepository extends CrudRepository<User, UUID> {
    Optional<User> findByLogin(String login);

    boolean existsByLogin(String login);

    Optional<User> findByPersonalData(User user);
}
