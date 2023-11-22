package com.checo.bigevent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.checo.bigevent.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    List<Category> getListByUser();
}
