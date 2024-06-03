package org.example.bullsandcowsapi.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.commons.lang3.NotImplementedException;
import org.example.bullsandcowsapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository implements UserCrudRepository {

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public UserRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public <S extends User> S save(S entity) {
        if(existsById(entity.id))
            return null;
        else
            return (S)em.createQuery("insert into user(login, password) values('"+entity.login+"', '"+entity.password+"');"+
                    "select * from userTable where login='"+entity.login+"';").getSingleResult();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return (Optional<User>) em.createQuery("select top 1 * from userTable where login='?1'").getSingleResult();
    }

    @Override
    public boolean existsByLogin(String login) {
        return !Objects.equals(findByLogin(login), Optional.empty());
    }

    @Override
    public Optional<User> findByPersonalData(User user) {
        return (Optional<User>) em.createQuery("select top 1 * from userTable where login='"+user.login+"' and password='"+user.password+"'").getSingleResult();
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<User> findById(UUID uuid) {
        return (Optional<org.example.bullsandcowsapi.entity.User>) em.createQuery("select top 1 * from userTable where id=?1").getSingleResult();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return !Objects.equals(findById(uuid), Optional.empty());
    }

    @Override
    public Iterable<User> findAll() {
        return em.createQuery("select * from userTable").getResultList();
    }

    @Override
    public Iterable<User> findAllById(Iterable<UUID> uuids) {
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
    public void delete(User entity) {
        deleteById(entity.id);
    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {
        for(UUID id : uuids){
            deleteById(id);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {
        for(org.example.bullsandcowsapi.entity.User user : entities){
            deleteById(user.id);
        }
    }

    @Override
    public void deleteAll() {
        em.createQuery("delete from userTable");
    }
}
