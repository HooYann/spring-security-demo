package com.example.demo.oauth2.authorizationserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.oauth2.authorizationserver.entity.UserInfoEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends BaseMapper<UserInfoEntity> {
}
