package ru.gb.backend.entity;

import lombok.*;
import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "attachments")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //TODO понять как будем хранить вложения (фото, может быть файлы)
    @Column(name = "body", nullable = false)
    private String body;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

}
