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

//    @Query(value = "select u.veryHot as veryHot, u.hot as hot, u.good as good, u.cold as cold, u.veryCold as veryCold from UserDetail u where u.userIdx = :userIdx")
//    Optional<UserDataMapping> findUserData(@Param("userIdx") Long userIdx);

    @Query(value = "select u.Mon as Mon, u.Tue as Tue, u.Wed as Wed, u.Thu as Thu, u.Fri as Fri, u.Sat as Sat, u.Sun as Sun, u.outAlarm as outAlarm, u.climateAlarm as climateAlarm, u.customAlarm as customAlarm, u.rainAlarm as rainAlarm, u.snowAlarm as snowAlarm from UserDetail u where u.userIdx = :userIdx")
    Optional<UserAlarmMapping> findUserAlarm(@Param("userIdx") Long userIdx);

    @Query(value = "select rainFall, dust, wind from UserDetail where userIdx = :userIdx")
    Optional<MainDataMapping> findMainData(@Param("userIdx") Long userIdx);

    @Query(value = "select u.windAlarm as windAlarm, u.windValue as windValue from UserDetail u where u.userIdx = :userIdx")
    Optional<WindAlarmMapping> findWindAlarm(@Param("userIdx") Long userIdx);
}