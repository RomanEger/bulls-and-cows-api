package org.example.bullsandcowsapi.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.repository.CrudRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class userRepository<User> implements CrudRepository<org.example.bullsandcowsapi.entity.User, UUID> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public <S extends org.example.bullsandcowsapi.entity.User> S save(S entity) {
        return null;
    }

    @Override
    public <S extends org.example.bullsandcowsapi.entity.User> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<org.example.bullsandcowsapi.entity.User> findById(UUID uuid) {
        return (Optional<org.example.bullsandcowsapi.entity.User>) em.createQuery("select top 1 * from userTable where id=?1").getSingleResult();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return !Objects.equals(findById(uuid), Optional.empty());
    }

    @Override
    public Iterable<org.example.bullsandcowsapi.entity.User> findAll() {
        return em.createQuery("select * from userTable").getResultList();
    }

    @Override
    public Iterable<org.example.bullsandcowsapi.entity.User> findAllById(Iterable<UUID> uuids) {
        throw new NotImplementedException();
    }

    @Override
    public long count() {
        return (long)em.createQuery("select count(*) from userTable").getSingleResult();
    }

    @Override
    public void deleteById(UUID uuid) {
        em.createQuery("delete from userTable where id=?1");
    }

    @Override
    public void delete(org.example.bullsandcowsapi.entity.User entity) {
        deleteById(entity.id);
    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {
        for(UUID id : uuids){
            deleteById(id);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends org.example.bullsandcowsapi.entity.User> entities) {
        for(org.example.bullsandcowsapi.entity.User user : entities){
            deleteById(user.id);
        }
    }

    @Override
    public void deleteAll() {
        em.createQuery("delete from userTable");
    }
}
