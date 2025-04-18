package org.finalwork.mapper;

import org.apache.ibatis.annotations.*;
import org.finalwork.pojo.Article;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("INSERT INTO article(id, title, content, cover_img, state," +
            " category_id, create_user, create_time, update_time) " +
            "VALUES(#{id}, #{title}, #{content}, #{coverImg}, #{state}," +
            " #{categoryId}, #{createUser}, #{createTime}, #{updateTime})")
    void add(Article article);

    List<Article> list(String categoryId, String state, Integer userId);

    @Select("SELECT * FROM article WHERE id=#{id} AND create_user=#{userId}")
    Article detail(Integer id, Integer userId);

    @Update("UPDATE article SET title=#{title},content=#{content},cover_img=#{coverImg}, " +
            "state=#{state},category_id=#{categoryId},update_time=#{updateTime} " +
            "WHERE create_user=#{createUser} AND id=#{id}")
    void update(Article article);

    @Delete("DELETE FROM article WHERE id=#{id} AND create_user=#{userId}")
    void delete(Integer id, Integer userId);
}
