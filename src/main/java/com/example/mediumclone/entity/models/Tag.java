package com.example.mediumclone.entity.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tags")
public class Tag {
    @Id
    private int id;
    private String name;
    @ManyToMany(mappedBy = "tags")
    private Set<Article> articlesList = new HashSet<>();
    public Tag(String name){
        this.name = name;
    }
}
