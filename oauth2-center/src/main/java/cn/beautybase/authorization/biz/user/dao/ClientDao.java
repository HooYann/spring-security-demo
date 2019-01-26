package cn.beautybase.authorization.biz.user.dao;

import cn.beautybase.authorization.biz.user.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDao extends JpaRepository<Client, Long> {
    @Query("select O from Client O where O.clientId = :clientId")
    Client getByClientId(@Param("clientId") String clientId);
}
