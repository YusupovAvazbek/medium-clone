package com.example.mediumclone.moduls.articleservice.tagservice.repository;

import com.example.mediumclone.entity.models.Tag;
import com.example.mediumclone.projection.TagProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> findByName(String name);
    @Query(value = "SELECT t.id, t.name, COUNT(*)\n" +
            "FROM tag AS t\n" +
            "JOIN articles_tags AS at ON t.id = at.tags_id\n" +
            "GROUP BY t.id, t.name\n" +
            "ORDER BY count DESC\n" +
            "LIMIT 10;", nativeQuery = true)
    List<TagProjection> getPopularTags();
}
