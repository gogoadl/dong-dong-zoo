package com.dongdong.zoo.like.service;

import com.dongdong.zoo.like.dto.LikeRequest;
import com.dongdong.zoo.like.model.SongLikes;
import com.dongdong.zoo.like.repository.SongLikeRepository;
import com.dongdong.zoo.song.model.Song;
import com.dongdong.zoo.song.service.SongService;
import com.dongdong.zoo.user.model.User;
import com.dongdong.zoo.user.repository.UserRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class SongLikeService extends LikeService<SongService, SongLikeRepository> {

    public SongLikeService(UserRepository userRepository, SongService songService, SongLikeRepository songLikeRepository){
        super(userRepository,songService,songLikeRepository);
    }

    @Override
    public void deleteLike(Long id, Long userId){
        likeRepository.findBySong_SongIdAndUser_UserId(id, userId).ifPresentOrElse(likeRepository::delete, () -> {
            throw new DuplicateKeyException("존재하는 like인지 확인해주세요");
        });
    }

    @Override
    protected Long createLikeObject(LikeRequest likeRequest, User user) {
        if (checkUserLike(likeRequest.getSongId(), likeRequest.getUserId())) {
            throw new DuplicateKeyException("이미 존재하는 song_likes 입니다");
        }

        Song song = service.findById(likeRequest.getSongId());

        SongLikes songLikes = SongLikes.builder()
                .song(song)
                .user(user)
                .build();

        SongLikes save = likeRepository.save(songLikes);
        return save.getId();
    }


    @Override
    public boolean checkUserLike(Long id, Long userId){
        return likeRepository.existsSongLikeBySong_SongIdAndUser_UserId(id,userId);
    }
}
