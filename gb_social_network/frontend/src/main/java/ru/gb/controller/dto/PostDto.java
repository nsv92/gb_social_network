package ru.gb.controller.dto;

import lombok.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

    private Long id;
    private Long userId;
    private String head;
    private LocalDateTime date;
}
