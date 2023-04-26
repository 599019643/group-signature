package com.cora.block.controller;

import com.cora.block.dto.ApiResult;
import com.cora.block.repository.MPGroupInfoRepository;
import com.cora.block.vo.CreateGroupReq;
import com.cora.block.vo.JoinGroupReq;
import com.cora.block.vo.OpenSignReq;
import com.cora.block.vo.SignReq;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 群签名接口
 * @author maochaowu
 * @date 2023/4/25 19:25
 */
@RestController
public class GroupSignatureController {

    @Resource
    private MPGroupInfoRepository groupInfoRepository;

    /**
     * 创建群组
     * @author maochaowu
     * @date 2023/4/26 17:12
     * @param createGroupReq 创建群组请求
     * @return com.cora.block.dto.ApiResult<java.lang.Boolean>
     */
    @PostMapping("createGroup")
    public ApiResult<Boolean> createGroup(@RequestBody CreateGroupReq createGroupReq) {
        return ApiResult.success(groupInfoRepository.createGroup(createGroupReq));
    }

    /**
     * 加入群组
     * @author maochaowu
     * @date 2023/4/26 17:12
     * @param joinGroupReq 加入群组请求
     * @return com.cora.block.dto.ApiResult<java.lang.Boolean>
     */
    @PostMapping("joinGroup")
    public ApiResult<Boolean> joinGroup(@RequestBody JoinGroupReq joinGroupReq) {
        return ApiResult.success(groupInfoRepository.joinGroup(joinGroupReq));
    }

    /**
     * 群组签名
     * @author maochaowu
     * @date 2023/4/26 17:12
     * @param signReq 签名请求
     * @return com.cora.block.dto.ApiResult<java.lang.Boolean>
     */
    @PostMapping("sign")
    public ApiResult<Boolean> sign(@RequestBody SignReq signReq) {
        return ApiResult.success(groupInfoRepository.sign(signReq));
    }

    /**
     * 打开签名
     * @author maochaowu
     * @date 2023/4/26 17:12
     * @param openSignReq 打开签名请求
     * @return com.cora.block.dto.ApiResult<java.lang.String>
     */
    @PostMapping("openSign")
    public ApiResult<String> openSign(@RequestBody OpenSignReq openSignReq) {
        return ApiResult.success(groupInfoRepository.openSign(openSignReq));
    }
}
