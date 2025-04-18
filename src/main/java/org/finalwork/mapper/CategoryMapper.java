package org.finalwork.mapper;

import org.apache.ibatis.annotations.*;
import org.finalwork.pojo.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
    // 新增
    @Insert("INSERT INTO category(category_name, category_alias, create_user, create_time, update_time) " +
            "VALUES(#{categoryName}, #{categoryAlias}, #{createUser}, #{createTime}, #{updateTime})")
    void add(Category category);

    @Select("SELECT * FROM category where create_user=#{id}")
    List<Category> list(Integer id);

    @Select("SELECT * FROM category where id=#{id}")
    Category findById(Integer id);

    @Update("UPDATE category set category_name=#{categoryName},category_alias=#{categoryAlias}," +
            "update_time=#{updateTime} where id=#{id}")
    void update(Category category);

    @Delete("DELETE FROM category where id=#{id}")
    void delete(Integer id);
}
