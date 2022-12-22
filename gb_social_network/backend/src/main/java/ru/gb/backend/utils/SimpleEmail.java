package ru.gb.backend.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimpleEmail {
    private String message;
    private String subject;
    private String mailTo;
}
