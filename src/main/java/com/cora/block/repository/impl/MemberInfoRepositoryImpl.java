package com.cora.block.repository.impl;

import com.cora.block.entity.MemberInfoEntity;
import com.cora.block.dao.IMemberInfoDao;
import com.cora.block.repository.MPMemberInfoRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 群成员信息 服务实现类
 * </p>
 *
 * @author mao chaowu
 * @since 2023-04-25
 */
@Service
public class MemberInfoRepositoryImpl extends ServiceImpl<IMemberInfoDao, MemberInfoEntity> implements MPMemberInfoRepository {

}
