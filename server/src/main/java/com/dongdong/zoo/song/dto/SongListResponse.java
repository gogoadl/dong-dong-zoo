package com.dongdong.zoo.song.dto;

import com.dongdong.zoo.song.model.Lyrics;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongListResponse {
    // 동요 pk
    private Long songId;
    // 동요 이름
    private String songName;
    // 동요 썸네일 이미지 경로
    private String songImageUrl;
//    // 동요 음원 파일 경로
//    private String songFileUrl;
//    // 동요 박자 (초 / 가사)
//    private Long beat;
    // 조회수
    private Long views;

    // 좋아요 수
    private Long likeCount;
}
