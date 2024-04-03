package com.dongdong.zoo.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class SignUpDto {

	@NotNull
	private final String username;

	@Email
	private final String email;

	@NotNull
	private final String password;

}
