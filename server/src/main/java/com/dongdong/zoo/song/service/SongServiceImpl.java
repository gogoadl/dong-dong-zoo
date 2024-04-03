package com.dongdong.zoo.song.service;

import com.dongdong.zoo.song.dto.SongDetailResponse;
import com.dongdong.zoo.song.dto.SongLikeCountResponse;
import com.dongdong.zoo.song.dto.SongListResponse;
import com.dongdong.zoo.song.dto.StoryImageResponse;
import com.dongdong.zoo.song.model.LyricsKeyword;
import com.dongdong.zoo.song.model.Song;
import com.dongdong.zoo.song.model.Lyrics;
import com.dongdong.zoo.song.model.SongKeyword;
import com.dongdong.zoo.song.repository.LyricsRepository;
import com.dongdong.zoo.song.repository.SongRepository;
import com.dongdong.zoo.user.repository.UserRepository;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements SongService{

    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final LyricsRepository lyricsRepository;

    public SongServiceImpl(SongRepository songRepository, UserRepository userRepository, LyricsRepository lyricsRepository) {
        this.songRepository = songRepository;
        this.userRepository = userRepository;
        this.lyricsRepository = lyricsRepository;
    }


    // 좋아요, 조회수 내림차순으로 인기 동요 조회
    @Override
    @Transactional
    public List<SongListResponse> getSongsByLikesAndViews() {
        return songRepository.findSongsByLikesAndViews();

//        return songRepository.findSongsByLikesAndViews().stream().map(this::getSongListResponse).collect(Collectors.toList());
    }

    // 동요 상세 조회
    @Override
    @Transactional
    public SongDetailResponse getSongBySongId(Long songId){
        Song song = songRepository.findSongBySongId(songId);
        return getSongResponse(song);
    }

    @Override
    public Song findById(Long songId){
        return songRepository.findSongBySongId(songId);
    }


    @Override
    public void viewCountUp(Long songId){

        Song song = findById(songId);
        song.viewCountUp(song);
    }

    @Override
    public List<StoryImageResponse> getStoryImages(Long songId, String tag){
        //songId를 통해 lyrics,-> lyrics keyword로 프로픔트 가져오기
        List<Lyrics> lyricsList = lyricsRepository.findBySongSongId(songId);
        List<StoryImageResponse> imageList = new ArrayList<>();

        for(Lyrics lyrics : lyricsList){
            StringBuilder prompt = new StringBuilder();
            for(LyricsKeyword keyword : lyrics.getLyricsKeywordList()){
                prompt.append(keyword.getKeyword()).append(", ");
            }
            //이미지 생성 요청 보내기;
            String url = "";
            try{
                url = SongCreateStory.createStory(prompt.toString(), tag);
            }catch (JSONException e){
                throw new JSONException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            StoryImageResponse response =
                StoryImageResponse.builder()
                    .content(lyrics.getContent())
                    .imageURL(url)
                    .startTime(lyrics.getStartTime())
                    .build();

            imageList.add(response);
        }

        return imageList;
    }

    private SongListResponse getSongListResponse(Song song) {

        return SongListResponse.builder()
                .songId(song.getSongId())
                .songName(song.getSongName())
                .songImageUrl(song.getSongImageUrl())
                .views(song.getViews())
                .build();
    }

    private SongDetailResponse getSongResponse(Song song){
        List<String> lyricsList = song.getLyricsList().stream()
                .map(Lyrics::getContent)
                .collect(Collectors.toList());

        SongLikeCountResponse likeCount = songRepository.findSongLikeCountBySongId(song.getSongId());

        List<String> songKeywordList = song.getSongKeywordList().stream()
                .map(SongKeyword::getSongKeyword)
                .collect(Collectors.toList());

        List<String> songKeywordKoreanList = song.getSongKeywordList().stream()
                .map(SongKeyword::getSongKeywordKorean)
                .collect(Collectors.toList());

        return SongDetailResponse.builder()
                .songId(song.getSongId())
                .songName(song.getSongName())
                .songImageUrl(song.getSongImageUrl())
                .songFileUrl(song.getSongFileUrl())
                .beat(song.getBeat())
                .views(song.getViews())
                .lyricsList(lyricsList)
                .LikeCount(likeCount.getLikeCount())
                .songKeywordList(songKeywordList)
                .songKeywordKoreanList(songKeywordKoreanList)
                .build();
    }
}
