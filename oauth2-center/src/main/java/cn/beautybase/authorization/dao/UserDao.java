package cn.beautybase.authorization.dao;

import cn.beautybase.authorization.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    //自定义写法1
    //@Query("SELECT O FROM UserDO O WHERE O.name = :name1  OR O.name = :name2 ")
    //List<User> findTwoName(@Param("name1") String name1, @Param("name2") String name2);

    //自定义写法2
    //@Query(nativeQuery = true, value = "SELECT * FROM AUTH_USER WHERE name = :name1  OR name = :name2 ")
    //List<User> findSQL(@Param("name1") String name1, @Param("name2") String name2);

    @Query("SELECT O FROM User O WHERE O.username = :username and O.deleted =  :deleted ")
    User getByUsername(@Param("username") String username, @Param("deleted") String deleted);

}
