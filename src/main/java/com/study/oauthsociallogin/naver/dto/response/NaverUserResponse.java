package com.study.oauthsociallogin.naver.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
// 네이버 사용자 정보 요청 API의 응답 데이터를 매핑
public class NaverUserResponse {
    // 네이버 API 호출 결과 코드 - 일반적으로 "00"은 성공 그 외는 오류
    @JsonProperty("resultcode")
    private String resultCode;
    // 호출 결과에 대한 메시지 - 성공 또는 오류 내용을 포함
    @JsonProperty("message")
    private String message;
    // 사용자 정보를 담고 있는 하위 객체 - 네이버 API의 response 키와 매핑
    @JsonProperty("response")
    private NaverUserDetail naverUserDetail;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    // NaverUserResponse의 하위 클래스 - response 키에 포함된 사용자 세부 정보를 매핑
    public static class NaverUserDetail {
        // 네이버에서 제공하는 사용자 고유 ID
        private String id;
        // 사용자의 이름
        private String name;
        // 사용자의 이메일 주소
        private String email;
    }
}
