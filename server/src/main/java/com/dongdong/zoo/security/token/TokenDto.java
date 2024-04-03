package com.dongdong.zoo.security.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenDto {

	private String accessToken;
	private String refreshToken;
	private String grantType;

	@Override
	public String toString() {
		return "JwtDto{" +
			"accessToken='" + accessToken + '\'' +
			", refreshToken='" + refreshToken + '\'' +
			", grantType='" + grantType + '\'' +
			'}';
	}
}
