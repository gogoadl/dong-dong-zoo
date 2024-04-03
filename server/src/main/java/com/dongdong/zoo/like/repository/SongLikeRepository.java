package com.dongdong.zoo.like.repository;

import com.dongdong.zoo.like.model.SongLikes;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SongLikeRepository extends LikeRepository<SongLikes> {

    Optional<SongLikes> findBySong_SongIdAndUser_UserId(Long songId, Long userId);
    boolean existsSongLikeBySong_SongIdAndUser_UserId(@Param("boardId") Long boardId, @Param("userId") Long userId);
}
