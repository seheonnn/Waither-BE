package com.waither.repository;

import com.waither.entities.AlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface AlarmRepository extends JpaRepository<AlarmEntity, Long> {

    Optional<AlarmEntity> findByAlarmIdx(Long alarmIdx);

    List<AlarmEntity> findAllByUserIdx(Long userIdx);
}
