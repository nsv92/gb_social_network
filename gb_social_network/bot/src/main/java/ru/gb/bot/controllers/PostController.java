package ru.gb.bot.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    //TODO сделать ручку для post notifications

    @GetMapping("/rest")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/posts")
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public ResponseEntity<String> ok() {
        return ResponseEntity.status(HttpStatus.CREATED).body("OK (CODE 200)\n");
    }
}
