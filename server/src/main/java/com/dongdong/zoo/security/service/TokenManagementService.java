package com.dongdong.zoo.security.service;

import com.dongdong.zoo.security.token.TokenHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@RequiredArgsConstructor
@Transactional
@Service
public class TokenManagementService {

	private static final String LOGOUT_ACCESS_TOKEN_VALUE = "LOGOUT";

	private final RedisTemplate<String, String> redisTemplate;
	private final TokenHandler tokenHandler;


	public void saveRefreshToken(String id, String refreshToken) {
		long time = tokenHandler.getExpirationMillis(refreshToken);
		long now = System.currentTimeMillis();

		ValueOperations<String, String> operations = redisTemplate.opsForValue();
		operations.set(id, refreshToken, time - now, MILLISECONDS);
	}

	public void removeRefreshToken(String id) {
		redisTemplate.delete(id);
	}

	@Transactional(readOnly = true)
	public boolean matchesExistingRefreshToken(String id, String refreshToken) {
		ValueOperations<String, String> operations = redisTemplate.opsForValue();
		String existingRefreshToken = operations.get(id);

		return Objects.equals(refreshToken, existingRefreshToken);
	}

	public void saveAccessTokenInLogoutList(String accessToken) {
		long time = tokenHandler.getExpirationMillis(accessToken);
		long now = System.currentTimeMillis();

		ValueOperations<String, String> operations = redisTemplate.opsForValue();
		operations.set(accessToken, LOGOUT_ACCESS_TOKEN_VALUE, time - now, MILLISECONDS);
	}

	@Transactional(readOnly = true)
	public boolean existsAccessTokenInLogoutList(String accessToken) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(accessToken));
	}
}
