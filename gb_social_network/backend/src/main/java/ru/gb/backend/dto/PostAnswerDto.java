package ru.gb.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostAnswerDto {
    private Long id;
    private Long userId;
//    private String userName;
    private String head;
    private LocalDateTime date;
}
