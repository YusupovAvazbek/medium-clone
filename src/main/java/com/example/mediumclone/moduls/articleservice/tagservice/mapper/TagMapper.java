package com.example.mediumclone.moduls.articleservice.tagservice.mapper;

import com.example.mediumclone.entity.models.Tag;
import org.springframework.stereotype.Component;


import java.util.Set;
import java.util.stream.Collectors;

@Component
//@Mapper(componentModel = "spring")
public class TagMapper {
    Set<String> toDto(Set<Tag> tags){
        return tags.stream().map(tag -> tag.getName()).collect(Collectors.toSet());
    }
    Set<Tag> toEntity(Set<String> stringTag){
        return stringTag.stream().map(st->new Tag(st)).collect(Collectors.toSet());
    }
}
