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

    // 메인화면 날씨 데이터 조회(실황, 예보)
    public GetWeatherRes getMainWea(String nx, String ny) throws BaseException {
        try {
            GetWeatherRes getWeatherRes = openDao.getMainWea(nx, ny);
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
