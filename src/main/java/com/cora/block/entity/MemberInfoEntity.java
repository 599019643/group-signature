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
 * 群成员信息
 * </p>
 *
 * @author mao chaowu
 * @since 2023-04-25
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("member_info")
public class MemberInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 群组ID
     */
    @TableField("group_id")
    private Integer groupId;

    /**
     * 群成员名称
     */
    @TableField("member_name")
    private String memberName;

    @TableField("a1")
    private String a1;

    @TableField("b1")
    private String b1;

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
