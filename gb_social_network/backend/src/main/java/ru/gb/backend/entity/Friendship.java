package ru.gb.backend.entity;

import lombok.*;
import javax.persistence.*;
import java.util.Optional;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "friendship")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    private User friend;

}
