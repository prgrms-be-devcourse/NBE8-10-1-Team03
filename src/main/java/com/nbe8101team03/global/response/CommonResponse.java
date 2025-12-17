package com.nbe8101team03.global.response;

import lombok.Getter;


/**
 * 공통 응답 형식입니다. 모든 API 응답은 해당 객체로 래핑한 값이 와야 합니다.
 * <pre>
 *     {@code
 *       return ResponseEntity.ok(CommonResponse.success(orderData);
 *     }
 * </pre>
 * 와 같은 형식을 사용하여 반환할 수 있습니다.
 * @param <T>
 */
@Getter
public class CommonResponse<T> implements Response<T> {
    private final String status;
    private final T data;
    private final String message;

    protected CommonResponse(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> CommonResponse<T> success(T data, String message) {
        return new CommonResponse<>("success", data, message);
    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>("success", data, "success to response");
    }

    public static <T> CommonResponse<T> success() {
        return new CommonResponse<>("success", null, "success to response");
    }

    public static CommonResponse<Void> fail(String message) {
        return new CommonResponse<>("fail", null, message);
    }

    public static CommonResponse<Void> fail() {
        return new CommonResponse<>("fail", null, "fail to response");
    }
}
