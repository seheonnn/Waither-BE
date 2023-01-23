package com.waither.openapi;

import com.waither.config.BaseException;
import com.waither.openapi.model.GetPastWeatherRes;
import com.waither.openapi.model.GetWeatherReq;
import com.waither.openapi.model.GetWeatherRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.waither.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리

@RequiredArgsConstructor
@Service
public class OpenProvider {

    private final OpenDao openDao;

    // 좌표값의 초단기 실황 조회
    public GetWeatherRes getUltraSc(String x, String y) throws BaseException {
        try {
            GetWeatherRes getWeatherRes = openDao.getUltraSc(x, y);
            return getWeatherRes;
        } catch (Exception exception) {
            throw new BaseException(SERVER_ERROR);
        }
    }



    // 과거 날씨데이터 조회-> 설문 저장에 사용
    public GetPastWeatherRes getPastWea(String date, String time, String region) throws BaseException {
        try {
            GetPastWeatherRes getPastWeatherRes = openDao.getPastWea(date, time, region);
            return getPastWeatherRes;
        } catch (Exception exception) {
            throw new BaseException(SERVER_ERROR);
        }
    }
}
