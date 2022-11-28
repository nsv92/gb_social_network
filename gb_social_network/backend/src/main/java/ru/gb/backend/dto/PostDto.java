package ru.gb.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gb.backend.entity.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostDto {
    private Long id;
    private User user;
    private String head;
    private LocalDateTime date;
}
