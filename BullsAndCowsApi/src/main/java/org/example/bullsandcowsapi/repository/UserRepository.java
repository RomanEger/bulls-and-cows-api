package org.example.bullsandcowsapi.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.bullsandcowsapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository implements UserCrudRepository {

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public UserRepository(EntityManager em){
        this.em = em;
    }

    @Modifying(clearAutomatically = true)
    @Transactional
    @Override
    public void update(User entity){
        var sql = "";
        var query = em.createNativeQuery(sql);
        query.setParameter(1, entity.id);
        query.setParameter(2, entity.login);
        query.setParameter(3, entity.password);

        query.executeUpdate();
    }

    @Transactional
    @Override
    public void create(User entity) {
        em.persist(entity);
    }

    @Override
    public User findByLogin(String login) {
        var typedQuery = em.createQuery("select u from user u where u.login = ?1", User.class);
        typedQuery.setParameter(1, login);
        return typedQuery.getSingleResult();
    }

    @Override
    public boolean existsByLogin(String login) {
        return findByLogin(login) != null;
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        String SQL = "select * from users u where u.login = ?1 and u.password = ?2";
        var query = em.createNativeQuery(SQL, User.class);
        query.setParameter(1, login);
        query.setParameter(2, password);
        return (User) query.getSingleResult();
    }

    @Override
    public User findById(UUID uuid) {
        var query =  em.createQuery("select u from users u where id=?1", User.class);
        query.setParameter(1, uuid);
        return query.getSingleResult();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return findById(uuid) != null;
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select u from users u", User.class).getResultList();
    }

    @Override
    public long count() {
        return (long)em.createNativeQuery("select count(*) from users").getSingleResult();
    }

    public void deleteById(UUID uuid) {
        var query = em.createQuery("delete from users u where u.id = ?1");
        query.setParameter(1, uuid);
        query.executeUpdate();
    }

    @Override
    public void delete(User entity) {
        deleteById(entity.id);
    }

}
