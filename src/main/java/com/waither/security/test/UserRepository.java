package com.waither.security.test;

import com.waither.entities.UserEntity;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserRepository {

    UserEntity[] users;

    public UserEntity findUserByAuthId(String id) {
        return users[0];
    }

    public UserEntity save(UserEntity user){
        users[0] = user;
        log.info(user + "가 저장되었습니다.");
        return user;
    }
}

