package ru.gb.backend.entity;

import lombok.*;
import javax.persistence.*;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "password", nullable = false, length = 256)
    private String password;

    @Column(name = "name", nullable = false, length = 60)
    private String name;

    @Column(name = "nick_name", nullable = false, unique = true, length = 50)
    private String nickName;

    @Column(name = "email", nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "phone", nullable = true, unique = true, length = 15)
    private String phone;
}
