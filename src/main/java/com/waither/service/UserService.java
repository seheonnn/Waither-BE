package com.waither.service;

import com.waither.config.BaseResponseStatus;
import com.waither.model.UserData;
import com.waither.config.BaseException;
import com.waither.entities.UserDetailEntity;
import com.waither.entities.UserEntity;
import com.waither.mapping.MainDataMapping;
import com.waither.mapping.UserAlarmMapping;
import com.waither.mapping.WindAlarmMapping;
import com.waither.model.UserInfo;
import com.waither.repository.UserDetailRepository;
import com.waither.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.thymeleaf.util.ListUtils.size;

//import static org.hibernate.Hibernate.size;

@Log4j2
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
    @Transactional
    public boolean savedSurvey(Long userIdx, String type, Integer value) throws BaseException {
        log.info("type");
        log.info("value");
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
    public Optional<MainDataMapping> getMainData(Long userIdx) throws BaseException {
        Optional<MainDataMapping> mainData = userDetailRepository.findMainData(userIdx);

        return mainData;
    }

    // 12 설정 메인화면 변경
    @Transactional
    public boolean updateMainData(Long userIdx, char rainFall, char dust, char wind) throws BaseException {
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
    public UserData getUserData(Long userIdx) throws BaseException{
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
    @Transactional
    public boolean updateUserData(Long userIdx, String type, Integer value) throws BaseException{
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
    public Optional<UserAlarmMapping> getAlarmData(Long userIdx) throws BaseException {
        Optional<UserAlarmMapping> userAlarmMapping = userDetailRepository.findUserAlarm(userIdx);
        return userAlarmMapping;
    }

    // 16 알람 설정 변경
    @Transactional
    public boolean updateAlarmData(Long userIdx, Character Mon, Character Tue, Character Wed, Character Thu, Character Fri, Character Sat, Character Sun, Character outAlarm, Character climateAlarm, Character customAlarm, Character rainAlarm, Character snowAlarm)  throws  BaseException{
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
    public Optional<WindAlarmMapping> getWindAlarm(Long userIdx)  throws BaseException{
        Optional<WindAlarmMapping> windAlarmMapping = userDetailRepository.findWindAlarm(userIdx);
        return windAlarmMapping;
    }

    // 18 사용자 바람 세기 설정 변경
    @Transactional
    public boolean updateWindAlarm(Long userIdx, Character windAlarm, Integer windValue) throws BaseException{
        UserDetailEntity userData = userDetailRepository.findById(userIdx).get();
        userData.setWindAlarm(windAlarm);
        userData.setWindValue(windValue);
        return true;
    }

    //19 회원정보 조회
    public UserInfo getUserInfo(Long userIdx) throws BaseException{
        UserInfo userInfo = new UserInfo();
        Optional<UserEntity> user = userRepository.findById(userIdx);
        userInfo.setName(user.get().getUserName());
        userInfo.setEmail(user.get().getEmail());
        return userInfo;
    }


    //20 회원이름 변경
    @Transactional
    public boolean updateUserName(Long userIdx, String name) throws BaseException{
        try{
            Optional<UserEntity> user = userRepository.findById(userIdx);
            if(user.isPresent()){
                log.info(user.get().getUserName());
                user.get().changeName(name);
                log.info(user.get().getUserName());
                log.info(name);
                return true;
            }
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.REQUEST_ERROR);
        }
        return false;
    }

    //21 비밀번호 일치 확인
    public boolean pwValidation(Long userIdx, String pw) throws BaseException{
        Optional<UserEntity> user = userRepository.findById(userIdx);
        return user.filter(userEntity -> pw.equals(userEntity.getPw())).isPresent();
    }

    //22 비밀번호 재설정
    @Transactional
    public boolean updatePw(Long userIdx, String pw) throws BaseException{
        Optional<UserEntity> user = userRepository.findById(userIdx);
        user.ifPresent(userEntity -> userEntity.changePw(pw));
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
