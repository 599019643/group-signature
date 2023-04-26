package com.cora.block.repository.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cora.block.bbs04.*;
import com.cora.block.dao.IGroupInfoDao;
import com.cora.block.entity.GroupInfoEntity;
import com.cora.block.entity.MemberInfoEntity;
import com.cora.block.entity.SignInfoEntity;
import com.cora.block.exception.CustomException;
import com.cora.block.repository.MPGroupInfoRepository;
import com.cora.block.repository.MPMemberInfoRepository;
import com.cora.block.repository.MPSignInfoRepository;
import com.cora.block.vo.CreateGroupReq;
import com.cora.block.vo.JoinGroupReq;
import com.cora.block.vo.OpenSignReq;
import com.cora.block.vo.SignReq;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.parameters.PropertiesParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.Map;

/**
 * <p>
 * 群信息表 服务实现类
 * </p>
 *
 * @author mao chaowu
 * @since 2023-04-25
 */
@Slf4j
@Service
public class GroupInfoRepositoryImpl extends ServiceImpl<IGroupInfoDao, GroupInfoEntity> implements MPGroupInfoRepository {

    @Resource
    private MPMemberInfoRepository memberInfoRepository;
    @Resource
    private MPSignInfoRepository signInfoRepository;

    @Override
    public boolean createGroup(CreateGroupReq createGroupReq) {
        boolean flag = validNameRepeat(createGroupReq.getGroupName());
        Assert.isFalse(flag, () -> new CustomException("群组名称重复"));
        PropertiesParameters parameters = compPropertiesParameters(createGroupReq.getPairingParam());
        BBS04 bbs04 = new BBS04();
        Pairing pairing = PairingFactory.getPairing(parameters);
        Element og1 = pairing.getG1().newRandomElement();
        Element og2 = pairing.getG2().newRandomElement();
        PrivateKey privateKey = bbs04.GenerateGroup(og1, og2, pairing);
        Base64.Encoder encoder = Base64.getEncoder();
        Group group = privateKey.Group;

        String gamma = encoder.encodeToString(privateKey.gamma.toBytes());
        String xi1 = encoder.encodeToString(privateKey.xi1.toBytes());
        String xi2 = encoder.encodeToString(privateKey.xi2.toBytes());
        String g1 = encoder.encodeToString(group.g1.toBytes());
        String h = encoder.encodeToString(group.h.toBytes());
        String u = encoder.encodeToString(group.u.toBytes());
        String v = encoder.encodeToString(group.v.toBytes());
        String g2 = encoder.encodeToString(group.g2.toBytes());
        String w = encoder.encodeToString(group.w.toBytes());
        String ehw = encoder.encodeToString(group.ehw.toBytes());
        String ehg2 = encoder.encodeToString(group.ehg2.toBytes());

        GroupInfoEntity groupInfoEntity = new GroupInfoEntity();
        groupInfoEntity.setGroupName(createGroupReq.getGroupName());
        groupInfoEntity.setGroupDesc(createGroupReq.getGroupDesc());
        groupInfoEntity.setPairingParam(createGroupReq.getPairingParam());
        groupInfoEntity.setGmma(gamma);
        groupInfoEntity.setXi1(xi1);
        groupInfoEntity.setXi2(xi2);
        groupInfoEntity.setG1(g1);
        groupInfoEntity.setH(h);
        groupInfoEntity.setU(u);
        groupInfoEntity.setV(v);
        groupInfoEntity.setG2(g2);
        groupInfoEntity.setW(w);
        groupInfoEntity.setEhw(ehw);
        groupInfoEntity.setEhg2(ehg2);
        return save(groupInfoEntity);
    }

    @Override
    public boolean joinGroup(JoinGroupReq joinGroupReq) {
        BBS04 bbs04 = new BBS04();
        String groupName = joinGroupReq.getGroupName();
        GroupInfoEntity groupInfoEntity = loadByName(groupName);
        Assert.notNull(groupInfoEntity, () -> new CustomException("群组信息不存在"));
        boolean flag = validMemberRepeat(groupInfoEntity.getId(), joinGroupReq.getMemberName());
        Assert.isFalse(flag, () -> new CustomException("群成员重复"));

        PrivateKey privateKey = compPrivateKey(groupInfoEntity);
        Cert cert = bbs04.Cert(privateKey);

        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();
        memberInfoEntity.setGroupId(groupInfoEntity.getId());
        memberInfoEntity.setMemberName(joinGroupReq.getMemberName());

        Base64.Encoder encoder = Base64.getEncoder();
        String a1 = encoder.encodeToString(cert.a1.toBytes());
        memberInfoEntity.setA1(a1);
        String b1 = encoder.encodeToString(cert.b1.toBytes());
        memberInfoEntity.setB1(b1);
        return memberInfoRepository.save(memberInfoEntity);
    }


    @Override
    public boolean sign(SignReq signReq) {
        BBS04 bbs04 = new BBS04();
        String groupName = signReq.getGroupName();
        GroupInfoEntity groupInfoEntity = loadByName(groupName);
        Assert.notNull(groupInfoEntity, () -> new CustomException("群组信息不存在"));
        MemberInfoEntity memberInfoEntity = getByMemberName(groupInfoEntity.getId(), signReq.getMemberName());
        Assert.notNull(memberInfoEntity, () -> new CustomException("群成员不存在"));
        boolean flag = validSignRepeat(groupInfoEntity.getId(), memberInfoEntity.getId());
        Assert.isFalse(flag, () -> new CustomException("群成员已处理过对应消息"));

        PrivateKey privateKey = compPrivateKey(groupInfoEntity);
        Cert cert = compMemberCert(privateKey, memberInfoEntity);

        Sig sig = bbs04.sign(cert, signReq.getMessage());
        Base64.Encoder encoder = Base64.getEncoder();
        SignInfoEntity signInfo = new SignInfoEntity();
        signInfo.setGroupId(groupInfoEntity.getId());
        signInfo.setMemberId(memberInfoEntity.getId());
        signInfo.setMessage(signReq.getMessage());

        String t1 = encoder.encodeToString(sig.t1.toBytes());
        String t2 = encoder.encodeToString(sig.t2.toBytes());
        String t3 = encoder.encodeToString(sig.t3.toBytes());
        String c = encoder.encodeToString(sig.c.toBytes());
        String salpha = encoder.encodeToString(sig.salpha.toBytes());
        String sbeta = encoder.encodeToString(sig.sbeta.toBytes());
        String sa = encoder.encodeToString(sig.sa.toBytes());
        String sdelta1 = encoder.encodeToString(sig.sdelta1.toBytes());
        String sdelta2 = encoder.encodeToString(sig.sdelta2.toBytes());
        signInfo.setT1(t1);
        signInfo.setT2(t2);
        signInfo.setT3(t3);
        signInfo.setC(c);
        signInfo.setSalpha(salpha);
        signInfo.setSbeta(sbeta);
        signInfo.setSa(sa);
        signInfo.setSdelta1(sdelta1);
        signInfo.setSdelta2(sdelta2);
        return signInfoRepository.save(signInfo);
    }

    @Override
    public String openSign(OpenSignReq openSignReq) {
        BBS04 bbs04 = new BBS04();
        Base64.Encoder encoder = Base64.getEncoder();
        String groupName = openSignReq.getGroupName();
        GroupInfoEntity groupInfoEntity = loadByName(groupName);
        Assert.notNull(groupInfoEntity, () -> new CustomException("群组信息不存在"));
        MemberInfoEntity memberInfoEntity = getByMemberName(groupInfoEntity.getId(), openSignReq.getMemberName());
        Assert.notNull(memberInfoEntity, () -> new CustomException("群成员不存在"));
        SignInfoEntity signInfo = getSignInfo(groupInfoEntity.getId(), memberInfoEntity.getId());
        Assert.notNull(signInfo, () -> new CustomException("群签名不存在"));
        PrivateKey privateKey = compPrivateKey(groupInfoEntity);
        Sig sig = compMemberSig(privateKey, signInfo);
        Element element = bbs04.Open(privateKey, sig);
        String a1 = encoder.encodeToString(element.toBytes());
        MemberInfoEntity memberInfoEntitySign = getByMemberA1(groupInfoEntity.getId(), a1);
        Assert.notNull(memberInfoEntitySign, () -> new CustomException("解密出的用户不存在"));
        return memberInfoEntitySign.getMemberName();
    }


    private Sig compMemberSig(PrivateKey privateKey, SignInfoEntity signInfo) {
        Base64.Decoder decoder = Base64.getDecoder();
        Sig sig = new Sig();
        Pairing pairing = privateKey.Group.pairing;
        byte[] t1Byte = decoder.decode(signInfo.getT1());
        byte[] t2Byte = decoder.decode(signInfo.getT2());
        byte[] t3Byte = decoder.decode(signInfo.getT3());
        byte[] cByte = decoder.decode(signInfo.getC());
        byte[] salphaByte = decoder.decode(signInfo.getSalpha());
        byte[] sbetaByte = decoder.decode(signInfo.getSbeta());
        byte[] saByte = decoder.decode(signInfo.getSa());
        byte[] sdelta1Byte = decoder.decode(signInfo.getSdelta1());
        byte[] sdelta2Byte = decoder.decode(signInfo.getSdelta2());

        sig.t1 = pairing.getG1().newElementFromBytes(t1Byte);
        sig.t2 = pairing.getG1().newElementFromBytes(t2Byte);
        sig.t3 = pairing.getG1().newElementFromBytes(t3Byte);
        sig.c = pairing.getZr().newElementFromBytes(cByte);
        sig.salpha = pairing.getZr().newElementFromBytes(salphaByte);
        sig.sbeta = pairing.getZr().newElementFromBytes(sbetaByte);
        sig.sa = pairing.getZr().newElementFromBytes(saByte);
        sig.sdelta1 = pairing.getZr().newElementFromBytes(sdelta1Byte);
        sig.sdelta2 = pairing.getZr().newElementFromBytes(sdelta2Byte);
        return sig;
    }

    /**
     * 根据群组信息组装群组私钥
     * @author maochaowu
     * @date 2023/4/26 9:17
     * @param groupInfoEntity 群组信息
     * @return com.cora.block.bbs04.PrivateKey
     */
    private PrivateKey compPrivateKey(GroupInfoEntity groupInfoEntity) {
        PropertiesParameters parameters = compPropertiesParameters(groupInfoEntity.getPairingParam());
        Pairing pairing = PairingFactory.getPairing(parameters);

        Base64.Decoder decoder = Base64.getDecoder();
        Group group = new Group();
        byte[] g1Byte = decoder.decode(groupInfoEntity.getG1());
        group.g1 = pairing.getG1().newElementFromBytes(g1Byte);
        byte[] hByte = decoder.decode(groupInfoEntity.getH());
        group.h = pairing.getG1().newElementFromBytes(hByte);
        byte[] uByte = decoder.decode(groupInfoEntity.getU());
        group.u = pairing.getG1().newElementFromBytes(uByte);
        byte[] vByte = decoder.decode(groupInfoEntity.getV());
        group.v = pairing.getG1().newElementFromBytes(vByte);
        byte[] g2Byte = decoder.decode(groupInfoEntity.getG2());
        group.g2 = pairing.getG2().newElementFromBytes(g2Byte);
        byte[] wByte = decoder.decode(groupInfoEntity.getW());
        group.w = pairing.getG2().newElementFromBytes(wByte);
        byte[] ehwByte = decoder.decode(groupInfoEntity.getEhw());
        group.ehw = pairing.getGT().newElementFromBytes(ehwByte);
        byte[] ehg2Byte = decoder.decode(groupInfoEntity.getEhg2());
        group.ehg2 = pairing.getGT().newElementFromBytes(ehg2Byte);

        group.pairing = pairing;

        PrivateKey privateKey = new PrivateKey();
        privateKey.Group = group;
        byte[] gammaByte = decoder.decode(groupInfoEntity.getGmma());
        privateKey.gamma = pairing.getZr().newElementFromBytes(gammaByte);
        byte[] xi1Byte = decoder.decode(groupInfoEntity.getXi1());
        privateKey.xi1 = pairing.getZr().newElementFromBytes(xi1Byte);
        byte[] xi2Byte = decoder.decode(groupInfoEntity.getXi2());
        privateKey.xi2 = pairing.getZr().newElementFromBytes(xi2Byte);
        return privateKey;
    }

    /**
     * 根据群成员信息获取对应证书
     * @author maochaowu
     * @date 2023/4/26 9:18
     * @param privateKey        群组私钥
     * @param memberInfoEntity  群成员信息
     * @return com.cora.block.bbs04.Cert
     */
    private Cert compMemberCert(PrivateKey privateKey, MemberInfoEntity memberInfoEntity) {
        Base64.Decoder decoder = Base64.getDecoder();
        Cert cert = new Cert();
        cert.Group = privateKey.Group;
        Pairing pairing = privateKey.Group.pairing;
        byte[] a1Byte = decoder.decode(memberInfoEntity.getA1());
        cert.a1 = pairing.getG1().newElementFromBytes(a1Byte);
        byte[] b1Byte = decoder.decode(memberInfoEntity.getB1());
        cert.b1 = pairing.getZr().newElementFromBytes(b1Byte);
        return cert;
    }

    /**
     * 组装双曲线算法参数
     * @author maochaowu
     * @date 2023/4/26 9:16
     * @param pairingParam 参数配置
     * @return it.unisa.dia.gas.plaf.jpbc.pairing.parameters.PropertiesParameters
     */
    private PropertiesParameters compPropertiesParameters(String pairingParam) {
        JSONObject json = new JSONObject(pairingParam);
        PropertiesParameters parameters = new PropertiesParameters();
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            parameters.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        parameters.put("sign1", "1");
        parameters.put("r", "730750818665451621361119245571504901405976559617");
        parameters.put("q", "8780710799663312522437781984754049815806883199414208211028653399266475630880222957078625179422662221423155858769582317459277713367317481324925129998224791");
        parameters.put("h", "12016012264891146079388821366740534204802954401251311822919615131047207289359704531102844802183906537786776");
        return parameters;
    }

    /**
     * 根据群组名称获取群组信息
     * @author maochaowu
     * @date 2023/4/26 9:14
     * @param groupName    群组名称
     * @return com.cora.block.entity.GroupInfoEntity
     */
    private GroupInfoEntity loadByName(String groupName) {
        LambdaQueryWrapper<GroupInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GroupInfoEntity::getGroupName, groupName);
        return getOne(queryWrapper);
    }

    /**
     * 获取签名信息
     * @author maochaowu
     * @date 2023/4/26 11:29
     * @param groupId       群组id
     * @param memberId      群成员id
     * @return com.cora.block.entity.SignInfoEntity
     */
    private SignInfoEntity getSignInfo(Integer groupId, Integer memberId) {
        LambdaQueryWrapper<SignInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SignInfoEntity::getGroupId, groupId);
        queryWrapper.eq(SignInfoEntity::getMemberId, memberId);
        return signInfoRepository.getOne(queryWrapper);
    }

    /**
     * 获取签名信息
     * @author maochaowu
     * @date 2023/4/26 11:29
     * @param groupId       群组id
     * @param memberId      群成员id
     * @return boolean
     */
    private boolean validSignRepeat(Integer groupId, Integer memberId) {
        LambdaQueryWrapper<SignInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SignInfoEntity::getGroupId, groupId);
        queryWrapper.eq(SignInfoEntity::getMemberId, memberId);
        return signInfoRepository.count(queryWrapper) > 0;
    }

    /**
     * 根据群成员名称获取群成员信息
     * @author maochaowu
     * @date 2023/4/26 9:11
     * @param groupId       群组id
     * @param memberName    群成员名称
     * @return com.cora.block.entity.MemberInfoEntity
     */
    private MemberInfoEntity getByMemberName(Integer groupId, String memberName) {
        LambdaQueryWrapper<MemberInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberInfoEntity::getGroupId, groupId);
        queryWrapper.eq(MemberInfoEntity::getMemberName, memberName);
        return memberInfoRepository.getOne(queryWrapper);
    }

    /**
     * 验证群成员是否已存在
     * @author maochaowu
     * @date 2023/4/26 17:06
     * @param groupId       群组id
     * @param memberName    群成员名称
     * @return boolean
     */
    private boolean validMemberRepeat(Integer groupId, String memberName) {
        LambdaQueryWrapper<MemberInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberInfoEntity::getGroupId, groupId);
        queryWrapper.eq(MemberInfoEntity::getMemberName, memberName);
        return memberInfoRepository.count(queryWrapper) > 0;
    }

    /**
     * 根据群成员证书获取群成员信息
     * @author maochaowu
     * @date 2023/4/26 11:43
     * @param groupId        群组id
     * @param a1             群成员证书
     * @return com.cora.block.entity.MemberInfoEntity
     */
    private MemberInfoEntity getByMemberA1(Integer groupId, String a1) {
        LambdaQueryWrapper<MemberInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberInfoEntity::getGroupId, groupId);
        queryWrapper.eq(MemberInfoEntity::getA1, a1);
        return memberInfoRepository.getOne(queryWrapper);
    }

    /**
     * 校验群组名称是否重复
     * @author maochaowu
     * @date 2023/4/26 9:15
     * @param groupName  群组名称
     * @return boolean
     */
    private boolean validNameRepeat(String groupName) {
        LambdaQueryWrapper<GroupInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GroupInfoEntity::getGroupName, groupName);
        return count(queryWrapper) > 0;
    }
}
