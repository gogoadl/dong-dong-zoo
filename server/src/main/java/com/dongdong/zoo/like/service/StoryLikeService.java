package com.dongdong.zoo.like.service;

import com.dongdong.zoo.like.dto.LikeRequest;
import com.dongdong.zoo.like.model.StoryLikes;
import com.dongdong.zoo.like.repository.StoryLikeRepository;
import com.dongdong.zoo.story.model.Story;
import com.dongdong.zoo.story.service.StoryService;
import com.dongdong.zoo.user.model.User;
import com.dongdong.zoo.user.repository.UserRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class StoryLikeService extends LikeService<StoryService, StoryLikeRepository> {

    public StoryLikeService(UserRepository userRepository, StoryService storyService, StoryLikeRepository StoryLikeRepository){
        super(userRepository,storyService,StoryLikeRepository);
    }

    @Override
    public void deleteLike(Long id, Long userId){
        likeRepository.findByStory_StoryIdAndUser_UserId(id, userId).ifPresentOrElse(likeRepository::delete, () -> {
            throw new DuplicateKeyException("존재하는 like인지 확인해주세요");
        });
    }

    @Override
    protected Long createLikeObject(LikeRequest likeRequest, User user) {
        if (checkUserLike(likeRequest.getStoryId(), likeRequest.getUserId())) {
            throw new DuplicateKeyException("이미 존재하는 Story_likes 입니다");
        }

        Story story = service.findById(likeRequest.getStoryId());

        StoryLikes storyLikes = StoryLikes.builder()
                .story(story)
                .user(user)
                .build();

        StoryLikes save = likeRepository.save(storyLikes);
        return save.getId();
    }


    @Override
    public boolean checkUserLike(Long id, Long userId){
        return likeRepository.existsStoryLikeByStory_StoryIdAndUser_UserId(id,userId);
    }
}
