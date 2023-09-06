package com.example.mediumclone.entity.models;

import com.example.mediumclone.entity.base.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Entity
@Table(name = "articles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Article extends Auditable {

    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "about", nullable = false)
    private String about;
    @Column(name = "body", nullable = false)
    private String body;
    @ManyToOne
    private User author;
    @ManyToMany(mappedBy = "likes")
    private List<User> likes = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "article_tags",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

}
