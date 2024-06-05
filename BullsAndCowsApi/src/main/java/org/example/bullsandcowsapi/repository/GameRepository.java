package org.example.bullsandcowsapi.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.bullsandcowsapi.dto.AttemptDto;
import org.example.bullsandcowsapi.dto.GameDto;
import org.example.bullsandcowsapi.entity.Attempt;
import org.example.bullsandcowsapi.entity.Game;
import org.example.bullsandcowsapi.entity.User;
import org.example.bullsandcowsapi.reponse.GameStatusReponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Repository
public class GameRepository implements GameCrudRepository {

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public GameRepository(EntityManager em){
        this.em = em;
    }

    @Transactional
    @Override
    public UUID create(Game game) {
        em.merge(game);
        var sql = "select top 1 session from games order by id desc";
        var nativeQuery = em.createNativeQuery(sql);
        var a = (byte[]) nativeQuery.getSingleResult();
        var uuid = asUuid(a);
        return uuid;
    }

    private static byte[] asBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.allocate(16);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    private static UUID asUuid(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long firstLong = bb.getLong();
        long secondLong = bb.getLong();
        return new UUID(firstLong, secondLong);
    }

    @Override
    public Game findById(UUID id) {
        var sqlAll = "select * from games";
        var queryAll = em.createNativeQuery(sqlAll, GameDto.class);
        var list = queryAll.getResultList();
        var dto = (GameDto)list.get(0);

        var uuid = asUuid(dto.session());
//        var sql = "select * from games g where g.id = ?1";
//        var query = em.createNativeQuery(sql);
//        query.setParameter(1, id);
//        var obj = query.getSingleResult();
//        var dto = (GameDto) obj;
        var game = new Game();
        game.id = dto.id();
        game.session = uuid;
        game.number = dto.number();
        game.rule = dto.rule();
        return game;
    }

    @Override
    public List<AttemptDto> findAttemptsByGameId(UUID gameId) {
        var sql = "select a.number, a.bulls, a.cows from attempts a, games g where g.session = ?1";
        var query = em.createNativeQuery(sql, AttemptDto.class);
        query.setParameter(1, gameId);
        List<AttemptDto> result = (List<AttemptDto>)query.getResultList();
        return result;
    }

    @Transactional
    @Override
    public void addAttempt(Attempt attempt) {
        em.merge(attempt);
    }
}
