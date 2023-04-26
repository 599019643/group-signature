package com.cora.block.handle;

import com.cora.block.dto.ApiResult;
import com.cora.block.exception.CustomException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 群签名公用应用异常处理器
 * @author maochaowu
 * @date 2023/4/26 17:20
 */
@RestControllerAdvice
public class GroupSignExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ApiResult<Void> customException(CustomException customException) {
        return ApiResult.fail(customException.getErrorMsg());
    }

    @ExceptionHandler(Exception.class)
    public ApiResult<Void> exception(Exception exception) {
        return ApiResult.fail("系统处理异常");
    }
}
