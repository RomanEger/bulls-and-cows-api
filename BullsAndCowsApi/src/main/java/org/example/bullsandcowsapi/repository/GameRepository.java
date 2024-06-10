package org.example.bullsandcowsapi.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.bullsandcowsapi.dto.AttemptDto;
import org.example.bullsandcowsapi.dto.GameDto;
import org.example.bullsandcowsapi.entity.Attempt;
import org.example.bullsandcowsapi.entity.Game;
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
        var nativeQuery = em.createNativeQuery(sql, UUID.class);
        var uuid = (UUID) nativeQuery.getSingleResult();
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
    public List<Game> findAll(){
        var sql = "select * from games";
        var query = em.createNativeQuery(sql, GameDto.class);
        var list = (List<GameDto>) query.getResultList();
        var resultList = new ArrayList<Game>();
        for(var item : list){
            var game = new Game();
            game.session = asUuid(item.session());
            game.rule = item.rule();
            game.id = item.id();
            game.number = item.number();
            game.userSession = asUuid(item.userSession());
            resultList.add(game);
        }
        return resultList;
    }

    @Override
    public Game findById(UUID id) {
        var sql = "select * from games where session=?";
        var query = em.createNativeQuery(sql, GameDto.class);
        query.setParameter(1, id);
        var dto = (GameDto) query.getSingleResult();
        var session = asUuid(dto.session());
        var userSession = asUuid(dto.userSession());
        var game = new Game();
        game.id = dto.id();
        game.session = session;
        game.number = dto.number();
        game.rule = dto.rule();
        game.userSession = userSession;
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

    @Override
    public void delete(int id){
        var sql = "delete * from games where games.id=?";
        var query = em.createNativeQuery(sql);
        query.setParameter(1, id);
        query.executeUpdate();
    }
}
