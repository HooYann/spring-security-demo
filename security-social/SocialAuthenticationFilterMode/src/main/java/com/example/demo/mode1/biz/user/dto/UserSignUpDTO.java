package com.example.demo.mode1.biz.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserSignUpDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String phoneNumber;

}
