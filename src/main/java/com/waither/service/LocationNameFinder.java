package com.waither.service;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class LocationNameFinder {

//    // 구글 맵스 API 키
//    String apiKey = "구글맵스API키";
//
//    // 위도와 경도
//    double latitude = 37.5666102;
//    double longitude = 126.9783881;
//
//    // REST API 호출 URL
//    String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?latlng="
//            + latitude + "," + longitude + "&key=" + apiKey;
//
//    // HttpClient 생성
//    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//
//    // GET 메서드 생성
//    HttpGet httpGet = new HttpGet(apiUrl);
//
//    // REST API 호출
//    CloseableHttpResponse response = httpClient.execute(httpGet);
//
//    // 응답 결과 추출
//    String responseBody = EntityUtils.toString(response.getEntity());
//
//    // JSON 파싱
//    JSONObject json = new JSONObject(responseBody);
//    JSONArray results = json.getJSONArray("results");
//    JSONObject result = results.getJSONObject(0);
//    String formattedAddress = result.getString("formatted_address");
//
//    // 결과 출력
//        System.out.println(formattedAddress);
//
}
