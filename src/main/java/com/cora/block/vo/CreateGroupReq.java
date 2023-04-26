package com.cora.block.vo;

import lombok.Data;

/**
 * 创建群组请求
 * @author maochaowu
 * @date 2023/4/25 19:31
 */
@Data
public class CreateGroupReq {
    /**
     * 群名称
     */
    private String groupName;
    /**
     * 群描述
     */
    private String groupDesc;
    /**
     * 群签名参数   {\"exp2\":\"159\",\"exp1\":\"107\",\"type\":\"a\"}
     */
    private String pairingParam;
}
