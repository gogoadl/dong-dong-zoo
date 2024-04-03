package com.dongdong.zoo.song.service;

import com.dongdong.zoo.song.dto.SongDetailResponse;
import com.dongdong.zoo.song.dto.SongListResponse;
import com.dongdong.zoo.song.dto.StoryImageResponse;
import com.dongdong.zoo.song.model.Lyrics;
import com.dongdong.zoo.song.model.Song;

import java.io.IOException;
import java.util.List;
import org.json.JSONException;

public interface SongService {

    // 좋아요, 조회수 내림차순으로 인기 동요 조회
    List<SongListResponse> getSongsByLikesAndViews();

    SongDetailResponse getSongBySongId(Long songId);

    Song findById(Long songId);

    void viewCountUp(Long songId);

    List<StoryImageResponse> getStoryImages(Long songId, String tag);
}
