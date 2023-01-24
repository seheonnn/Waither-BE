package com.waither.service;

import com.waither.entities.UserData;
import com.waither.entities.UserEntity;
import com.waither.mapping.MainDataMapping;
import com.waither.mapping.UserAlarmMapping;
import com.waither.mapping.WindAlarmMapping;
import com.waither.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.hibernate.Hibernate.size;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 7 설문 답변 저장
    public boolean savedSurvey(Long userIdx, String type, Integer value) {
        UserEntity userData = userRepository.findById(userIdx).get();
//        value = SensibleTemp.calculateTemp(value, // 풍속, // 습도) // 받은 온도를 체감온도로 변환 -> 체감 온도 삭제
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

    // 11 설정 메인화면 조회
    public Optional<MainDataMapping> getMainData(Long userIdx) {
        Optional<MainDataMapping> mainData = userRepository.findMainData(userIdx);

        return mainData;
    }

    // 12 설정 메인화면 변경
    public boolean updateMainData(Long userIdx, char rainFall, char dust, char wind) {
        Optional<UserEntity> userEntity = userRepository.findById(userIdx);
        if(userEntity.isPresent()) {
            userEntity.get().setRainFall(rainFall);
            userEntity.get().setDust(dust);
            userEntity.get().setWind(wind);
            return true;
        }
        else
            return false;
    }
    // 13 사용자 설정 데이터 조회
    public UserData getUserData(Long userIdx) {
        int sumVH = 0;
        int sumH = 0;
        int sumG = 0;
        int sumC = 0;
        int sumVC = 0;

        Optional<UserEntity> user = userRepository.findById(userIdx);
//        Optional<UserDataMapping> userData = userRepository.findUserData(userIdx);
        UserData ud = new UserData();

        int s = size(userRepository.findAll());
        for (UserEntity userEntity : userRepository.findAll()) {
            sumVH += userEntity.getVeryHot();
            sumH += userEntity.getHot();
            sumG += userEntity.getGood();
            sumC += userEntity.getCold();
            sumVC += userEntity.getVeryCold();
        }
        ud.setVeryHot(user.get().getVeryHot());
        ud.setHot(user.get().getHot());
        ud.setGood(user.get().getGood());
        ud.setCold(user.get().getCold());
        ud.setVeryCold(user.get().getVeryCold());

        ud.setAvgVH(sumVH / s);
        ud.setAvgH(sumH / s);
        ud.setAvgG(sumG / s);
        ud.setAvgC(sumC / s);
        ud.setAvgVC(sumVC / s);


        return ud;
    }

    // 14 사용자 설정 데이터 변경
    public boolean updateUserData(Long userIdx, String type, Integer value) {
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

    // 17 사용자 바람 세기 설정 조회
    public Optional<WindAlarmMapping> getWindAlarm(Long userIdx) {
        Optional<WindAlarmMapping> windAlarmMapping = userRepository.findWindAlarm(userIdx);
        return windAlarmMapping;
    }

    // 18 사용자 바람 세기 설정 변경
    public boolean updateWindAlarm(Long userIdx, Character windAlarm, Integer windValue) {
        UserEntity userData = userRepository.findById(userIdx).get();
        userData.setWindAlarm(windAlarm);
        userData.setWindValue(windValue);
        return true;
    }

}
