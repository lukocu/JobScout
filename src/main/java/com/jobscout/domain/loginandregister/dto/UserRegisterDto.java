package com.jobscout.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record UserRegisterDto(String username, String password) {
}
