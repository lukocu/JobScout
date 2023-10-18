package com.jobscout.domain.loginandregister;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryDatabase implements  UserRepository{

    Map<String, User> database=new ConcurrentHashMap<>();



    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(database.get(username));
    }

    @Override
    public User save(User user) {
        UUID uuid= UUID.randomUUID();
        User userWithId=new User(uuid.toString(), user.username(), user.password());

        database.put(user.username(), userWithId);
        return userWithId;
    }
}
