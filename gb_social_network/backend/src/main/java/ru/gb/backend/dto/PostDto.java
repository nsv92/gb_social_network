package ru.gb.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostDto {
    private Long id;
    private LocalDateTime date;
    private String head;
    private Long userId;
    private String name;

    public PostDto() {
    }

    public PostDto(Long id, Long userId, String head, LocalDateTime date, String name) {
        this.id = id;
        this.userId = userId;
        this.head = head;
        this.date = date;
        this.name = name;
    }


}
