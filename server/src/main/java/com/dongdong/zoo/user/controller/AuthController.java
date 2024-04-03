package com.dongdong.zoo.user.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongdong.zoo.security.principal.CustomUserDetails;
import com.dongdong.zoo.user.dto.SignUpDto;
import com.dongdong.zoo.user.model.User;
import com.dongdong.zoo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@PostMapping("/signup")
	public void signup(@RequestBody SignUpDto signUpDto) {

		User user = User.builder()
			.userName(signUpDto.getUsername())
			.email(signUpDto.getEmail())
			.password(passwordEncoder.encode(signUpDto.getPassword()))
			.userImageUrl("")
			.build();

		userRepository.save(user);
	}

	@PostMapping("/login")
	public Long login(Authentication authentication) {
		log.info("login success");

		return ((CustomUserDetails)authentication.getPrincipal()).getUserId();
	}

	@PostMapping("/logout")
	public void logout() {
		log.info("logout success");
	}

	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable Long userId) {
		userRepository.deleteUserByUserId(userId);
	}
}
