package com.example.mediumclone.moduls.articleservice.mapper;

import com.example.mediumclone.entity.models.Article;
import com.example.mediumclone.moduls.articleservice.dto.ArticleDto;
import com.example.mediumclone.moduls.articleservice.tagservice.mapper.TagMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ArticleMapper {
    @Autowired
    protected TagMapper tagMapper;
    @Mapping(target = "likes",expression = "java((articles.getLikes() == null) ? 0 : articles.getLikes().size())")
    @Mapping(target = "tags", expression = "java(tagMapper.toDto(articles.getTags()))")
    public abstract ArticleDto toDto(Article article);
    @Mapping(target = "likes",ignore = true)
    @Mapping(target = "tags", expression = "java(tagMapper.toEntity(articlesDto.getTags()))")
    public abstract Article toEntity(ArticleDto articleDto);

}
