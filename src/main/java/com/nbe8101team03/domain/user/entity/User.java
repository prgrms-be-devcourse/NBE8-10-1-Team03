package com.nbe8101team03.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name="USERS")
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    // 우편번호
    @Column(nullable = false)
    private int zipcode;
}
