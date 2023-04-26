package com.cora.block.dto;

import cn.hutool.http.HttpStatus;
import lombok.Data;

/**
 * Api结果
 * @author maochaowu
 * @date 2023/4/26 16:48
 */
@Data
public class ApiResult<T> {

    private Integer code;

    private String message;

    private T data;

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setData(data);
        apiResult.setCode(HttpStatus.HTTP_OK);
        return apiResult;
    }

    public static <T> ApiResult<T> fail(String message) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
        apiResult.setMessage(message);
        return apiResult;
    }
}
