package com.dongdong.zoo.song.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongLikeCountResponse {

    private Long songId;

    private Long likeCount;
}
