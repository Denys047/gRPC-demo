package com.example.repository;

import com.example.model.Role;
import com.example.model.User;

import java.util.*;

public class UserRepository {

    private static final Map<Long, User> db = new HashMap<>();

    static {
        db.put(1L,new User(1L,"Den",Role.USER));
        db.put(2L,new User(2L,"Max",Role.USER));
        db.put(3L,new User(3L,"Ivan",Role.USER));
        db.put(4L,new User(4L,"Artem",Role.ADMIN));
        db.put(5L,new User(5L,"Taras",Role.ADMIN));
    }


    public Optional<User> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    public List<User> findAll() {
        return new ArrayList<>(db.values());
    }

}
