package com.dongdong.zoo.like.repository;

import com.dongdong.zoo.like.model.StoryLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoryLikeRepository extends LikeRepository<StoryLikes> {
    Optional<StoryLikes> findByStory_StoryIdAndUser_UserId(Long id, Long userId);

    boolean existsStoryLikeByStory_StoryIdAndUser_UserId(Long id, Long userId);
}
