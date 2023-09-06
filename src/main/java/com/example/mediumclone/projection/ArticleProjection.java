package com.example.mediumclone.projection;

import java.time.LocalDateTime;
import java.util.Set;

public interface ArticleProjection {
    UserProjection getAuthor();
    String getAbout();
    String getBody();
    String getTitle();
    boolean liked();
    LocalDateTime CreatedAt();
    Set<TagProjection> getTags();

}
