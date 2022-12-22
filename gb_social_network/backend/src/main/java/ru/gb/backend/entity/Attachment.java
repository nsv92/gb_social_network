package ru.gb.backend.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Data
@Table(name = "attachments")
//TODO Может быть переименовать в Picture ???
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "storage_file_name", nullable = false, length = 256, unique = true)
    private String storageFileName;

    @Column(name  = "extension", nullable = false, length = 10)
    private String extension;

    @ManyToOne
    @JoinColumn(name = "post_id")
    //TODO Понять сколько может быть аттачментов у поста
    private Post post;

}