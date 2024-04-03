package com.dongdong.zoo.like.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LikeRequest {

    @NotNull
    Long userId;

    Long songId;

    Long storyId;

    @AssertTrue
    public boolean checkSongId(LikeRequest likeRequest) {return likeRequest.songId != null;}
    @AssertTrue
    public boolean checkStoryId(LikeRequest likeRequest) {return likeRequest.storyId != null;}
}
