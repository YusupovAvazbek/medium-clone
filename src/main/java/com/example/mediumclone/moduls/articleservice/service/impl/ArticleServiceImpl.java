package com.example.mediumclone.moduls.articleservice.service.impl;

import com.example.mediumclone.aop.CurrentUser;
import com.example.mediumclone.baseDto.ResponseDto;
import com.example.mediumclone.entity.models.Article;
import com.example.mediumclone.entity.models.Tag;
import com.example.mediumclone.entity.models.User;
import com.example.mediumclone.message.AppStatusCodes;
import com.example.mediumclone.message.AppStatusMessages;
import com.example.mediumclone.moduls.articleservice.dto.ArticleDto;
import com.example.mediumclone.moduls.articleservice.mapper.ArticleMapper;
import com.example.mediumclone.moduls.articleservice.repository.ArticlesRepository;
import com.example.mediumclone.moduls.articleservice.service.ArticleService;
import com.example.mediumclone.moduls.articleservice.tagservice.service.TagService;
import com.example.mediumclone.moduls.userservice.dto.UsersDto;
import com.example.mediumclone.moduls.userservice.mapper.UserMapper;
import com.example.mediumclone.moduls.userservice.repository.UsersRepository;
import com.example.mediumclone.projection.ArticleProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static com.example.mediumclone.message.AppStatusCodes.*;
import static com.example.mediumclone.message.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService{
    private final ArticleMapper articleMapper;
    private final ArticlesRepository articlesRepository;
    private final UsersRepository usersRepository;
    private final TagService tagService;
    private final UserMapper userMapper;
    @Override
    public ResponseDto<ArticleDto> create(@CurrentUser UsersDto currentUser, ArticleDto dto) {
        if (currentUser == null) {
            return ResponseDto.<ArticleDto>builder()
                    .message(AppStatusMessages.NOT_AUTHENTICATED)
                    .code(AppStatusCodes.NOT_AUTHENTICATED)
                    .build();
        }

        if (dto.getAuthor().getId() == currentUser.getId()) {
            ResponseEntity<Set<Tag>> add = tagService.add(dto.getTags());
            Article article = articleMapper.toEntity(dto);
            article.setTags(add.getBody());
            article.setAuthor(userMapper.toEntityPassword(currentUser));
            try {
                Article save = articlesRepository.save(article);
                log.info("article {} added by {}", article.getTitle(),currentUser.getUsername());
                return ResponseDto.<ArticleDto>builder()
                        .code(OK_CODE)
                        .data(articleMapper.toDto(save))
                        .message(OK)
                        .success(true)
                        .build();
            } catch (Exception e) {
                log.error("article add {}", e.getMessage());
                return ResponseDto.<ArticleDto>builder()
                        .success(false)
                        .message(DATABASE_ERROR+": "+e.getMessage())
                        .code(DATABASE_ERROR_CODE)
                        .build();
            }
        } else {
            return ResponseDto.<ArticleDto>builder()
                    .code(HttpStatus.FORBIDDEN.value())
                    .message(HttpStatus.FORBIDDEN.toString())
                    .build();
        }
    }

    @Override
    public ResponseDto<Void> delete(@CurrentUser UsersDto usersDto,Long id) {

        if(usersDto == null){
            return ResponseDto.<Void>builder()
                    .message(AppStatusMessages.NOT_AUTHENTICATED)
                    .code(AppStatusCodes.NOT_AUTHENTICATED)
                    .build();
        }
        Optional<Article> byId = articlesRepository.findById(id.intValue());
        if(byId.isEmpty()){
            log.info("not found id delete article");
            return ResponseDto.<Void>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .success(true)
                    .build();
        }
        if(byId.get().getAuthor().getId() == usersDto.getId()) {
            try {
                articlesRepository.delete(byId.get());
                log.info("articles deleted {} ", id);
                return ResponseDto.<Void>builder()
                        .code(OK_CODE)
                        .message(OK)
                        .success(true)
                        .build();
            } catch (Exception e) {
                return ResponseDto.<Void>builder()
                        .success(false)
                        .message(DATABASE_ERROR+": "+e.getMessage())
                        .code(DATABASE_ERROR_CODE)
                        .build();
            }
        }else {
            return ResponseDto.<Void>builder()
                    .code(HttpStatus.FORBIDDEN.value())
                    .message(HttpStatus.FORBIDDEN.toString())
                    .build();
        }
    }

    @Override
    public ResponseDto<ArticleDto> update(@CurrentUser UsersDto usersDto,ArticleDto dto) {
        if(usersDto == null){
            return ResponseDto.<ArticleDto>builder()
                    .message(AppStatusMessages.NOT_AUTHENTICATED)
                    .code(AppStatusCodes.NOT_AUTHENTICATED)
                    .build();
        }
        if(usersDto.getId() != dto.getAuthor().getId()){
            return ResponseDto.<ArticleDto>builder()
                    .code(HttpStatus.FORBIDDEN.value())
                    .message(HttpStatus.FORBIDDEN.toString())
                    .build();
        }
        if(dto.getId() == null){
            return ResponseDto.<ArticleDto>builder()
                    .code(NULL_VALUE_CODE)
                    .message(NULL_VALUE)
                    .build();
        }
        Optional<Article> byId = articlesRepository.findById(dto.getId().intValue());
        if(byId.isEmpty()){
            return ResponseDto.<ArticleDto>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        }
        try {
            Article uparticles = byId.get();
            if(dto.getTitle() != null){
                uparticles.setTitle(dto.getTitle());
            } if(dto.getAbout() != null){
                uparticles.setAbout(dto.getAbout());
            } if(dto.getTags() != null){
                ResponseEntity<Set<Tag>> add = tagService.add(dto.getTags());
                uparticles.setTags(add.getBody());
            }
            articlesRepository.save(uparticles);
            return ResponseDto.<ArticleDto>builder()
                    .code(OK_CODE)
                    .message(OK)
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<ArticleDto>builder()
                    .success(false)
                    .message(DATABASE_ERROR+": "+e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }

    @Override
    public ResponseDto<ArticleDto> get(UsersDto usersDto,Long id) {
        if(usersDto == null){
            return ResponseDto.<ArticleDto>builder()
                    .message(AppStatusMessages.NOT_AUTHENTICATED)
                    .code(AppStatusCodes.NOT_AUTHENTICATED)
                    .build();
        }
        if(id == null){
            return ResponseDto.<ArticleDto>builder()
                    .code(NULL_VALUE_CODE)
                    .message(NULL_VALUE)
                    .build();
        }
        try {
        Optional<Article> byId = articlesRepository.findById(id.intValue());
        if(byId.isEmpty()){
            return ResponseDto.<ArticleDto>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        }

            return ResponseDto.<ArticleDto>builder()
                    .code(OK_CODE)
                    .message(OK)
                    .data(articleMapper.toDto(byId.get()))
                    .success(true)
                    .build();
        }catch (Exception e){
            return ResponseDto.<ArticleDto>builder()
                    .success(false)
                    .message(DATABASE_ERROR+": "+e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }

    @Override
    public ResponseDto<Void> like(@CurrentUser UsersDto usersDto,Integer articleId) {
        if(usersDto == null){
            return ResponseDto.<Void>builder()
                    .message(AppStatusMessages.NOT_AUTHENTICATED)
                    .code(AppStatusCodes.NOT_AUTHENTICATED)
                    .build();
        }if(articleId == null){
            return ResponseDto.<Void>builder()
                    .code(NULL_VALUE_CODE)
                    .message(NULL_VALUE)
                    .build();
        }
        try {
            Optional<Article> article = articlesRepository.findById(articleId);
            if (article.isPresent()) {
                User user = userMapper.toEntityPassword(usersDto);
                Article articles = article.get();
                if (articles.getLikes().contains(user)) {
                    user.getLikes().remove(articles);
                } else {
                    user.getLikes().add(articles);
                }
                usersRepository.save(user);
                return ResponseDto.<Void>builder()
                        .code(OK_CODE)
                        .message(OK)
                        .success(true)
                        .build();

            } else {
                return ResponseDto.<Void>builder()
                        .code(NOT_FOUND_ERROR_CODE)
                        .message(NOT_FOUND)
                        .build();

        }
        }catch (Exception e){
            return ResponseDto.<Void>builder()
                    .success(false)
                    .message(DATABASE_ERROR+": "+e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }

    @Override
    public ResponseDto<Page<ArticleProjection>> getAll(Integer userId, Integer limit, Integer offset) {
        return null;
    }

    @Override
    public ResponseDto<Page<ArticleProjection>> feed(@CurrentUser UsersDto usersDto) {
        return null;
    }

    @Override
    public ResponseEntity<Page<ArticleProjection>> favorite(String favorite) {
        return null;
    }
}
