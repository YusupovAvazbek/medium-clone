package com.example.mediumclone.entity.models;

import com.example.mediumclone.entity.base.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends Auditable {
    private String fullname;
    private String username;
    private String password;
    private String email;
    private String bio;
    private String role;
    @ManyToMany
    @JoinTable(
            name = "subscriptions",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> follows = new HashSet<>();
    @ManyToMany(mappedBy = "follows")
    private Set<User> followers = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id"))
    private Set<Article> likes = new HashSet<>();
}
