package com.cora.block.bcos;

import cn.hutool.core.util.ObjectUtil;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

/**
 * Bcos Api
 * @author maochaowu
 * @date 2023/4/26 14:13
 */
@Component
public class BcosApi {

    private BcosSDK bcosSDK;

    @Value("${blockchain.switch}")
    private boolean bcosSwitch;

    @PostConstruct
    public void init() {
        if (!bcosSwitch) {
            return;
        }
        ClassPathResource classPathResource = new ClassPathResource("config.toml");
        String configPath;
        try {
            configPath = classPathResource.getFile().getPath();
        } catch (IOException e) {
            throw new IllegalStateException("区块链配置不存在");
        }
        bcosSDK = BcosSDK.build(configPath);
    }

    /**
     * 群组id
     * @author maochaowu
     * @date 2023/4/26 14:18
     * @param groupId  区块链群组ID
     * @return org.fisco.bcos.sdk.client.Client
     */
    public Client getClient(Integer groupId) {
        return bcosSDK.getClient(groupId);
    }

    @PreDestroy
    public void destroy() {
        if (ObjectUtil.isNotNull(bcosSDK)) {
            bcosSDK.stopAll();
        }
    }
}
