package com.chat.app.security.payload.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequest {
    @NotBlank
    private String userName;
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
}
