package ru.gb.backend.entity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name="passwordresettoken")
public class PasswordResetToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="token")
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    @Column(name="expiry_date")
    private Date expiryDate;

    public PasswordResetToken(){}

    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, EXPIRATION);
        this.expiryDate = cal.getTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
}
