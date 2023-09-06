package com.example.mediumclone.moduls.articleservice.service;

import com.example.mediumclone.baseDto.ResponseDto;
import com.example.mediumclone.projection.ArticleProjection;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdditionService {
    ResponseDto<Void> like(Integer articleId);
    ResponseDto<Page<ArticleProjection>> getAll(Integer userId, Integer limit, Integer offset);

    ResponseDto<Page<ArticleProjection>> feed();

    ResponseEntity<Page<ArticleProjection>> favorite(String favorite);
}
