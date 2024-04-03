package com.dongdong.zoo.like.repository;

import com.dongdong.zoo.like.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface LikeRepository<T extends Like> extends JpaRepository<T, Long> {
}
