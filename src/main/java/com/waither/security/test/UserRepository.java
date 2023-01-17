package com.waither.security.test;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserRepository {

    User[] users;

    public User findUserByAuthId(String id) {
        return users[0];
    }

    public User save(User user){
        users[0] = user;
        log.info(user + "가 저장되었습니다.");
        return user;
    }
}

