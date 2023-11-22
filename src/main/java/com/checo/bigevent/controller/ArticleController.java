package com.checo.bigevent.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.checo.bigevent.common.Result;
import com.checo.bigevent.entity.Article;
import com.checo.bigevent.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping
    public Result<Page<Article>> list(Integer pageNum,
                                      Integer pageSize,
                                      @RequestParam(required = false) String categoryId,
                                      @RequestParam(required = false) String state) {
        Page<Article> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasLength(categoryId), Article::getCategoryId, categoryId);
        queryWrapper.eq(StringUtils.hasLength(state), Article::getState, state);
        Page<Article> articlePage = articleService.page(page, queryWrapper);
        return Result.success(articlePage);
    }

    @PostMapping
    public Result<String> add(@RequestBody @Validated Article article) {
        articleService.save(article);
        return Result.success("添加文章成功");
    }

    @GetMapping("detail")
    public Result<Article> detail(@RequestParam Integer id) {
        Article article = articleService.getById(id);
        return Result.success(article);
    }

    @PutMapping("update")
    public Result<String> update(@RequestBody Article article) {
        articleService.updateById(article);
        return Result.success("更新文章成功");
    }

    @DeleteMapping
    public Result<String> delete(@RequestParam Integer id) {
        articleService.removeById(id);
        return Result.success("删除文章成功");
    }
}
