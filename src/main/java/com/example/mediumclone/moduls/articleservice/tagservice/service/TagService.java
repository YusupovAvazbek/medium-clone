package com.example.mediumclone.moduls.articleservice.tagservice.service;

import com.example.mediumclone.entity.models.Tag;
import com.example.mediumclone.moduls.base.CRUDService;
import org.springframework.http.ResponseEntity;


import java.util.Set;

public interface TagService {
    ResponseEntity<Set<Tag>> add(Set<String> tagDto);
    ResponseEntity<?> delete(Integer id);
    ResponseEntity<?> get(String name);
}
