package com.waither.openapi;

import com.waither.openapi.model.GetPastWeatherRes;
import com.waither.openapi.model.GetWeatherRes;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Repository
public class OpenDao {
    GetWeatherRes mainDto = new GetWeatherRes();
    String dataType = "JSON";

    //메인페이지 조회
    public GetWeatherRes getMainWea(String nx, String ny) throws Exception {
        //초단기 실황
        getUltraSc(nx,ny);
        //초단기 예보
        getUltraFa(nx,ny);
        //미세먼지
        getAir("부산");
        //일 최고, 최저기온
        CrawlingTemp("수원시");

        return mainDto;
    }

    //하루 최고,최저 기온 조회
    public GetWeatherRes CrawlingTemp(String region) throws Exception{
        System.out.println("크롤링 들어옴");
        String URL = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query="+region+"+날씨";
        Document doc = Jsoup.connect(URL).get();
        Elements min = doc.select(".cs_weather_new .weekly_forecast_area .week_item.today .day_data .cell_temperature .temperature_inner .lowest");
        String[] mstr = min.text().split(" ");
        Elements max = doc.select(".cs_weather_new .weekly_forecast_area .week_item.today .day_data .cell_temperature .temperature_inner .highest");
        String[] Mstr = max.text().split(" ");
        Elements sunStr = doc.select(".cs_weather_new .report_card_wrap .item_today.type_sun .title");

        String sun = sunStr.text();
        String minStr = mstr[0];
        String maxStr = Mstr[0];

        //숫자만 추출
        String minsamp = minStr.substring(4,minStr.length()-1);
        String maxsamp = maxStr.substring(4,maxStr.length()-1);

        if(sun.equals("일출")){
            sun="밤";
        }
        else{
            sun="낮";
        }

        mainDto.setTmx(Integer.parseInt(maxsamp));
        mainDto.setTmn(Integer.parseInt(minsamp));
        mainDto.setDaynight(sun);

        return mainDto;
    }

    // 초단기 실황 조회
    public GetWeatherRes getUltraSc(String nx, String ny) throws Exception{
        // 변수 설정
        //초단기 실황 조회
        String apiURL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
        String authKey = "Xs2%2Frnfl80eyOtrEWs0Y4yBvqtH0Fv2LH5yDf836On7erI0qnhiIOBArWJAMTEiTfd5M4D%2B8WUVtXkU5EhGMJw%3D%3D"; // 개인 서비스 키

        //요청 날짜와 시간 받아서 계산
        LocalDateTime now = LocalDateTime.now();
        String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int hour = now.getHour();
        int min = now.getMinute();
        String baseTime = "";
        //hour=00일때는?
        if(min <= 30) { // 해당 시각 발표 전에는 자료가 없음 - 이전시각을 기준으로
            hour -= 1;
        }
        if(hour<10){
            baseTime = "0" + hour + "00"; // 정시 기준 호출 가능
        }
        else {
            baseTime = hour + "00"; // 정시 기준 호출 가능
        }

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
        int direction=0;
        Double speed=null;
        Double humid = null;

        JSONParser jsonParser = new JSONParser();
        JSONObject jObject =  (JSONObject) jsonParser.parse(result);
        JSONObject response = (JSONObject) jObject.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray jArray = (JSONArray) items.get("item");

        for(int i = 0; i < jArray.size(); i++) {
            JSONObject obj = (JSONObject) jArray.get(i);
            String category = (String) obj.get("category");
            //캐스팅 변환이 아니라 String 클래스의 valueOf(Object)로 가져오기
            double obsrValue = Double.valueOf((String) obj.get("obsrValue"));

            switch (category) {
                case "T1H":
                    temp = obsrValue;
                    break;
                case "RN1":
                    rainAmount = obsrValue;
                    break;
                case "VEC":
                    direction = (int) obsrValue;
                    break;
                case "WSD":
                    speed = obsrValue;
                    break;
                case "REH":
                    humid = obsrValue;
                    break;
            }
        }

        mainDto.setDate(baseDate);
        mainDto.setTime(baseTime);

        mainDto.setTmp(temp);
        mainDto.setRn1(rainAmount);
        mainDto.setVec(direction);
        mainDto.setWsd(speed);
        mainDto.setReh(humid);

        return mainDto;
    }

    //미세먼지 실황 조회
    public GetWeatherRes getAir(String region) throws Exception{
        // 변수 설정
        //초단기 실황 조회
        String apiURL = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
        String authKey = "Xs2%2Frnfl80eyOtrEWs0Y4yBvqtH0Fv2LH5yDf836On7erI0qnhiIOBArWJAMTEiTfd5M4D%2B8WUVtXkU5EhGMJw%3D%3D"; // 개인 서비스 키


        StringBuilder urlBuilder = new StringBuilder(apiURL);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + authKey);
        urlBuilder.append("&" + URLEncoder.encode("returnType", "UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8")); // 반환 타입
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); // 반환 타입
        urlBuilder.append("&" + URLEncoder.encode("pageNo=1", "UTF-8"));    // 페이지 수
        urlBuilder.append("&" + URLEncoder.encode("sidoName", "UTF-8") + "=" + URLEncoder.encode(region, "UTF-8")); // 지역명
        urlBuilder.append("&" + URLEncoder.encode("ver", "UTF-8") + "=" + URLEncoder.encode("1.0", "UTF-8")); // 호출 버전


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

        int value = 0;
        String grade = "";

        JSONParser jsonParser = new JSONParser();

        JSONObject jObject =  (JSONObject) jsonParser.parse(result);
        JSONObject response = (JSONObject) jObject.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONArray jArray = (JSONArray) body.get("items");

        for(int i = 0; i < jArray.size(); i++) {
            JSONObject obj = (JSONObject) jArray.get(i);
            value = Integer.valueOf((String) obj.get("pm10Value"));
            grade = (String) obj.get("pm10Grade");
            
            if(grade.equals("1")){
                grade="좋음";
            }
            else if(grade.equals("2")){
                grade="보통";
            }
            else if(grade.equals("3")){
                grade="나쁨";
            }
            else if(grade.equals("4")){
                grade="매우나쁨";
            }
        }

        mainDto.setPm10_value(value);
        mainDto.setPm10_grade(grade);

        return mainDto;
    }

    //초단기예보조회(기온, 강수량, 하늘상태)
    public GetWeatherRes getUltraFa(String x, String y) throws Exception{
        String apiURL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst";
        String authKey = "Xs2%2Frnfl80eyOtrEWs0Y4yBvqtH0Fv2LH5yDf836On7erI0qnhiIOBArWJAMTEiTfd5M4D%2B8WUVtXkU5EhGMJw%3D%3D"; // 개인 서비스 키

        //요청 날짜와 시간 받아서 계산
        LocalDateTime now = LocalDateTime.now();
        String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int hour = now.getHour();
        int min = now.getMinute();
        String baseTime = "";
        //hour=00일때는?
        if(min <= 30) { // 해당 시각 발표 전에는 자료가 없음 - 이전시각을 기준으로
            hour -= 1;
        }
        if(hour<10){
            baseTime = "0" + hour + "00";
        }
        else {
            baseTime = hour + "00";
        }
        int intTime = Integer.parseInt(baseTime);

        StringBuilder urlBuilder = new StringBuilder(apiURL);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + authKey);
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); // 표 개수
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); // 페이지
        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8")); // 반환 타입
        urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); // 조회 날짜
        urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); // 조회 시간
        urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(x, "UTF-8")); // x좌표
        urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(y, "UTF-8")); // y좌표

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
        JSONParser jsonParser = new JSONParser();
        JSONObject jObject =  (JSONObject) jsonParser.parse(result);
        JSONObject response = (JSONObject) jObject.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray jArray = (JSONArray) items.get("item");


        for(int i = 0; i < jArray.size(); i++) {
            JSONObject obj = (JSONObject) jArray.get(i);
            String category = (String) obj.get("category");
            int fcstTime = Integer.valueOf((String) obj.get("fcstTime"));
            String fcstValue = (String) obj.get("fcstValue");

            switch (category) {
                case "T1H":
                    if(fcstTime==(intTime+100)){
                        mainDto.setExpect_tmp1(Double.parseDouble(fcstValue));
                    }
                    else if(fcstTime==(intTime+200)) {
                        mainDto.setExpect_tmp2(Double.parseDouble(fcstValue));
                    }
                    else if(fcstTime==(intTime+300)) {
                        mainDto.setExpect_tmp3(Double.parseDouble(fcstValue));
                    }
                    else if(fcstTime==(intTime+400)) {
                        mainDto.setExpect_tmp4(Double.parseDouble(fcstValue));
                    }
                    else if(fcstTime==(intTime+500)) {
                        mainDto.setExpect_tmp5(Double.parseDouble(fcstValue));
                    }
                    else if(fcstTime==(intTime+600)) {
                        mainDto.setExpect_tmp6(Double.parseDouble(fcstValue));
                    }
                    break;
                case "RN1":
                    if(fcstValue.equals("강수없음")){
                        fcstValue="0";
                    }
                    if(fcstTime==(intTime+100)){
                        mainDto.setExpect_rn1(fcstValue);
                    }
                    else if(fcstTime==(intTime+200)) {
                        mainDto.setExpect_rn2(fcstValue);
                    }
                    else if(fcstTime==(intTime+300)) {
                        mainDto.setExpect_rn3(fcstValue);
                    }
                    else if(fcstTime==(intTime+400)) {
                        mainDto.setExpect_rn4(fcstValue);
                    }
                    else if(fcstTime==(intTime+500)) {
                        mainDto.setExpect_rn5(fcstValue);
                    }
                    else if(fcstTime==(intTime+600)) {
                        mainDto.setExpect_rn6(fcstValue);
                    }
                    break;
                case "SKY":
                    if(fcstValue.equals("1")){
                        fcstValue="맑음";
                    }
                    else if(fcstValue.equals("3")){
                        fcstValue="구름많음";
                    }
                    else if(fcstValue.equals("4")){
                        fcstValue="흐림";
                    }

                    if(fcstTime==(intTime+100)){
                        mainDto.setExpect_sky1(fcstValue);
                    }
                    else if(fcstTime==(intTime+200)) {
                        mainDto.setExpect_sky2(fcstValue);
                    }
                    else if(fcstTime==(intTime+300)) {
                        mainDto.setExpect_sky3(fcstValue);
                    }
                    else if(fcstTime==(intTime+400)) {
                        mainDto.setExpect_sky4(fcstValue);
                    }
                    else if(fcstTime==(intTime+500)) {
                        mainDto.setExpect_sky5(fcstValue);
                    }
                    else if(fcstTime==(intTime+600)) {
                        mainDto.setExpect_sky6(fcstValue);
                    }
                    break;
            }
        }
        return mainDto;
    }

    //과거 날씨 기온 조회
    public GetPastWeatherRes getPastWea(String date, String time, String region) throws Exception{

        GetPastWeatherRes pastDto = new GetPastWeatherRes();
        // 변수 설정
        String apiURL = "http://apis.data.go.kr/1360000/AsosHourlyInfoService/getWthrDataList";
        String authKey = "Xs2%2Frnfl80eyOtrEWs0Y4yBvqtH0Fv2LH5yDf836On7erI0qnhiIOBArWJAMTEiTfd5M4D%2B8WUVtXkU5EhGMJw%3D%3D"; // 개인 서비스 키

        StringBuilder urlBuilder = new StringBuilder(apiURL);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + authKey);
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호 Default : 10*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수 Default : 1*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default : XML*/
        urlBuilder.append("&" + URLEncoder.encode("dataCd","UTF-8") + "=" + URLEncoder.encode("ASOS", "UTF-8")); /*자료 분류 코드(ASOS)*/
        urlBuilder.append("&" + URLEncoder.encode("dateCd","UTF-8") + "=" + URLEncoder.encode("HR", "UTF-8")); /*날짜 분류 코드(HR)*/
        urlBuilder.append("&" + URLEncoder.encode("startDt","UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")); /*조회 기간 시작일(YYYYMMDD)*/
        urlBuilder.append("&" + URLEncoder.encode("startHh","UTF-8") + "=" + URLEncoder.encode(time, "UTF-8")); /*조회 기간 시작시(HH)*/
        urlBuilder.append("&" + URLEncoder.encode("endDt","UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")); /*조회 기간 종료일(YYYYMMDD) (전일(D-1) 까지 제공)*/
        urlBuilder.append("&" + URLEncoder.encode("endHh","UTF-8") + "=" + URLEncoder.encode(time, "UTF-8")); /*조회 기간 종료시(HH)*/
        urlBuilder.append("&" + URLEncoder.encode("stnIds","UTF-8") + "=" + URLEncoder.encode(region, "UTF-8")); /*종관기상관측 지점 번호 (활용가이드 하단 첨부 참조)*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        String result = sb.toString();
        //데이터 수신완료
        System.out.println(result);

        //json 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jObject =  (JSONObject) jsonParser.parse(result);
        JSONObject response = (JSONObject) jObject.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray jArray = (JSONArray) items.get("item");

        for(int i = 0; i < jArray.size(); i++) {
            JSONObject obj = (JSONObject) jArray.get(i);
            //캐스팅 변환이 아니라 String 클래스의 valueOf(Object)로 가져오기
            double ta = Double.valueOf((String) obj.get("ta"));
            pastDto.setTmp(ta);
        }
        return pastDto;
    }

}
