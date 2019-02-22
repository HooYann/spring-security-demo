package com.example.demo.mode1.biz.user.dao;

import com.example.demo.mode1.biz.user.entity.UserSocial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSocialDao extends JpaRepository<UserSocial, Long> {

    @Query("select O from UserSocial O where O.providerId = :providerId and O.providerUserId = :providerUserId")
    List<UserSocial> listByProviderIdAndProviderUserId(@Param("providerId") String providerId, @Param("providerUserId") String providerUserId);

    @Query("delete from UserSocial O where O.providerId = :providerId and O.providerUserId = :providerUserId")
    void deleteByProviderIdAndProviderUserId(@Param("providerId") String providerId, @Param("providerUserId") String providerUserId);
}
