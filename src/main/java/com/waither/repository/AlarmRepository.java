package com.waither.repository;

import com.waither.entities.AlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlarmRepository extends JpaRepository<AlarmEntity, Long> {

    Optional<AlarmEntity> findByAlarmIdx(Long alarmIdx);

    List<AlarmEntity> findAllByUserId(Long userIdx);
}
