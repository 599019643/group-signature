package com.cora.block;

import cn.hutool.core.util.StrUtil;
import com.cora.block.bcos.GroupSigService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 群签名合约测试
 * @author maochaowu
 * @date 2023/4/26 14:29
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GroupSigTest {

    @Resource
    private GroupSigService groupSigService;

    //@Test
    public void deployContract() {
        String contractAddress = groupSigService.deployContract();
        System.out.println(contractAddress);
        assert StrUtil.isNotBlank(contractAddress);
    }
}
