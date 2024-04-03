package com.dongdong.zoo.story.dto;

import com.dongdong.zoo.story.model.StoryImage;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoryDetailResponse {

    private Long storyId;

    private String storyName;

    private List<String> storyImages;

    private Long songId;

    private String songName;

    private String songFileUrl;

    private List<String> lyricsList;
}
