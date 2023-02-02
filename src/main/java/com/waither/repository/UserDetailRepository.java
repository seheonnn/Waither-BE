package com.waither.repository;

import com.waither.entities.UserDetailEntity;
import com.waither.mapping.MainDataMapping;
import com.waither.mapping.UserAlarmMapping;
import com.waither.mapping.UserDataMapping;
import com.waither.mapping.WindAlarmMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Long> {

    @Query(value = "select u.Mon as mon, u.Tue as tue, u.Wed as wed, u.Thu as thu, u.Fri as fri, u.Sat as sat, u.Sun as sun, u.outAlarm as outAlarm, u.climateAlarm as climateAlarm, u.customAlarm as customAlarm, u.rainAlarm as rainAlarm, u.snowAlarm as snowAlarm from UserDetail u where u.userIdx = :userIdx")
    Optional<UserAlarmMapping> findUserAlarm(@Param("userIdx") Long userIdx);

    @Query(value = "select u.rainFall as rainFall, u.dust as dust, u.wind as wind from UserDetail u where u.userIdx = :userIdx")
    Optional<MainDataMapping> findMainData(@Param("userIdx") Long userIdx);

    @Query(value = "select u.windAlarm as windAlarm, u.windValue as windValue from UserDetail u where u.userIdx = :userIdx")
    Optional<WindAlarmMapping> findWindAlarm(@Param("userIdx") Long userIdx);
}