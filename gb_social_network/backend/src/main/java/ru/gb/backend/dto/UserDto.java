package ru.gb.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String nickname;
    private String password;
    private String phone;
    List<String> roles;
}
