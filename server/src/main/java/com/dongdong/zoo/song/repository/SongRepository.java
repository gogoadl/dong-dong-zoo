package com.dongdong.zoo.song.repository;

import com.dongdong.zoo.song.dto.SongDetailResponse;
import com.dongdong.zoo.song.dto.SongLikeCountResponse;
import com.dongdong.zoo.song.dto.SongListResponse;
import com.dongdong.zoo.song.model.Song;
import com.dongdong.zoo.user.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    // 인기 동요 조회
    // 좋아요, 조회수 내림차순 DESC 정렬
    @Query("SELECT new com.dongdong.zoo.song.dto.SongListResponse(s.songId, s.songName, s.songImageUrl, s.views, count(sl.id) as likeCount) " +
            "FROM Song s LEFT JOIN SongLikes sl ON s.songId = sl.song.songId " +
            "GROUP BY s.songId, s.songName, s.songImageUrl, s.songFileUrl, s.views, s.beat " +
            "ORDER BY s.views DESC, likeCount DESC")
    List<SongListResponse> findSongsByLikesAndViews();

    @Query("SELECT s FROM Song s WHERE s.songId = :songId")
    Song findSongBySongId(Long songId);

    @Query("SELECT new com.dongdong.zoo.song.dto.SongLikeCountResponse(s.songId, count(sl.id) as likeCount) " +
            "FROM Song s LEFT JOIN SongLikes sl ON s.songId = sl.song.songId " +
            "WHERE s.songId = :songId " +
            "GROUP BY s.songId ")
    SongLikeCountResponse findSongLikeCountBySongId(Long songId);

    // @Query(
    //     "select new com.dongdong.zoo.song.dto.SongListResponse(ns.songId, ns.songName, ns.songImageUrl, ns.views, count(nsl.id) as likeCount) "
    //         + "from "
    //             + "(select s "
    //             + "from Song s "
    //             + "left join SongLikes sl on s.songId = sl.song.songId "
    //             + "where sl.user = :user) ns"
    //             + "left join SongLikes nsl on ns.song.songId = nsl.song.songId "
    //         + "group by ns.songId "
    //         + "order by ns.views desc, likeCount desc ")
    @Query(value = "select s2.song_id as songId , s2.song_name as songName, s2.song_image_url as songImageUrl, s2.views, count(sl2.song_likes_id) as likeCount "
        + "from "
        + "(select s.song_id, s.song_name, s.song_image_url, s.views "
        + "from song s "
        + "left join song_likes sl on s.song_id = sl.song_id "
        + "where sl.user_id = :userId) s2 "
        + "left join song_likes sl2 on s2.song_id = sl2.song_id "
        + "group by s2.song_id "
        + "order by s2.views desc, likeCount desc;", nativeQuery = true)
    List<SongListResponseResult> findSongsByLikes(@Param("userId") Long userId);

    interface SongListResponseResult {
        Long getSongId();
        String getSongName();
        String getSongImageUrl();
        Long getViews();
        Long getLikeCount();
    }
}
