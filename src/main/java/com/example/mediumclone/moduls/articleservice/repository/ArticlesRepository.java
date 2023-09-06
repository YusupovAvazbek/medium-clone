package com.example.mediumclone.moduls.articleservice.repository;

import com.example.mediumclone.entity.models.Article;
import com.example.mediumclone.entity.models.Tag;
import com.example.mediumclone.entity.models.User;
import com.example.mediumclone.projection.ArticleProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticlesRepository extends JpaRepository<Article, Integer>, PagingAndSortingRepository<Article, Integer> {

    @Query("SELECT u.follows FROM User u WHERE u.id = :userId")
    List<User> getFollowsByUserId(Integer userId);
//    @Query(value = "SELECT a FROM Articles a WHERE a.author IN (:follows)")
//    List<ArticlesProjection> feed(@Param("follows") List<Users> follows);
    @Query(value = "SELECT " +
        "    p.author_id AS author, " +
        "    p.about AS about, " +
        "    p.title AS title, " +
        "    p.body AS body, " +
        "    CASE WHEN lp.user_id IS NOT NULL THEN true ELSE false END AS liked, " +
        "    CASE WHEN f.id IS NOT NULL THEN true ELSE false END AS following " +
        "FROM articles p " +
        "LEFT JOIN likes lp ON p.id = lp.article_id AND lp.user_id = :userId " +
        "LEFT JOIN subscriptions s ON p.author_id = s.user_id AND s.follower_id = :userId " +
        "LEFT JOIN users f ON p.author_id = f.id " +
        "WHERE p.author_id IN (SELECT follows.id FROM users u JOIN subscriptions s ON u.id = s.follower_id JOIN users follows ON s.user_id = follows.id WHERE u.id = :userId)",
        countQuery = "SELECT COUNT(*) FROM articles p WHERE p.author_id IN (SELECT follows.id FROM users u JOIN subscriptions s ON u.id = s.follower_id JOIN users follows ON s.user_id = follows.id WHERE u.id = :userId)",
        nativeQuery = true)
    List<ArticleProjection> feed(@Param("userId") Integer userId);
    List<Article> findAllByTags(Tag tag);
    @Query("SELECT p.author AS author," +
            " p.about AS about," +
            " p.title AS title," +
            " p.body AS body," +
            " CASE WHEN lp.id IS NOT NULL THEN true ELSE false END AS liked" +
            " FROM Article p" +
            " LEFT JOIN p.likes lp WITH lp.id = :userId")
    Page<ArticleProjection> getAllArticles(@Param("userId") Integer userId, Pageable pageable);
    //Page<ArticlesProjection> findAllProjected(Pageable pageable);
    //findAllProjectedByAuthorId
}
