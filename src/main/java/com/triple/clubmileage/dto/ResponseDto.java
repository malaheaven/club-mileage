package com.triple.clubmileage.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.triple.clubmileage.common.enums.ResponseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {

    private Integer code;
    private String message;

    @Nullable
    T result;

    public static <T> ResponseDto<T> success() {
        return new ResponseDto<>(ResponseType.SUCCESS.getCode(), ResponseType.SUCCESS.getMessage(), null);
    }

    public static <T> ResponseDto<T> success(@Nullable final T result) {
        return new ResponseDto<>(ResponseType.SUCCESS.getCode(), ResponseType.SUCCESS.getMessage(), result);
    }

    public static <T> ResponseDto<T> from(final ResponseType responseType) {
        return new ResponseDto<>(responseType.getCode(), responseType.getMessage(), null);
    }

    public static <T> ResponseDto<T> from(final ResponseType responseType, String message) {
        return new ResponseDto<>(responseType.getCode(), message, null);
    }

}
