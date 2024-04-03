package com.dongdong.zoo.like.service;

import com.dongdong.zoo.like.dto.LikeRequest;
import com.dongdong.zoo.like.repository.LikeRepository;
import com.dongdong.zoo.user.model.User;
import com.dongdong.zoo.user.repository.UserRepository;
import jakarta.transaction.Transactional;

public abstract class LikeService<T, R extends LikeRepository<?>> {

    protected final UserRepository userRepository;
    protected T service;
    protected R likeRepository;

    public LikeService(UserRepository userRepository, T service, R likeRepository) {
        this.userRepository = userRepository;
        this.service = service;
        this.likeRepository = likeRepository;
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->new RuntimeException());
    }

    @Transactional
    public Long createLike(LikeRequest likeRequest) {
        User user = getUser(likeRequest.getUserId());
        return createLikeObject(likeRequest, user);
    }

    public abstract void deleteLike(Long id, Long userId);

    public abstract boolean checkUserLike(Long id, Long userId);

    protected abstract Long createLikeObject(LikeRequest likeRequest, User user);

}
