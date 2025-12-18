package com.nbe8101team03.domain.admin.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", length = 10, nullable = false, unique = true)
    private String userId;

    @Column(name = "password_hash", length = 255, nullable = false)
    private String passwordHash;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Builder
    public Admin(String userId, String passwordHash){
        this.userId = userId;
        this.passwordHash = passwordHash;
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    public void changeUserId(String userId){
        this.userId = userId;
    }

    public void changePasswordHash(String passwordHash){
        this.passwordHash = passwordHash;
    }
}
