package kr.co.houmuch.api.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private String code;
    private String message;
    private T data;

    public static ApiResponse<Void> empty() {
        return new ApiResponse<>();
    }

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(null, null, data);
    }
}
