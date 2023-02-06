package com.waither.service;

import com.waither.config.BaseException;
import com.waither.entities.AlarmEntity;
import com.waither.entities.UserDetailEntity;
import com.waither.entities.UserEntity;
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
        AlarmEntity newAlarm = new AlarmEntity();

        UserEntity u = userRepository.findById(userIdx).get();
        UserDetailEntity user = userDetailRepository.findById(userIdx).get();

        // 30 26  10  0  -10   현재 28
        double min = 9999;
        double[] arr = { Math.abs(user.getVeryHot()-temp), Math.abs(user.getHot()-temp), Math.abs(user.getGood()-temp), Math.abs(user.getCold()-temp), Math.abs(user.getVeryCold()-temp) };

        for (double i : arr) { min = Math.min(min, i); }

        if (min == Math.abs(user.getVeryHot()-temp))
            newAlarm.setContents("오늘은" + u.getUserName()+"님이 설정하신 매우 더움 온도에 가까운 " + temp +"도예요");

        else if (min == Math.abs(user.getHot()-temp))
            newAlarm.setContents("오늘은" + u.getUserName()+"님이 설정하신 더움 온도에 가까운 " + temp +"도예요");

        else if (min == Math.abs(user.getGood()-temp)) // *************
            newAlarm.setContents("오늘은" + u.getUserName()+"님이 설정하신 좋음 온도에 가까운 " + temp +"도예요");

        else if (min == Math.abs(user.getCold()-temp))
            newAlarm.setContents("오늘은" + u.getUserName()+"님이 설정하신 추움 온도에 가까운 " + temp +"도예요");

        else if (min == Math.abs(user.getVeryCold()-temp))
            newAlarm.setContents("오늘은" + u.getUserName()+"님이 설정하신 매우 추움 온도에 가까운 " + temp +"도예요");

        this.alarmRepository.saveAndFlush(newAlarm);
        return newAlarm.getContents();
    }

    public String createOutAlarm(Long userIdx,                 ) {
        AlarmEntity newAlarm = new AlarmEntity();
        UserDetailEntity user = userDetailRepository.findById(userIdx).get();

        LocalTime lt = LocalTime.now();
        LocalDate ld = LocalDate.now();

        switch (ld.getDayOfWeek().getValue()) {
            case 1:
                if (user.getMon() == 'Y') {
                    if (lt.getHour() == user.getOutTime().toLocalTime().getHour() && lt.getMinute() == user.getOutTime().toLocalTime().getMinute()) {
                        newAlarm.setUserIdx(userIdx);
                        String content = "월요일 오늘 날씨는 " + "이쪽에 날씨정보, 온도 등 채워넣을 듯" + "입니다.";
                        newAlarm.setContents(content);
                        newAlarm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                    }
                }
            case 2:
                if (user.getTue() == 'Y') {
                    if (lt.getHour() == user.getOutTime().toLocalTime().getHour() && lt.getMinute() == user.getOutTime().toLocalTime().getMinute()) {
                        newAlarm.setUserIdx(userIdx);
                        String content = "화요일 오늘 날씨는 ";
                        newAlarm.setContents(content);
                        newAlarm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                    }
                }
            case 3:
                if (user.getWed() == 'Y') {
                    if (lt.getHour() == user.getOutTime().toLocalTime().getHour() && lt.getMinute() == user.getOutTime().toLocalTime().getMinute()) {
                        newAlarm.setUserIdx(userIdx);
                        String content = "수요일 오늘 날씨는 ";
                        newAlarm.setContents(content);
                        newAlarm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                    }
                }
            case 4:
                if (user.getThu() == 'Y') {
                    if (lt.getHour() == user.getOutTime().toLocalTime().getHour() && lt.getMinute() == user.getOutTime().toLocalTime().getMinute()) {
                        newAlarm.setUserIdx(userIdx);
                        String content = "목요일 오늘 날씨는 ";
                        newAlarm.setContents(content);
                        newAlarm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                    }
                }
            case 5:
                if (user.getFri() == 'Y') {
                    if (lt.getHour() == user.getOutTime().toLocalTime().getHour() && lt.getMinute() == user.getOutTime().toLocalTime().getMinute()) {
                        newAlarm.setUserIdx(userIdx);
                        String content = "금요일 오늘 날씨는 ";
                        newAlarm.setContents(content);
                        newAlarm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                    }
                }
            case 6:
                if (user.getSat() == 'Y') {
                    if (lt.getHour() == user.getOutTime().toLocalTime().getHour() && lt.getMinute() == user.getOutTime().toLocalTime().getMinute()) {
                        newAlarm.setUserIdx(userIdx);
                        String content = "토요일 오늘 날씨는 ";
                        newAlarm.setContents(content);
                        newAlarm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                    }
                }
            case 7:
                if (user.getSun() == 'Y') {
                    if (lt.getHour() == user.getOutTime().toLocalTime().getHour() && lt.getMinute() == user.getOutTime().toLocalTime().getMinute()) {
                        newAlarm.setUserIdx(userIdx);
                        String content = "일요일 오늘 날씨는 ";
                        newAlarm.setContents(content);
                        newAlarm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                    }
                }
        }

        this.alarmRepository.saveAndFlush(newAlarm);
        return newAlarm.getContents();
    }

    public String createClimateAlarm(Long userIdx) {
        AlarmEntity newAlarm = new AlarmEntity();
        UserDetailEntity user = userDetailRepository.findById(userIdx).get();

        if(user.getClimateAlarm() == 'Y') {
            newAlarm.setUserIdx(userIdx);
            //콘텐츠는 api정보 알게되면 추후 디테일하게 수정
            newAlarm.setContents("새벽에 온도가 많이 떨어져요. 창문을 닫고 주무세요.");
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            newAlarm.setCreatedAt(ts);
        }
        this.alarmRepository.saveAndFlush(newAlarm);
        return newAlarm.getContents();
    }

    public String createCustomAlarm(Long userIdx) {
        AlarmEntity newAlarm = new AlarmEntity();
        UserDetailEntity user = userDetailRepository.findById(userIdx).get();

        if(user.getCustomAlarm() == 'Y') {
            newAlarm.setUserIdx(userIdx);
            newAlarm.setContents("");
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            newAlarm.setCreatedAt(ts);
        }
        this.alarmRepository.saveAndFlush(newAlarm);
        return newAlarm.getContents();
    }

    public String createRainAlarm(Long userIdx) {
        AlarmEntity newAlarm = new AlarmEntity();
        UserDetailEntity user = userDetailRepository.findById(userIdx).get();

        if(user.getRainAlarm() == 'Y') {
            newAlarm.setUserIdx(userIdx);
            newAlarm.setContents("오늘은 비가 와요!\n" +
                    "우산을 챙겨가세요.");
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            newAlarm.setCreatedAt(ts);
        }
        this.alarmRepository.saveAndFlush(newAlarm);
        return newAlarm.getContents();
    }

    public String createSnowAlarm(Long userIdx) {
        AlarmEntity newAlarm = new AlarmEntity();
        UserDetailEntity user = userDetailRepository.findById(userIdx).get();

        if(user.getSnowAlarm() == 'Y') {
            newAlarm.setUserIdx(userIdx);
            newAlarm.setContents("오늘은 눈이 와요!\n" +
                    "우산을 챙겨가세요.");
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            newAlarm.setCreatedAt(ts);
        }
        this.alarmRepository.saveAndFlush(newAlarm);
        return newAlarm.getContents();
    }

    public String createWindAlarm(Long userIdx, Integer windValue) {
        AlarmEntity newAlarm = new AlarmEntity();
        UserDetailEntity user = userDetailRepository.findById(userIdx).get();

        if(user.getWindAlarm() == 'Y') {
            if(user.getWindValue() < windValue) {
                newAlarm.setUserIdx(userIdx);
                newAlarm.setContents("대체로 바람이 많이 부는 날이에요.");
                Timestamp ts = new Timestamp(System.currentTimeMillis());
                newAlarm.setCreatedAt(ts);
            }

        }
        this.alarmRepository.saveAndFlush(newAlarm);
        return newAlarm.getContents();
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
