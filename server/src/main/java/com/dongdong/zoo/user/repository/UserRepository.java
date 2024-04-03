package com.dongdong.zoo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.dongdong.zoo.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findUserByEmail(String email);

	void deleteUserByUserId(Long userId);
}