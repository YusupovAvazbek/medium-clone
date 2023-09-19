package com.example.mediumclone.moduls.articleservice.service;


import com.example.mediumclone.aop.CurrentUser;
import com.example.mediumclone.baseDto.ResponseDto;
import com.example.mediumclone.moduls.articleservice.dto.ArticleDto;
import com.example.mediumclone.moduls.base.CRUDService;
import com.example.mediumclone.moduls.userservice.dto.UsersDto;
import com.example.mediumclone.projection.ArticleProjection;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface ArticleService extends CRUDService<ArticleDto, Long>,AdditionService{
    @Override
    ResponseDto<ArticleDto> create(UsersDto usersDto,ArticleDto dto);

    @Override
    ResponseDto<Void> delete(UsersDto usersDto,Long id);

    @Override
    ResponseDto<ArticleDto> update(UsersDto usersDto,ArticleDto dto);

    @Override
    ResponseDto<ArticleDto> get(UsersDto usersDto,Long id);

    @Override
    ResponseDto<Void> like(UsersDto usersDto,Integer articleId);

    @Override
    ResponseDto<Page<ArticleProjection>> getAll(Integer userId, Integer limit, Integer offset);

    @Override
    ResponseDto<Page<ArticleProjection>> feed(UsersDto usersDto);

    @Override
    ResponseEntity<Page<ArticleProjection>> favorite(String favorite);
}
