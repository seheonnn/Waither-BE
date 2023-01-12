package com.waither.service;

import com.waither.dao.UserDAO;
import com.waither.dto.UserDTO;
import com.waither.entities.UserEntity;
import com.waither.mapping.UserAlarmMapping;
import com.waither.mapping.UserDataMapping;
import com.waither.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UserService implements UserDAO {

    private UserRepository userRepository;

    // 암호화
//    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 7 설문 답변 저장
    public boolean savedSurvey(Long userIdx, double veryCold, double cold, double good, double hot, double veryHot, Timestamp outTime) {
        UserEntity userData = userRepository.findById(userIdx).get();
        userData.setVeryCold(veryCold);
        userData.setCold(cold);
        userData.setGood(good);
        userData.setHot(hot);
        userData.setVeryHot(veryHot);
        userData.setOutTime(outTime);
        return true;
    }

    // 13 사용자 설정 데이터 조회
    public Optional<UserDataMapping> getUserData(Long userIdx) {
        Optional<UserDataMapping> userData = userRepository.findUserData(userIdx);
        return userData;
    }

    // 14 사용자 설정 데이터 변경
    public boolean updateUserData(Long userIdx, String type, Double value) {
        UserEntity userData = userRepository.findById(userIdx).get();
        if (type.equals("veryHot")) {
            userData.setVeryHot(value);
        } else if (type.equals("hot")) {
            userData.setHot(value);
        } else if (type.equals("good")) {
            userData.setGood(value);
        } else if (type.equals("cold")) {
            userData.setCold(value);
        } else if (type.equals("veryCold")) {
            userData.setVeryCold(value);
        } else {
            return false;
        }
        return true;
    }

    // 15 사용자 알람 설정 조회
    public Optional<UserAlarmMapping> getAlarmData(Long userIdx) {
        Optional<UserAlarmMapping> userAlarmMapping = userRepository.findUserAlarm(userIdx);
        return userAlarmMapping;
    }

    // 16 알람 설정 변경
    public boolean updateAlarmData(Long userIdx, Character Mon, Character Tue, Character Wed, Character Thu, Character Fri, Character Sat, Character Sun, Character outAlarm, Character climateAlarm, Character customAlarm, Character rainAlarm, Character snowAlarm) {
        UserEntity userData = userRepository.findById(userIdx).get();
        userData.setMon(Mon);
        userData.setTue(Tue);
        userData.setWed(Wed);
        userData.setThu(Thu);
        userData.setFri(Fri);
        userData.setSat(Sat);
        userData.setSun(Sun);
        userData.setOutAlarm(outAlarm);
        userData.setClimateAlarm(climateAlarm);
        userData.setCustomAlarm(customAlarm);
        userData.setRainAlarm(rainAlarm);
        userData.setSnowAlarm(snowAlarm);
        return true;
    }

}
