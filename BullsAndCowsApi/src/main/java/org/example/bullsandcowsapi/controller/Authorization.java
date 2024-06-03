package org.example.bullsandcowsapi.controller;

import org.example.bullsandcowsapi.entity.User;
import org.example.bullsandcowsapi.reponse.BaseResponse;
import org.example.bullsandcowsapi.repository.UserRepository;
import org.example.bullsandcowsapi.request.AuthorizationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class Authorization {

    private final UserRepository repository;

    @Autowired
    public Authorization(UserRepository repository){
        this.repository = repository;
    }

    @PostMapping("/register")
    public BaseResponse Registration(@RequestBody AuthorizationRequestDto requestUser){
        var user = new User();
        user.login = requestUser.login();
        user.password = requestUser.password();
        try{
            var a = repository.findByLoginAndPassword(user.login, user.password);
            repository.save(user);
        }
        catch (Exception ex){
            var msg = ex.getMessage();
            System.out.println(msg);
            /*
            org.hibernate.query.SyntaxException: At 1:11 and token '1', mismatched input '1', expecting one of the following tokens: <EOF>, ',', '.', '[', '+', '-', '*', '/', '%', '||', ID, VERSION, VERSIONED, NATURALID, FK, ALL, AND, ANY, AS, ASC, AVG, BETWEEN, BOTH, BREADTH, BY, CASE, CAST, COLLATE, COLUMN, CONFLICT, CONSTRAINT, COUNT, CROSS, CUBE, CURRENT, CURRENT_DATE, CURRENT_INSTANT, CURRENT_TIME, CURRENT_TIMESTAMP, CYCLE, DATE, DATETIME, DAY, DEFAULT, DELETE, DEPTH, DESC, DISTINCT, DO, ELEMENT, ELEMENTS, ELSE, EMPTY, END, ENTRY, EPOCH, ERROR, ESCAPE, EVERY, EXCEPT, EXCLUDE, EXISTS, EXTRACT, FETCH, FILTER, FIRST, FOLLOWING, FOR, FORMAT, FROM, FUNCTION, GROUP, GROUPS, HAVING, HOUR, IGNORE, ILIKE, IN, INDEX, INDICES, INSERT, INSTANT, INTERSECT, INTO, IS, JOIN, KEY, KEYS, LAST, LATERAL, LEADING, LIKE, LIMIT, LIST, LISTAGG, LOCAL, LOCAL_DATE, LOCAL_DATETIME, LOCAL_TIME, MAP, MATERIALIZED, MAX, MAXELEMENT, MAXINDEX, MEMBER, MICROSECOND, MILLISECOND, MIN, MINELEMENT, MININDEX, MINUTE, MONTH, NANOSECOND, NEW, NEXT, NO, NOT, NOTHING, NULLS, OBJECT, OF, OFFSET, OFFSET_DATETIME, ON, ONLY, OR, ORDER, OTHERS, OVER, OVERFLOW, OVERLAY, PAD, PARTITION, PERCENT, PLACING, POSITION, PRECEDING, QUARTER, RANGE, RESPECT, ROLLUP, ROW, ROWS, SEARCH, SECOND, SELECT, SET, SIZE, SOME, SUBSTRING, SUM, THEN, TIES, TIME, TIMESTAMP, TIMEZONE_HOUR, TIMEZONE_MINUTE, TO, TRAILING, TREAT, TRIM, TRUNC, TRUNCATE, TYPE, UNBOUNDED, UNION, UPDATE, USING, VALUE, VALUES, WEEK, WHEN, WHERE, WITH, WITHIN, WITHOUT, YEAR, ZONED, IDENTIFIER, QUOTED_IDENTIFIER [select top 1 * from userTable where login=?1 and password=?1]
            */
            var stack = ex.getStackTrace();
        }

        return new BaseResponse("OK", null);
    }

    @PostMapping("/login")
    public BaseResponse Login(@RequestBody AuthorizationRequestDto user){
        /*
        //query to db
        var querySelectByLogin = "select * from Users where login=user.login"
        var result = ...(querySelectByLogin);
        if(result == null){
            return new BaseResponse("FAIL", "неверный логин");
        }

        var querySelectByLoginAndPassword = "select * from Users where login=user.login and password=user.password
        var result = ...(querySelectByLoginAndPassword);
        if(result == null){
            return new BaseResponse("FAIL", "неверный пароль");
        }
        */
        var result = repository.findByLoginAndPassword(user.login(), user.password());
        if(Objects.equals(result, Optional.empty())){
            return new BaseResponse("FAIL", "ошибка входа");
        }
        return new BaseResponse("OK", null);
    }
}
