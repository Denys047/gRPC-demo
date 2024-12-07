package com.example.repository;

import com.example.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {

    private static final Map<Long, User> db = new HashMap<>();

    static {
        db.put(1L, new User(1L, "Den1", "email1", "password1"));
        db.put(2L, new User(2L, "Den2", "email2", "password2"));
        db.put(3L, new User(3L, "Den3", "email3", "password3"));
        db.put(4L, new User(4L, "Den4", "email4", "password4"));
    }

    public User getUser(long id) {
        if (db.containsKey(id)) {
            return db.get(id);
        }
        throw new RuntimeException("user not found");
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(db.values());
    }

}
