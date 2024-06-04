package org.example.bullsandcowsapi.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.example.bullsandcowsapi.entity.Attempt;
import org.example.bullsandcowsapi.entity.Game;
import org.example.bullsandcowsapi.entity.User;
import org.example.bullsandcowsapi.reponse.GameStatusReponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class GameRepository implements GameCrudRepository {

    EntityManager em;

    @Autowired
    public GameRepository(EntityManager em){
        this.em = em;
    }

    @Transactional
    @Override
    public void create(Game game) {
        em.persist(game);
    }

    @Override
    public Game findById(UUID id) {
        var sql = "select * from games g where g.id = ?1";
        var query = em.createNativeQuery(sql);
        query.setParameter(1, id);
        return (Game) query.getSingleResult();
    }

    @Override
    public List<Attempt> findAttemptsByGameId(UUID gameId) {
        var sql = "select * from attempts a where a.gameId = ?1";
        var query = em.createNativeQuery(sql);
        query.setParameter(1, gameId);
        return query.getResultList();
    }

    @Override
    public void addAttempt(Attempt attempt) {
        em.persist(attempt);
    }
}
