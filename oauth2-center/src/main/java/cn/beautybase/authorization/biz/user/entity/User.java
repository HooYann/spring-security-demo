package cn.beautybase.authorization.biz.user.entity;

import cn.beautybase.authorization.biz.user.constants.UserStatus;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@Table(name = "z_user")
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 登录账号
     */
    @NotNull(message = "用户名不能为空")
    @Column(length = 20)
    private String username;
    /**
     * 昵称
     */
    @Column
    private String nickname;
    /**
     * 真实姓名
     */
    //@NotNull(message = "姓名不能为空")
    //@Length(max = 20,message = "姓名长度必须为{max}个字符内")
    @Column
    private String name;
    /**
     * 性别{F:女,M:男,U:未知}
     */
    @Column
    private String sex;
    /**
     * 密码
     */
    @Column
    private String password;
    /**
     * 密码盐
     */
    //private String salt;
    /**
     * 电话号码
     */
    @Column(name = "phone_number")
    private String phoneNumber;
    /**
     * 电子邮箱
     */
    @Email(message = "邮箱格式错误")
    @Column
    private String email;
    /**
     * 头像
     */
    @Column
    private String avatar;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;
    /**
     * 用户状态，1：正常，0：禁用，-1锁定
     */
    @Column
    private String status;
    /**
     * 删除状态: Y-已删除,N-未删除
     */
    @Column
    private String deleted;


    /**    UserDetails  */

    @Transient
    private boolean signUp;
    public boolean getSignUp() {
        return this.signUp;
    }
    public void setSignUp(boolean signUp) {
        this.signUp = signUp;
    }


    @Transient
    private Collection<GrantedAuthority> authorities = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities.size() == 0) {
            authorities.add(new SimpleGrantedAuthority("user"));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if(UserStatus.LOCKED.equals(this.status)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if(UserStatus.ENABLED.equals(this.status)) {
            return true;
        }
        return false;
    }
}
