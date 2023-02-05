package com.waither.repository;

import com.waither.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserByAuthId(String id);
    UserEntity findUserByEmail(String email);
}
