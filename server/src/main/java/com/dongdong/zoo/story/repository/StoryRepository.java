package com.dongdong.zoo.story.repository;

import com.dongdong.zoo.story.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
    Story findStoryByStoryId(Long storyId);
}
