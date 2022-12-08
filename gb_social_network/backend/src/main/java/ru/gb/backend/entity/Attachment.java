package ru.gb.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
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
    //TODO На фронте у нас есть форма для создания поста, в которой мы загружаем наш аттачмент.
    // При отправке этой формы в БД пишется пост и аттачмент, но для записи аттачмента нам нужен id поста,
    // который нам еще не известен. Разобрать этот момент. Тупой вариант - добавлене аттачмента в отдельной форме
    private Post post;

}
