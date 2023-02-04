package com.waither.repository;

import com.waither.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserByAuthId(String id);

    Optional<UserEntity> findUserByEmail(String email);
}
