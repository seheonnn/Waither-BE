package com.waither.repository;

import com.waither.entities.UserEntity;
import com.waither.mapping.UserAlarmMapping;
import com.waither.mapping.UserDataMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserId(String userId);

    @Query(value = "select u.veryHot as veryHot, u.hot as hot, u.good as good, u.cold as cold, u.veryCold as veryCold from User u where u.userIdx > ?1")
    Optional<UserDataMapping> findUserData(Long userIdx);

    @Query(value = "select u.Mon as Mon, u.Tue as Tue, u.Wed as Wed, u.Thu as Thu, u.Fri as Fri, u.Sat as Sat, u.Sun as Sun, u.outAlarm as outAlarm, u.climateAlarm as climateAlarm, u.customAlarm as customAlarm, u.rainAlarm as rainAlarm, u.snowAlarm as snowAlarm from User u where u.userIdx > ?1")
    Optional<UserAlarmMapping> findUserAlarm(Long userIdx);
}
