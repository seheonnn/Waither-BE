package com.waither.openapi;

import com.waither.openapi.model.GetWeatherRes;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/app/main")

public class OpenController {

    /*@Autowired
    private final OpenProvider openProvider;*/


    public OpenController() throws Exception{

        GetWeatherRes dto = new GetWeatherRes();


        // 변수 설정
        //초단기 실황 조회
        String apiURL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
        String authKey = "Xs2%2Frnfl80eyOtrEWs0Y4yBvqtH0Fv2LH5yDf836On7erI0qnhiIOBArWJAMTEiTfd5M4D%2B8WUVtXkU5EhGMJw%3D%3D"; // 개인 서비스 키

        // 요청 xy좌표 대입
        String nx = "55";
        String ny = "127";

        String dataType = "JSON";

        //요청 날짜와 시간 받아서 계산
        LocalDateTime now = LocalDateTime.now();
        String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int hour = now.getHour();
        int min = now.getMinute();
        if(min <= 30) { // 해당 시각 발표 전에는 자료가 없음 - 이전시각을 기준으로
            hour -= 1;
        }
        String baseTime = hour + "00"; // 정시 기준 호출 가능


        StringBuilder urlBuilder = new StringBuilder(apiURL);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + authKey);
        urlBuilder.append("&" + URLEncoder.encode("numOfRows=10", "UTF-8"));    // 표 개수
        urlBuilder.append("&" + URLEncoder.encode("pageNo=1", "UTF-8"));    // 페이지 수
        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8")); // 반환 타입
        urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); // 조회 날짜
        urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); // 조회 시간
        urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); // x좌표
        urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); // y좌표

        URL url = new URL(urlBuilder.toString());
        System.out.println(url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder data = new StringBuilder();

        String line;
        while ((line = rd.readLine()) != null) {
            data.append(line);
        }
        rd.close();
        conn.disconnect();
        String result = data.toString();

        // 테스트를 위해 콘솔 출력
        System.out.println(result);

        //데이터 수신완료
        //json 파싱

        Double temp = null;
        Double rainAmount = null;
        Double direction=null;
        Double speed=null;
        Double humid = null;


        JSONObject jObject = new JSONObject(data);
        JSONObject response = jObject.getJSONObject("response");
        JSONObject body = response.getJSONObject("body");
        JSONObject items = body.getJSONObject("items");
        JSONArray jArray = items.getJSONArray("item");

        for(int i = 0; i < jArray.length(); i++) {
            JSONObject obj = jArray.getJSONObject(i);
            String category = obj.getString("category");
            double obsrValue = obj.getDouble("obsrValue");

            switch (category) {
                case "T1H":
                    temp = obsrValue;
                    break;
                case "RN1":
                    rainAmount = obsrValue;
                    break;
                case "VEC":
                    direction = obsrValue;
                    break;
                case "WSD":
                    speed = obsrValue;
                    break;
                case "REH":
                    humid = obsrValue;
                    break;
            }
        }

        dto.setTmp(temp);
        dto.setRn1(rainAmount);
        dto.setVec(direction);
        dto.setWsd(speed);
        dto.setReh(humid);

        System.out.println("수신 온도 출력"+dto.getTmp());
    }

    /*@ResponseBody
    @GetMapping("")
    public String getWeather() throws Exception{
        *//*try {
            return new BaseResponse<>(getWeatherRes());
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }*//*

        return null;
    }*/
}
