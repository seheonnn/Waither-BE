package com.waither.service;

import com.waither.repository.AlarmRepository;
import com.waither.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlarmService {

    private AlarmRepository alarmRepository;

    @Autowired
    public AlarmService(AlarmRepository alarmRepository) { this.alarmRepository = alarmRepository; }
}
