package cn.beautybase.authorization.biz.user.dao;

import cn.beautybase.authorization.biz.user.entity.UserSocial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSocialDao extends JpaRepository<UserSocial, Long> {

    @Query("delete from UserSocial O where O.providerId = :providerId and O.providerUserId = :providerUserId")
    void delete(@Param("providerId") String providerId, @Param("providerUserId") String providerUserId);

    @Query("select O from UserSocial O where O.providerId = :providerId and O.providerUserId = :providerUserId")
    UserSocial get(@Param("providerId") String providerId, @Param("providerUserId") String providerUserId);

    @Query("select O from UserSocial O where O.providerId = :providerId and O.userId = :userId")
    UserSocial getByProviderIdAndUserId(@Param("providerId") String providerId, @Param("userId") Long userId);

    @Query("select O from UserSocial O where O.userId = :userId")
    List<UserSocial> listByUserId(@Param("userId") Long userId);

}
