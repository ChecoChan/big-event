package com.checo.bigevent.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.checo.bigevent.entity.Article;
import com.checo.bigevent.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
}
