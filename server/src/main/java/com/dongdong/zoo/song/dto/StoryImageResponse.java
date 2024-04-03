package com.dongdong.zoo.song.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoryImageResponse {

    //시작 시간
    private String startTime;
    //동요 가사
    private String content;
    //이미지 경로
    private String imageURL;

}
