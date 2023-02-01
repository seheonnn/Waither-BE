package com.waither.openapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;


public class RegionTransfer {
    Map<String, Integer> map = new HashMap<>();

    public RegionTransfer() {
        map.put("속초", 90);
        map.put("북춘천", 93);
        map.put("철원", 95);
        map.put("동두천", 98);
        map.put("파주", 99);
        map.put("대관령", 100);
        map.put("춘천", 101);
        map.put("백령도", 102);
        map.put("북강릉", 104);
        map.put("강릉", 105);
        map.put("동해", 106);
        map.put("서울", 108);
        map.put("인천", 112);
        map.put("원주", 114);
        map.put("울릉도", 115);

        map.put("수원", 119);
        map.put("영월", 121);
        map.put("충주", 127);
        map.put("서산", 129);
        map.put("울진", 130);
        map.put("청주", 131);
        map.put("대전", 133);
        map.put("추풍령", 135);
        map.put("안동", 136);
        map.put("상주", 137);
        map.put("포항", 138);
        map.put("군산", 140);
        map.put("대구", 143);
        map.put("전주", 146);
        map.put("울산", 152);
        map.put("창원", 155);
        map.put("광주", 156);
        map.put("부산", 159);

        map.put("통영", 162);
        map.put("목포", 165);
        map.put("여수", 168);
        map.put("흑산도", 169);
        map.put("완도", 170);
        map.put("고창", 172);
        map.put("순천", 174);
        map.put("홍성", 177);
        map.put("제주", 184);
        map.put("고산", 185);
        map.put("성산", 188);
        map.put("서귀포", 189);
        map.put("진주", 192);
        map.put("강화", 201);
        map.put("양평", 202);
        map.put("이천", 203);
        map.put("인제", 211);

        map.put("홍천", 212);
        map.put("태백", 216);
        map.put("정선군", 217);
        map.put("제천", 221);
        map.put("보은", 226);
        map.put("천안", 232);
        map.put("보령", 235);
        map.put("부여", 236);
        map.put("금산", 238);
        map.put("세종", 239);
        map.put("부안", 243);
        map.put("임실", 244);
        map.put("정읍", 245);
        map.put("남원", 247);
        map.put("장수", 248);

        map.put("고창군", 251);
        map.put("영광군", 252);
        map.put("김해시", 253);
        map.put("순창군", 254);
        map.put("북창원", 255);
        map.put("양산시", 257);
        map.put("보성군", 258);
        map.put("강진군", 259);
        map.put("장흥", 260);
        map.put("해남", 261);
        map.put("고흥", 262);
        map.put("의령군", 263);
        map.put("함양군", 264);
        map.put("광양시", 266);
        map.put("진도군", 268);
        map.put("봉화", 271);
        map.put("영주", 272);
        map.put("문경", 273);

        map.put("청송군", 276);
        map.put("영덕", 277);
        map.put("의성", 278);
        map.put("구미", 279);
        map.put("영천", 281);
        map.put("경주시", 283);
        map.put("거창", 284);
        map.put("합천", 285);
        map.put("밀양", 288);
        map.put("산청", 289);
        map.put("거제", 294);
        map.put("남해", 295);
    }


    public int get(String region) {
        return this.map.get(region);
    }
}
