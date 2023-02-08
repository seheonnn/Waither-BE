package com.waither.service;

import com.waither.config.BaseException;
import com.waither.entities.AlarmEntity;
import com.waither.entities.UserDetailEntity;
import com.waither.entities.UserEntity;
import com.waither.openapi.model.GetWeatherRes;
import com.waither.repository.AlarmRepository;
import com.waither.repository.UserDetailRepository;
import com.waither.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.ServerError;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AlarmService {

    private AlarmRepository alarmRepository;

    private UserRepository userRepository;
    private UserDetailRepository userDetailRepository;

    @Autowired
    public AlarmService(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    //8 알람 생성                                   현재 기온
    public String createTempAlarm(Long userIdx, double temp) {

        UserEntity u = userRepository.findById(userIdx).get();
        UserDetailEntity user = userDetailRepository.findById(userIdx).get();

        double min = 9999;
        double[] arr = { Math.abs(user.getVeryHot()-temp), Math.abs(user.getHot()-temp), Math.abs(user.getGood()-temp), Math.abs(user.getCold()-temp), Math.abs(user.getVeryCold()-temp) };

        if (user.getCustomAlarm() == 'Y') {

            AlarmEntity newAlarm = new AlarmEntity();
            newAlarm.setUserIdx(userIdx);

            for (double i : arr) {
                min = Math.min(min, i);
            }

            if (min == Math.abs(user.getVeryHot() - temp))
                newAlarm.setContents("오늘은" + u.getUserName() + "님이 설정하신 매우 더움 온도에 가까운 " + temp + "도예요");

            else if (min == Math.abs(user.getHot() - temp))
                newAlarm.setContents("오늘은" + u.getUserName() + "님이 설정하신 더움 온도에 가까운 " + temp + "도예요");

            else if (min == Math.abs(user.getGood() - temp)) // *************
                newAlarm.setContents("오늘은" + u.getUserName() + "님이 설정하신 좋음 온도에 가까운 " + temp + "도예요");

            else if (min == Math.abs(user.getCold() - temp))
                newAlarm.setContents("오늘은" + u.getUserName() + "님이 설정하신 추움 온도에 가까운 " + temp + "도예요");

            else if (min == Math.abs(user.getVeryCold() - temp))
                newAlarm.setContents("오늘은" + u.getUserName() + "님이 설정하신 매우 추움 온도에 가까운 " + temp + "도예요");

            newAlarm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            this.alarmRepository.saveAndFlush(newAlarm);
            return newAlarm.getContents();
        }
        return null;
    }

    public String createOutAlarm(Long userIdx, GetWeatherRes getWeatherRes) {
        UserDetailEntity user = userDetailRepository.findById(userIdx).get();

        LocalTime lt = LocalTime.now();
        LocalDate ld = LocalDate.now();

        boolean val = false;
        String day = "";
        switch (ld.getDayOfWeek().getValue()) {
            case 1:
                if (user.getMon() == 'Y') {
                    val = true;
                    day = "월요일 ";
                }

            case 2:
                if (user.getTue() == 'Y') {
                    val = true;
                    day = "화요일 ";
                }
            case 3:
                if (user.getWed() == 'Y') {
                    val = true;
                    day = "수요일 ";
                }
            case 4:
                if (user.getThu() == 'Y') {
                    val = true;
                    day = "목요일 ";
                }
            case 5:
                if (user.getFri() == 'Y') {
                    val = true;
                    day = "금요일 ";
                }
            case 6:
                if (user.getSat() == 'Y') {
                    val = true;
                    day = "토요일 ";
                }
            case 7:
                if (user.getSun() == 'Y') {
                    val = true;
                    day = "일요일 ";
                }
        }
        if (val == true) {
            AlarmEntity newAlarm = new AlarmEntity();
            newAlarm.setUserIdx(userIdx);
            String content = day + "오늘 현재 기온은 " + getWeatherRes.getTmp() + "도, 최저 기온은 " + getWeatherRes.getTmn() + "도, 최고 기온은 " + getWeatherRes.getTmx() + "도입니다.";
            newAlarm.setContents(content);
            newAlarm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            this.alarmRepository.saveAndFlush(newAlarm);
            return newAlarm.getContents();
        }
        return null;

//
//        switch (ld.getDayOfWeek().getValue()) {
//            case 1:
//                if (user.getMon() == 'Y') {
//                    if (lt.getHour() == user.getOutTime().toLocalTime().getHour() && lt.getMinute() == user.getOutTime().toLocalTime().getMinute()) {
//                        AlarmEntity newAlarm = new AlarmEntity();
//                        newAlarm.setUserIdx(userIdx);
//                        String content = "월요일 오늘 현재 기온은 " + getWeatherRes.getTmp() + "도, 최저 기온은 " + getWeatherRes.getTmn() + "도, 최고 기온은 " + getWeatherRes.getTmx() + "도입니다.";
//                        newAlarm.setContents(content);
//                        newAlarm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//                        this.alarmRepository.saveAndFlush(newAlarm);
//                        return newAlarm.getContents();
//                    }
//                }
//            case 2:
//                if (user.getTue() == 'Y') {
//                    if (lt.getHour() == user.getOutTime().toLocalTime().getHour() && lt.getMinute() == user.getOutTime().toLocalTime().getMinute()) {
//                        AlarmEntity newAlarm = new AlarmEntity();
//                        newAlarm.setUserIdx(userIdx);
//                        String content = "화요일 오늘 현재 기온은 " + getWeatherRes.getTmp() + "도, 최저 기온은 " + getWeatherRes.getTmn() + "도, 최고 기온은 " + getWeatherRes.getTmx() + "도입니다.";
//                        newAlarm.setContents(content);
//                        newAlarm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//                        this.alarmRepository.saveAndFlush(newAlarm);
//                        return newAlarm.getContents();
//                    }
//                }
//            case 3:
//                if (user.getWed() == 'Y') {
//                    if (lt.getHour() == user.getOutTime().toLocalTime().getHour() && lt.getMinute() == user.getOutTime().toLocalTime().getMinute()) {
//                        AlarmEntity newAlarm = new AlarmEntity();
//                        newAlarm.setUserIdx(userIdx);
//                        String content = "수요일 오늘 현재 기온은 " + getWeatherRes.getTmp() + "도, 최저 기온은 " + getWeatherRes.getTmn() + "도, 최고 기온은 " + getWeatherRes.getTmx() + "도입니다.";
//                        newAlarm.setContents(content);
//                        newAlarm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//                        this.alarmRepository.saveAndFlush(newAlarm);
//                        return newAlarm.getContents();
//                    }
//                }
//            case 4:
//                if (user.getThu() == 'Y') {
//                    if (lt.getHour() == user.getOutTime().toLocalTime().getHour() && lt.getMinute() == user.getOutTime().toLocalTime().getMinute()) {
//                        AlarmEntity newAlarm = new AlarmEntity();
//                        newAlarm.setUserIdx(userIdx);
//                        String content = "목요일 오늘 현재 기온은 " + getWeatherRes.getTmp() + "도, 최저 기온은 " + getWeatherRes.getTmn() + "도, 최고 기온은 " + getWeatherRes.getTmx() + "도입니다.";
//                        newAlarm.setContents(content);
//                        newAlarm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//                        this.alarmRepository.saveAndFlush(newAlarm);
//                        return newAlarm.getContents();
//                    }
//                }
//            case 5:
//                if (user.getFri() == 'Y') {
//                    if (lt.getHour() == user.getOutTime().toLocalTime().getHour() && lt.getMinute() == user.getOutTime().toLocalTime().getMinute()) {
//                        AlarmEntity newAlarm = new AlarmEntity();
//                        newAlarm.setUserIdx(userIdx);
//                        String content = "금요일 오늘 현재 기온은 " + getWeatherRes.getTmp() + "도, 최저 기온은 " + getWeatherRes.getTmn() + "도, 최고 기온은 " + getWeatherRes.getTmx() + "도입니다.";
//                        newAlarm.setContents(content);
//                        newAlarm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//                        this.alarmRepository.saveAndFlush(newAlarm);
//                        return newAlarm.getContents();
//                    }
//                }
//            case 6:
//                if (user.getSat() == 'Y') {
//                    if (lt.getHour() == user.getOutTime().toLocalTime().getHour() && lt.getMinute() == user.getOutTime().toLocalTime().getMinute()) {
//                        AlarmEntity newAlarm = new AlarmEntity();
//                        newAlarm.setUserIdx(userIdx);
//                        String content = "토요일 오늘 현재 기온은 " + getWeatherRes.getTmp() + "도, 최저 기온은 " + getWeatherRes.getTmn() + "도, 최고 기온은 " + getWeatherRes.getTmx() + "도입니다.";
//                        newAlarm.setContents(content);
//                        newAlarm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//                        this.alarmRepository.saveAndFlush(newAlarm);
//                        return newAlarm.getContents();
//                    }
//                }
//            case 7:
//                if (user.getSun() == 'Y') {
//                    if (lt.getHour() == user.getOutTime().toLocalTime().getHour() && lt.getMinute() == user.getOutTime().toLocalTime().getMinute()) {
//                        AlarmEntity newAlarm = new AlarmEntity();
//                        newAlarm.setUserIdx(userIdx);
//                        String content = "일요일 오늘 현재 기온은 " + getWeatherRes.getTmp() + "도, 최저 기온은 " + getWeatherRes.getTmn() + "도, 최고 기온은 " + getWeatherRes.getTmx() + "도입니다.";
//                        newAlarm.setContents(content);
//                        newAlarm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//                        this.alarmRepository.saveAndFlush(newAlarm);
//                        return newAlarm.getContents();
//                    }
//                }
//        }
//        return null;
    }



    //기상특보 api 확인 후 수정
    public String createClimateAlarm(Long userIdx) {

        UserDetailEntity user = userDetailRepository.findById(userIdx).get();

        if(user.getClimateAlarm() == 'Y') {
            AlarmEntity newAlarm = new AlarmEntity();
            newAlarm.setUserIdx(userIdx);
            //콘텐츠는 api정보 알게되면 추후 디테일하게 수정
            newAlarm.setContents("새벽에 온도가 많이 떨어져요. 창문을 닫고 주무세요.");
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            newAlarm.setCreatedAt(ts);
            this.alarmRepository.saveAndFlush(newAlarm);
            return newAlarm.getContents();
        }
        return null;
    }

    public String createRainAlarm(Long userIdx, String getTime, String rn1, String rn2, String rn3, String rn4, String rn5, String rn6) {

        UserDetailEntity user = userDetailRepository.findById(userIdx).get();

        int time = Integer.parseInt(getTime);

        if(user.getRainAlarm() == 'Y') {
            AlarmEntity newAlarm = new AlarmEntity();

            if(!Objects.equals(rn1, "0")){
                time += 3600;
            }
            else{
                if(!Objects.equals(rn2, "0")){
                    time += 7200;
                }
                else{
                    if(!Objects.equals(rn3, "0")){
                        time += 10800;
                    }
                    else{
                        if(!Objects.equals(rn4, "0")){
                            time += 14400;
                        }
                        else{
                            if(!Objects.equals(rn5, "0")){
                                time += 18000;
                            }
                            else {
                                if(!Objects.equals(rn6, "0")){
                                    time += 21600;
                                }
                            }
                        }
                    }
                }
            }

            int hour = time/(60*60);
            int minute = time/60-(hour*60);

            newAlarm.setContents(hour + ":" + minute + "분부터 소나기가 와요!\n" +
                    "우산을 챙겨가세요.");
            newAlarm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            this.alarmRepository.saveAndFlush(newAlarm);
            return newAlarm.getContents();
        }
        return null;
    }

    //강설 정보 api 없음
    public String createSnowAlarm(Long userIdx) {

        UserDetailEntity user = userDetailRepository.findById(userIdx).get();

        if(user.getSnowAlarm() == 'Y') {
            AlarmEntity newAlarm = new AlarmEntity();
            newAlarm.setUserIdx(userIdx);
            newAlarm.setContents("오늘은 눈이 와요!\n" +
                    "우산을 챙겨가세요.");
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            newAlarm.setCreatedAt(ts);
            this.alarmRepository.saveAndFlush(newAlarm);
            return newAlarm.getContents();
        }
        return null;
    }

    public String createWindAlarm(Long userIdx, double windValue) {

        UserDetailEntity user = userDetailRepository.findById(userIdx).get();

        if(user.getWindAlarm() == 'Y' && user.getWindValue() < windValue) {
            AlarmEntity newAlarm = new AlarmEntity();

            newAlarm.setUserIdx(userIdx);
            newAlarm.setContents("현재 풍속은 " + windValue + "으로 설정하신 풍속보다 높습니다.");
            newAlarm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            this.alarmRepository.saveAndFlush(newAlarm);
            return newAlarm.getContents();
        }
        return null;
    }


    // 9 전체 알람 조회
    public List<AlarmEntity> myAlarmList(Long userIdx) throws BaseException {
        return alarmRepository.findAllByUserIdx(userIdx);
    }

    // 10 알람 삭제
    public boolean delete(Long alarmIdx) throws BaseException {
        try {
            Optional<AlarmEntity> idx = this.alarmRepository.findById(alarmIdx);
            alarmRepository.delete(idx.get());
            return true;
        }catch (Exception exception){
            return false;
        }
    }
}
