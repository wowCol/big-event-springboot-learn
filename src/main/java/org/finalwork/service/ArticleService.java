package org.finalwork.service;

import org.finalwork.pojo.Article;
import org.finalwork.pojo.Category;
import org.finalwork.pojo.PageBean;


public interface ArticleService {
    // 新增文章
    void add(Article article);

    // 条件分页列表查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, String categoryId, String state);

    // 根据id查询文章详细内容
    Article detail(Integer id);

    // 更新文章内容
    void update(Article article);

    // 删除文章
    void delete(Integer id);
}
