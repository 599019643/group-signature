package com.cora.block.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cora.block.entity.GroupInfoEntity;
import com.cora.block.vo.CreateGroupReq;
import com.cora.block.vo.JoinGroupReq;
import com.cora.block.vo.OpenSignReq;
import com.cora.block.vo.SignReq;

/**
 * <p>
 * 群信息表 服务类
 * </p>
 *
 * @author mao chaowu
 * @since 2023-04-25
 */
public interface MPGroupInfoRepository extends IService<GroupInfoEntity> {

    /**
     * 创建群组
     * @author maochaowu
     * @date 2023/4/25 19:34
     * @param createGroupReq 创建群组请求
     */
    boolean createGroup(CreateGroupReq createGroupReq);

    /**
     * 加入群组
     * @author maochaowu
     * @date 2023/4/25 20:11
     * @param joinGroupReq 加入群组请求
     */
    boolean joinGroup(JoinGroupReq joinGroupReq);

    /**
     * 群组签名
     * @author maochaowu
     * @date 2023/4/26 9:10
     * @param signReq 签名请求
     */
    boolean sign(SignReq signReq);

    /**
     * 打开签名
     * @author maochaowu
     * @date 2023/4/26 11:24
     * @param openSignReq       打开签名
     */
    String openSign(OpenSignReq openSignReq);
}
