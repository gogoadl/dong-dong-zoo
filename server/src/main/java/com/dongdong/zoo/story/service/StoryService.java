package com.dongdong.zoo.story.service;

import com.dongdong.zoo.story.dto.StoryDetailResponse;
import com.dongdong.zoo.story.dto.StoryListResponse;
import com.dongdong.zoo.story.model.Story;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StoryService {
    List<StoryListResponse> getStoryList();

    StoryDetailResponse getStoryDetail(Long storyId);

    @Transactional
    void deleteStory(Long storyId);

    Story findById(Long storyId);

}
