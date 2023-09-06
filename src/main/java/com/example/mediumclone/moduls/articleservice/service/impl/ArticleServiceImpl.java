package com.example.mediumclone.moduls.articleservice.service.impl;

import com.example.mediumclone.baseDto.ResponseDto;
import com.example.mediumclone.moduls.articleservice.dto.ArticleDto;
import com.example.mediumclone.moduls.articleservice.mapper.ArticleMapper;
import com.example.mediumclone.moduls.articleservice.repository.ArticlesRepository;
import com.example.mediumclone.moduls.articleservice.service.AdditionService;
import com.example.mediumclone.moduls.articleservice.service.ArticleService;
import com.example.mediumclone.projection.ArticleProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService{
    private final ArticleMapper articleMapper;
    private final ArticlesRepository articlesRepository;
    @Override
    public ResponseDto<ArticleDto> create(ArticleDto dto) {
        return null;
    }

    @Override
    public ResponseDto<Void> delete(Long id) {
        return null;
    }

    @Override
    public ResponseDto<ArticleDto> update(ArticleDto dto) {
        return null;
    }

    @Override
    public ResponseDto<ArticleDto> get(Long id) {
        return null;
    }

    @Override
    public ResponseDto<Void> like(Integer articleId) {
        return null;
    }

    @Override
    public ResponseDto<Page<ArticleProjection>> getAll(Integer userId, Integer limit, Integer offset) {
        return null;
    }

    @Override
    public ResponseDto<Page<ArticleProjection>> feed() {
        return null;
    }

    @Override
    public ResponseEntity<Page<ArticleProjection>> favorite(String favorite) {
        return null;
    }
}
