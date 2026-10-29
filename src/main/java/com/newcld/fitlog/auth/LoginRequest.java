package com.newcld.fitlog.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "로그인 요청")
public record LoginRequest(
    @Schema(description = "아이디", example = "admin")
    @NotBlank(message = "아이디를 입력해 주세요.")
    String username,

    @Schema(description = "비밀번호")
    @NotBlank(message = "비밀번호를 입력해 주세요.")
    String password
) {}
