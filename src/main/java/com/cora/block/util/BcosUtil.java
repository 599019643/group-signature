package com.cora.block.util;

import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.eventsub.EventSubscribe;

import java.lang.reflect.Field;

/**
 * Bcos 工具类
 * @author maochaowu
 * @date 2023/4/26 14:51
 */
public class BcosUtil {
    /**
     * 关闭合约
     * @author maochaowu
     * @date 2023/4/26 14:52
     * @param contract  合约
     */
    public static void destroy(Contract contract) {
        if (null != contract) {
            try {
                Field field = Contract.class.getDeclaredField("eventSubscribe");
                field.setAccessible(true);
                EventSubscribe eventSubscribe = (EventSubscribe) field.get(contract);
                eventSubscribe.stop();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
