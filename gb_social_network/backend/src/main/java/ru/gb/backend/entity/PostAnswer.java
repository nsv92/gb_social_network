package ru.gb.backend.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "posts_answer")
public class PostAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_name")
    private User user;

    @Column(name = "head", length = 1000)
    private String head;

    @Column(name = "date")
    private LocalDateTime date;

}
