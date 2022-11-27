package ru.gb.backend.entity;

import javax.persistence.*;

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

    public Attachment() {
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
