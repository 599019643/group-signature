package com.cora.block.vo;

import lombok.Data;

/**
 * 签名请求
 * @author maochaowu
 * @date 2023/4/25 19:31
 */
@Data
public class SignReq {
    /**
     * 群名称
     */
    private String groupName;
    /**
     * 成员信息
     */
    private String memberName;

    /**
     * 待签名信息
     */
    private String message;
}
