package ru.gb.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordDto {
    private String oldPassword;
    private String token;
    private String newPassword;
}
