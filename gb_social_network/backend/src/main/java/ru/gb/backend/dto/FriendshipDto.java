package ru.gb.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class FriendshipDto {
    private Long id;
    private Long userId;
    private Long friend_id;

    public FriendshipDto(Long id, Long userId, Long friend_id) {
        this.id = id;
        this.userId = userId;
        this.friend_id = friend_id;
    }

    public FriendshipDto(Long userId, Long friend_id) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setFriend_id(Long friend_id) {
        this.friend_id = friend_id;
    }

    public FriendshipDto() {
    }
}
