package com.dongdong.zoo.security.service;

import com.dongdong.zoo.security.principal.CustomUserDetails;
import com.dongdong.zoo.user.model.User;
import com.dongdong.zoo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException("Username " + username + " is not found."));

		return new CustomUserDetails(user.getUserId(), user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("USER"))); // todo
	}
}
