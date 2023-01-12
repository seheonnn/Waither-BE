/*
package com.waither.config;

import lombok.Getter;
@Getter
public class BaseResponseStatus {
    //200 로딩성공
    SUCCESS(true, 200, "로딩 성공");

    //400 파라미터 오류
    */
/*REQUEST_ERROR(false, 400, "파라미터 오류"),
    //500 서버 오류
    SERVER_ERROR(false, 500, "서버 오류");*//*


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
*/
