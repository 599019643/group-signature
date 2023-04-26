package com.cora.block.repository.impl;

import com.cora.block.entity.SignInfoEntity;
import com.cora.block.dao.ISignInfoDao;
import com.cora.block.repository.MPSignInfoRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 签名信息 服务实现类
 * </p>
 *
 * @author mao chaowu
 * @since 2023-04-26
 */
@Service
public class SignInfoRepositoryImpl extends ServiceImpl<ISignInfoDao, SignInfoEntity> implements MPSignInfoRepository {

}
