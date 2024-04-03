package com.dongdong.zoo.like.controller;

import com.dongdong.zoo.like.dto.LikeRequest;
import com.dongdong.zoo.like.service.StoryLikeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/likes/story")
@CrossOrigin(origins = "*",allowedHeaders = "*")
@Tag(name = "StoryLike", description = "StoryLike API")
public class StoryLikeController extends LikeController<StoryLikeService>{

    public StoryLikeController(StoryLikeService storyLikeService) {
        super(storyLikeService);
    }

    /*
    좋아요 생성
    likeRequest는 boardId와 userId를 받음
     */
    @Override
    public ResponseEntity<URI> createLike(@Valid @RequestBody LikeRequest likeRequest) {
        if (likeRequest.checkStoryId(likeRequest)) {
            likeService.createLike(likeRequest);
        }
        // 뭘 돌려줄까?
        return ResponseEntity.ok().build();
    }

}
