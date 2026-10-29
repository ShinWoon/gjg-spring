package com.newcld.fitlog.auth;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 응답")
public record TokenResponse(
    @Schema(description = "액세스 토큰. 이후 요청의 Authorization 헤더에 'Bearer 토큰' 형식으로 넣어요.")
    String accessToken,

    @Schema(description = "토큰 종류", example = "Bearer")
    String tokenType,

    @Schema(description = "만료 시각 (UTC)", example = "2026-11-28T06:00:00Z")
    Instant expiresAt
) {}
