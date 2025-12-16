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
    private int id;

    @Column(name = "user_id", length = 10, nullable = false, unique = true)
    private String userId;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Builder
    public Admin(String userId, String password){
        this.userId = userId;
        this.password = password;
    }

    // todo 아이디나 비밀번호 변경에 필요
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
}
