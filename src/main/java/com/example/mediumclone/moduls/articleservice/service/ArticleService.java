package com.example.mediumclone.moduls.articleservice.service;


import com.example.mediumclone.baseDto.ResponseDto;
import com.example.mediumclone.moduls.articleservice.dto.ArticleDto;
import com.example.mediumclone.moduls.base.CRUDService;
import com.example.mediumclone.projection.ArticleProjection;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface ArticleService extends CRUDService<ArticleDto, Long>,AdditionService{
    @Override
    ResponseDto<ArticleDto> create(ArticleDto dto);

    @Override
    ResponseDto<Void> delete(Long id);

    @Override
    ResponseDto<ArticleDto> update(ArticleDto dto);

    @Override
    ResponseDto<ArticleDto> get(Long id);

    @Override
    ResponseDto<Void> like(Integer articleId);

    @Override
    ResponseDto<Page<ArticleProjection>> getAll(Integer userId, Integer limit, Integer offset);

    @Override
    ResponseDto<Page<ArticleProjection>> feed();

    @Override
    ResponseEntity<Page<ArticleProjection>> favorite(String favorite);
}
