package com.nbe8101team03.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name="users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    // 우편번호
    @Column(nullable = false)
    private int zipcode;

    public void modify(String email, String address, int zipcode) {
        this.email = email;
        this.address = address;
        this.zipcode = zipcode;
    }
}
