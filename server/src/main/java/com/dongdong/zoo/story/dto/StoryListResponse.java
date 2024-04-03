package com.dongdong.zoo.story.dto;

import com.dongdong.zoo.song.dto.SongDetailResponse;
import com.dongdong.zoo.song.model.Lyrics;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoryListResponse {

    private Long storyId;

    private String storyName;

    private Long songId;

    private String songName;

    private List<String> lyricsList;

}
