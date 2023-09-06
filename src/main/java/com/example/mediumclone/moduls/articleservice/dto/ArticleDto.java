package com.example.mediumclone.moduls.articleservice.dto;

import com.example.mediumclone.baseDto.BaseDto;
import com.example.mediumclone.moduls.userservice.dto.UsersDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto extends BaseDto {
    private Integer id;
    private String title;
    private String about;
    private String body;
    private UsersDto author;
    private Set<String> tags;
    private Integer likes;

}

