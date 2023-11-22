package com.checo.bigevent.controller;

import com.checo.bigevent.common.Result;
import com.checo.bigevent.entity.Category;
import com.checo.bigevent.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result<String> addCategoryByUser(@RequestBody @Validated Category category) {
        categoryService.save(category);
        return Result.success("添加文章分类成功");
    }

    @GetMapping
    public Result<List<Category>> getListByUser() {
        List<Category> categoryList = categoryService.getListByUser();
        return Result.success(categoryList);
    }

    @GetMapping("detail")
    public Result<Category> detail(@RequestParam Integer id) {
        Category category = categoryService.getById(id);
        return Result.success(category);
    }

    @PutMapping
    public Result<String> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return Result.success("修改文章分类信息成功");
    }

    @DeleteMapping
    public Result<String> delete(@RequestParam Integer id) {
        categoryService.removeById(id);
        return Result.success("删除文章分类成功");
    }
}
