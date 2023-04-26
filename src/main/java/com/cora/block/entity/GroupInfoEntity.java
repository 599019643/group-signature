    package com.cora.block.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 群信息表
 * </p>
 *
 * @author mao chaowu
 * @since 2023-04-25
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("group_info")
public class GroupInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 群名称
     */
    @TableField("group_name")
    private String groupName;

    /**
     * 群描述
     */
    @TableField("group_desc")
    private String groupDesc;

    @TableField("gmma")
    private String gmma;

    @TableField("xi1")
    private String xi1;

    @TableField("xi2")
    private String xi2;

    @TableField("g1")
    private String g1;

    @TableField("h")
    private String h;

    @TableField("u")
    private String u;

    @TableField("v")
    private String v;

    @TableField("g2")
    private String g2;

    @TableField("w")
    private String w;

    @TableField("ehw")
    private String ehw;

    @TableField("ehg2")
    private String ehg2;

    @TableField("pairing_param")
    private String pairingParam;

    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField("gmt_modify")
    private LocalDateTime gmtModify;


}
