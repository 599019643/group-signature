package com.cora.block.bcos;

import com.cora.block.contract.GroupSig;
import com.cora.block.exception.CustomException;
import com.cora.block.util.BcosUtil;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 群签名合约调用实现
 * @author maochaowu
 * @date 2023/4/26 14:19
 */
@Slf4j
@Component
public class GroupSigService {

    @Resource
    private BcosApi bcosApi;

    @Value("${blockchain.group.id:1}")
    private Integer groupId;

    @Value("${blockchain.sig.address:}")
    private String contractAddress;

    /**
     * 部署合约
     * @author maochaowu
     * @date 2023/4/26 14:20
     * @return java.lang.String
     */
    public String deployContract() {
        Client client = bcosApi.getClient(groupId);
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().getCryptoKeyPair();
        GroupSig groupSig = null;
        try {
            groupSig = GroupSig.deploy(client, cryptoKeyPair);
            return groupSig.getContractAddress();
        } catch (ContractException e) {
            log.error("部署群签名合约失败", e);
            throw new CustomException("部署群签名合约失败");
        } finally {
            BcosUtil.destroy(groupSig);
        }
    }

    /**
     * 群签名校验
     * @author maochaowu
     * @date 2023/4/26 14:49
     * @param sig             群签名信息
     * @param gpk             群公钥信息
     * @param pbcParam        群签名所在组的pbc参数信息
     * @param message         签名对应的明文信息
     * @return boolean
     */
    public boolean groupSigVerify(String sig, String gpk, String pbcParam, String message) {
        Client client = bcosApi.getClient(groupId);
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().getCryptoKeyPair();
        GroupSig groupSig = GroupSig.load(contractAddress, client, cryptoKeyPair);
        try {
            TransactionReceipt transactionReceipt = groupSig.verify_group_sig(sig, message, gpk, pbcParam);
            log.info("群签名校验返回信息：{}", transactionReceipt);
            Tuple1<Boolean> result = groupSig.getVerify_group_sigOutput(transactionReceipt);
            return result.getValue1();
        } catch (Exception e) {
            log.error("明文={}对应的签名校验失败", message, e);
            return false;
        } finally {
            BcosUtil.destroy(groupSig);
        }
    }

}
