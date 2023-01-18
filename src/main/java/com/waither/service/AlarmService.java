package com.waither.service;

import com.waither.entities.AlarmEntity;
import com.waither.repository.AlarmRepository;
import com.waither.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlarmService {

    private AlarmRepository alarmRepository;

    @Autowired
    public AlarmService(AlarmRepository alarmRepository) { this.alarmRepository = alarmRepository; }


    // 9 전체 알람 조회
    public List<AlarmEntity> myAlarmList(Long userIdx) {
        return alarmRepository.findAllByUserIdx(userIdx);
    }

    // 10 알람 삭제
    public boolean delete(Long alarmIdx) {
        Optional<AlarmEntity> idx = this.alarmRepository.findByAlarmIdx(alarmIdx);
        if(idx.isPresent()) {
            alarmRepository.delete(idx.get());
            return true;
        }
        else {
            return false;
        }

    }
}
