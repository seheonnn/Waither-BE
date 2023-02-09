package com.waither.service;

import com.waither.config.BaseException;
import com.waither.entities.UserEntity;
import com.waither.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class LoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //#1 유저 일반 로그인
    public void userLogin(String email, String pw) throws BaseException {
//        Optional<UserEntity> user = userRepository.findById();
    }
}
