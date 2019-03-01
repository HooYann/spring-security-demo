package cn.beautybase.authorization.biz.user.dao;

import cn.beautybase.authorization.biz.user.entity.UserSocialData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSocialDataDao extends JpaRepository<UserSocialData, Long> {



}
