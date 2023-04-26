package com.cora.block.exception;

import lombok.Data;

/**
 * 业务异常
 * @author maochaowu
 * @date 2023/4/26 14:23
 */
@Data
public class CustomException extends RuntimeException {

    private String errorMsg;

    public CustomException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public CustomException(String errorMsg, Exception exception) {
        super(exception);
        this.errorMsg = errorMsg;
    }
}
