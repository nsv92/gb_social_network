package ru.gb.backend.dto;

import lombok.Data;

@Data
//@AllArgsConstructor
public class FriendshipDto {
    private Long id;
    private Long userId;
    private Long friend_id;

    public FriendshipDto(Long id, Long userId, Long friend_id) {
        this.id = id;
        this.userId = userId;
        this.friend_id = friend_id;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getFriend_id() {
        return friend_id;
    }
}
