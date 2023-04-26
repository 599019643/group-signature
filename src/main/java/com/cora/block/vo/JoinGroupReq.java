package com.cora.block.vo;

import lombok.Data;

/**
 * 加入群组请求
 * @author maochaowu
 * @date 2023/4/25 19:31
 */
@Data
public class JoinGroupReq {
    /**
     * 群名称
     */
    private String groupName;
    /**
     * 成员信息
     */
    private String memberName;
}
