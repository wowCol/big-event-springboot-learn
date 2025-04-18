package org.finalwork.service.impl;

import org.finalwork.mapper.ArticleMapper;
import org.finalwork.pojo.Article;
import org.finalwork.pojo.Category;
import org.finalwork.pojo.PageBean;
import org.finalwork.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.finalwork.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId);

        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, String categoryId, String state) {
        // 1.创建PageBean对象
        PageBean<Article> pb = new PageBean<Article>();

        // 2.开启分页查询
        PageHelper.startPage(pageNum, pageSize);

        // 3.调用Mapper完成查询
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> as = articleMapper.list(categoryId, state, userId);
        // Page中提供了方法，可以提高PageHelper分页查询后得到的总记录多数和当前页数据
        // Page是List的一个子类
        Page<Article> p = (Page<Article>) as;

        // 把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public Article detail(Integer id) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        return articleMapper.detail(id, userId);
    }

    @Override
    public void update(Article article) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        article.setCreateUser(userId);
        article.setUpdateTime(LocalDateTime.now());

        articleMapper.update(article);
    }

    @Override
    public void delete(Integer id) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        articleMapper.delete(id, userId);
    }
}
