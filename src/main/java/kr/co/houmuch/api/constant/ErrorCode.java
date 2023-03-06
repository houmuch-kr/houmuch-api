package kr.co.houmuch.api.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 규칙 : contract관련 에러는 앞에 C붙여주기!
    CODE_C1000("요청 파라미터가 누락되었습니다."),
    CODE_C1001("해당 지역의 실거래 내역이 존재하지 않습니다."),
    CODE_E9999("알 수 없는 오류가 발생했습니다.");

    private String message;
}