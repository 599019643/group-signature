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
 * 签名信息
 * </p>
 *
 * @author mao chaowu
 * @since 2023-04-26
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sign_info")
public class SignInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 群组id
     */
    @TableField("group_id")
    private Integer groupId;

    /**
     * 群组用户id
     */
    @TableField("member_id")
    private Integer memberId;

    /**
     * 加签原数据
     */
    @TableField("message")
    private String message;

    @TableField("t1")
    private String t1;

    @TableField("t2")
    private String t2;

    @TableField("t3")
    private String t3;

    @TableField("c")
    private String c;

    @TableField("salpha")
    private String salpha;

    @TableField("sbeta")
    private String sbeta;

    @TableField("sa")
    private String sa;

    @TableField("sdelta1")
    private String sdelta1;

    @TableField("sdelta2")
    private String sdelta2;

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
