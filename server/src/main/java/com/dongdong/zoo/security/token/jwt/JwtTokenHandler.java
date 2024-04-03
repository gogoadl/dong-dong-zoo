package com.dongdong.zoo.security.token.jwt;

import com.dongdong.zoo.security.principal.CustomUserDetails;
import com.dongdong.zoo.security.token.TokenDto;
import com.dongdong.zoo.security.token.TokenHandler;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Slf4j
@Component
public class JwtTokenHandler implements TokenHandler {

	private static final String USER_ID_KEY = "userId";
	private static final String AUTHORITIES_KEY = "authorities";
	private static final String DELIMITER = ",";

	private final Key key;

	@Value("${jwt.expiration.access}")
	private long accessTokenExpiration;

	@Value("${jwt.expiration.refresh}")
	private long refreshTokenExpiration;

	@Value("${jwt.grant_type}")
	private String grantType;

	@Value("${service.url}")
	private String issuer;

	public JwtTokenHandler(@Value("${jwt.secret}") String secretKey) {
		byte[] decodedSecretKey = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(decodedSecretKey);
	}


	@Override
	public TokenDto generateToken(Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

		long now = System.currentTimeMillis();

		Date accessTokenExpirationDate = new Date(now + accessTokenExpiration);
		Date refreshTokenExpirationDate = new Date(now + refreshTokenExpiration);

		String accessToken = Jwts.builder()
			.setIssuer(issuer)
			.claim(USER_ID_KEY, userDetails.getUserId())
			.claim(AUTHORITIES_KEY, userDetails.getFlattenedAuthorities(DELIMITER))
			.setExpiration(accessTokenExpirationDate)
			.signWith(key, HS256)
			.compact();

		String refreshToken = Jwts.builder()
			.setIssuer(issuer)
			.claim(USER_ID_KEY, userDetails.getUserId())
			.setExpiration(refreshTokenExpirationDate)
			.signWith(key, HS256)
			.compact();

		return TokenDto.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.grantType(grantType)
			.build();
	}

	@Override
	public boolean validateToken(String token) throws CredentialsExpiredException {
		try {
			JwtParser jwtParser = Jwts.parserBuilder()
				.setSigningKey(key)
				.build();

			jwtParser.parseClaimsJws(token); // todo: ???
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT Token", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT", e);
			throw new CredentialsExpiredException("Token is expired");
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty", e);
		}
		return false;
	}

	@Override
	public String extractToken(String header) {
		if (StringUtils.hasText(header) && StringUtils.startsWithIgnoreCase(header, grantType.toLowerCase())) {
			return header.substring(grantType.length() + 1);
		}

		return null;
	}

	@Override
	public Authentication getAuthentication(String token) {
		Claims claims = getClaims(token);

		if (claims.get(AUTHORITIES_KEY) == null) {
			throw new RuntimeException(); // todo
		}

		Long userId = claims.get(USER_ID_KEY, Long.class);
		List<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY, String.class).split(DELIMITER))
			.map(SimpleGrantedAuthority::new)
			.toList();

		UserDetails userDetails = new CustomUserDetails(userId, null, null, authorities);

		return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
	}

	@Override
	public <T> T getId(String token, Class<T> aClass) {
		Claims claims = getClaims(token);
		return claims.get(USER_ID_KEY, aClass);
	}

	@Override
	public long getExpirationMillis(String token) {
		Claims claims = getClaims(token);
		return claims.getExpiration().getTime();
	}

	@Override
	public boolean isAccessToken(String token) {
		Claims claims = getClaims(token);
		return claims.containsKey(AUTHORITIES_KEY);
	}

	public <T> T getClaim(String key, String token, Class<T> aClass) {
		Claims claims = getClaims(token);
		return claims.get(key, aClass);
	}

	private Claims getClaims(String token) {
		Claims claims = null;

		try {
			JwtParser jwtParser = Jwts.parserBuilder()
				.setSigningKey(key)
				.build();

			claims = jwtParser.parseClaimsJws(token)
				.getBody();
		} catch (ExpiredJwtException e) {
			claims = e.getClaims();
		}

		return claims;
	}
}
