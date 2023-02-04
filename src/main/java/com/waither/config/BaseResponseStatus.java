package com.waither.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 200 : 요청 성공
     */
    SUCCESS(true, 200, "로딩 성공"),

    /**
    * 200: 요청 성공 - POST
     * */
    SUCCESS_POST(true,200,"변경 성공"),

    INVALID(true, 204, "일치하지 않음"),

    /**
     * 400 : Request 오류
     */
    REQUEST_ERROR(false, 400, "파라미터 오류"),

    //500 서버 오류
    SERVER_ERROR(false, 500, "서버 오류");



    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
