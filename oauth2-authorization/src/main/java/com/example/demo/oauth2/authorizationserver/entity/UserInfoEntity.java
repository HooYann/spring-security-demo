package com.example.demo.oauth2.authorizationserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@TableName("adi_sys_user")
@Data
@Accessors(chain = true)
public class UserInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
    private Long id;
    /**
     * 登录账号
     */
    @NotNull(message = "用户名不能为空")
    @Length(max = 20,message = "用户名长度必须在{max}个字符内")
    private String username;
    /**
     * 名称
     */
    @NotNull(message = "姓名不能为空")
    @Length(max = 20,message = "姓名长度必须为{max}个字符内")
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 密码盐
     */
    private String salt;
    /**
     * 电话号码
     */
    @TableField("phone_number")
    private String phoneNumber;
    /**
     * 电子邮箱
     */
    @Email(message = "邮箱格式错误")
    private String email;
    /**
     * 状态:  Y-可用  N-不可用
     */
    private String status;

    /**
     * 性别{F:女,M:男}
     */
    private String sex;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 删除状态: Y-已删除,N-未删除
     */
    private String deleted;
    private String avatar;
}
