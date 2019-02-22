package com.example.demo.mode2.biz.constants;

/**
 * 用户状态，1：正常，0：禁用，-1锁定
 */
public interface UserStatus {
    /**
     * 1：正常，0：禁用，-1锁定
     */
    String ENABLED = "1";
    String DISABLED = "0";
    String LOCKED = "-1";
}
