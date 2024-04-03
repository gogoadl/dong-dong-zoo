package com.dongdong.zoo.story.service;

import com.dongdong.zoo.song.model.Lyrics;
import com.dongdong.zoo.story.dto.StoryDetailResponse;
import com.dongdong.zoo.story.dto.StoryListResponse;
import com.dongdong.zoo.story.model.Story;
import com.dongdong.zoo.story.model.StoryImage;
import com.dongdong.zoo.story.repository.StoryRepository;
import com.dongdong.zoo.user.repository.UserRepository;
import org.hibernate.sql.ast.tree.expression.Over;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoryServiceImpl implements StoryService{

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;

    public StoryServiceImpl(StoryRepository storyRepository, UserRepository userRepository) {
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<StoryListResponse> getStoryList() {
        return storyRepository.findAll().stream().map(this::getStoryListResponse).collect(Collectors.toList());
    }

    private StoryListResponse getStoryListResponse(Story story) {
        List<String> lyricsList = story.getSong().getLyricsList().stream()
                .map(Lyrics::getContent)
                .collect(Collectors.toList());

        return StoryListResponse.builder()
                .storyId(story.getStoryId())
                .storyName(story.getStoryName())
                .songId(story.getSong().getSongId())
                .songName(story.getSong().getSongName())
                .lyricsList(lyricsList)
                .build();
    }

    @Override
    public StoryDetailResponse getStoryDetail(Long storyId) {
        Story story = storyRepository.findStoryByStoryId(storyId);
        return getStoryDetailResponse(story);
    }

    private StoryDetailResponse getStoryDetailResponse(Story story) {
        List<String> lyricsList = story.getSong().getLyricsList().stream()
                .map(Lyrics::getContent)
                .collect(Collectors.toList());

        List<String> storyImageList = story.getStoryImageList().stream()
                .map(StoryImage::getStoryImageUrl)
                .collect(Collectors.toList());

        return StoryDetailResponse.builder()
                .storyId(story.getStoryId())
                .storyName(story.getStoryName())
                .storyImages(storyImageList)
                .songId(story.getSong().getSongId())
                .songName(story.getSong().getSongName())
                .songFileUrl(story.getSong().getSongFileUrl())
                .lyricsList(lyricsList)
                .build();
    }

    @Override
    public void deleteStory(Long storyId) {
        storyRepository.delete(findById(storyId));
    }

    @Override
    public Story findById(Long storyId) {
        return storyRepository.findStoryByStoryId(storyId);
    }
}
