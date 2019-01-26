package cn.beautybase.authorization.biz.user.dao;

import cn.beautybase.authorization.biz.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    //自定义写法1
    //@Query("select O from UserDO O where O.name = :name1  OR O.name = :name2 ")
    //List<User> findTwoName(@Param("name1") String name1, @Param("name2") String name2);

    //自定义写法2
    //@Query(nativeQuery = true, value = "select * from AUTH_USER where name = :name1  OR name = :name2 ")
    //List<User> findSQL(@Param("name1") String name1, @Param("name2") String name2);

    @Query("select O from User O where O.username = :username and O.deleted =  :deleted ")
    User getByUsername(@Param("username") String username, @Param("deleted") String deleted);

    @Query("select O from User O where (O.username = :username or O.phoneNumber = :username) and O.deleted =  :deleted ")
    List<User> listByUsernameOrPhoneNumber(@Param("username") String username, @Param("deleted") String deleted);

    @Query("select O from User O where (O.username = :username or O.email = :username) and O.deleted =  :deleted ")
    List<User> listByUsernameOrEmail(@Param("username") String username, @Param("deleted") String deleted);
}
