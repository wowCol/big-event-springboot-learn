package org.finalwork.mapper;

import org.finalwork.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    // 根据用户名查询用户
    @Select("SELECT  * FROM user where username=#{username}")
    User findByUserName(String username);

    @Insert("INSERT INTO user(username, password, create_time, update_time)" +
            " VALUES (#{username}, #{password},  now(), now())")
    // 添加
    void add(String username, String password);

    @Update("UPDATE user set nickname=#{nickname}, email=#{email}, update_time=#{updateTime} where id=#{id}")
    void update(User user);

    @Update("UPDATE user set user_pic=#{avatarUrl}, update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl, Integer id);

    @Update("UPDATE user set password=#{md5String}, update_time=now() where id=#{id}")
    void updatePwd(String md5String, Integer id);
}
