package com.waither.service;

import com.waither.UserData;
import com.waither.entities.UserDetailEntity;
import com.waither.entities.UserEntity;
import com.waither.mapping.MainDataMapping;
import com.waither.mapping.UserAlarmMapping;
import com.waither.mapping.WindAlarmMapping;
import com.waither.repository.UserDetailRepository;
import com.waither.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.thymeleaf.util.ListUtils.size;

//import static org.hibernate.Hibernate.size;

@Service
public class UserService {

    private UserDetailRepository userDetailRepository;

    @Autowired
    public UserService(UserDetailRepository userDetailRepository,
                       UserRepository userRepository) {
        this.userDetailRepository = userDetailRepository;
        this.userRepository = userRepository;
    }

    // 7 설문 답변 저장
    public boolean savedSurvey(Long userIdx, String type, Integer value) {
        UserDetailEntity userData = userDetailRepository.findById(userIdx).get();

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
        Optional<MainDataMapping> mainData = userDetailRepository.findMainData(userIdx);

        return mainData;
    }

    // 12 설정 메인화면 변경
    public boolean updateMainData(Long userIdx, char rainFall, char dust, char wind) {
        Optional<UserDetailEntity> userDetailEntity = userDetailRepository.findById(userIdx);
        if(userDetailEntity.isPresent()) {
            userDetailEntity.get().setRainFall(rainFall);
            userDetailEntity.get().setDust(dust);
            userDetailEntity.get().setWind(wind);
            return true;
        }
        else
            return false;
    }
    // 13 사용자 설정 데이터 조회
    public UserData getUserData(Long userIdx) {
        int sumVC = 0;
        int sumC = 0;
        int sumG = 0;
        int sumH = 0;
        int sumVH = 0;

        Optional<UserDetailEntity> user = userDetailRepository.findById(userIdx);

        UserData ud = new UserData();

        int s = size(userDetailRepository.findAll());
        for (UserDetailEntity userDetailEntity : userDetailRepository.findAll()) {
            sumVC += userDetailEntity.getVeryCold();
            sumC += userDetailEntity.getCold();
            sumG += userDetailEntity.getGood();
            sumH += userDetailEntity.getHot();
            sumVH += userDetailEntity.getVeryHot();
        }

        ud.setVeryCold(user.get().getVeryCold());
        ud.setCold(user.get().getCold());
        ud.setGood(user.get().getGood());
        ud.setHot(user.get().getHot());
        ud.setVeryHot(user.get().getVeryHot());

        ud.setAvgVC(sumVC / s);
        ud.setAvgC(sumC / s);
        ud.setAvgG(sumG / s);
        ud.setAvgH(sumH / s);
        ud.setAvgVH(sumVH / s);


        return ud;
    }

    // 14 사용자 설정 데이터 변경
    public boolean updateUserData(Long userIdx, String type, Integer value) {
        UserDetailEntity userData = userDetailRepository.findById(userIdx).get();
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
        Optional<UserAlarmMapping> userAlarmMapping = userDetailRepository.findUserAlarm(userIdx);
        return userAlarmMapping;
    }

    // 16 알람 설정 변경
    public boolean updateAlarmData(Long userIdx, Character Mon, Character Tue, Character Wed, Character Thu, Character Fri, Character Sat, Character Sun, Character outAlarm, Character climateAlarm, Character customAlarm, Character rainAlarm, Character snowAlarm) {
        UserDetailEntity userData = userDetailRepository.findById(userIdx).get();
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
        Optional<WindAlarmMapping> windAlarmMapping = userDetailRepository.findWindAlarm(userIdx);
        return windAlarmMapping;
    }

    // 18 사용자 바람 세기 설정 변경
    public boolean updateWindAlarm(Long userIdx, Character windAlarm, Integer windValue) {
        UserDetailEntity userData = userDetailRepository.findById(userIdx).get();
        userData.setWindAlarm(windAlarm);
        userData.setWindValue(windValue);
        return true;
    }


    UserEntity[] users;
    private final UserRepository userRepository;

    public UserEntity findUserByAuthId(String id) {
        UserEntity user = userRepository.findUserByAuthId(id);
        return user;
    }

    public UserEntity save(UserEntity user){
        users[0] = user;
//        log.info(user + "가 저장되었습니다.");
        return user;
    }

}
