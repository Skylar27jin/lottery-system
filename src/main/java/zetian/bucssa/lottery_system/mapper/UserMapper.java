package zetian.bucssa.lottery_system.mapper;

import org.apache.ibatis.annotations.*;
import zetian.bucssa.lottery_system.domain.Lottery;
import zetian.bucssa.lottery_system.domain.User;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO bucssa_lottery_users(bu_id, name, email, phone, created_at) VALUES(#{buId}, #{name}, #{email}, #{phone}, #{created_at})")
    void insertUser(User user);

    @Insert({
            "<script>",
            "INSERT INTO bucssa_lottery_users (bu_id, name, email, phone) VALUES ",
            "<foreach collection='users' item='user' separator=','>",
            "(#{user.buId}, #{user.name}, #{user.email}, #{user.phone})",
            "</foreach>",
            "</script>"
    })
    void insertManyUsers(@Param("users") List<User> users);

    @Select("SELECT * FROM bucssa_lottery_users WHERE bu_id = #{buId}")
    @Results(id = "user", value={
            @Result(property = "buId", column = "bu_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email"),
            @Result(property = "phone", column = "phone")
    })
    User getUserByBuId(String buId);


    @Select("SELECT * FROM bucssa_lottery_users")
    @ResultMap("user")
    List<User> getAllUsers();
}
