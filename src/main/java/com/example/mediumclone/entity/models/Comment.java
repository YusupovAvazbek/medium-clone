package com.example.mediumclone.entity.models;


import com.example.mediumclone.entity.base.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment extends Auditable {
    private String description;
    @ManyToOne
    private User users;
    @ManyToOne
    private Article articles;
}
