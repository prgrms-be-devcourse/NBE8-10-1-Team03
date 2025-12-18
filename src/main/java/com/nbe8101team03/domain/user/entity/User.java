package com.nbe8101team03.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Table(name="USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String address;

    // 우편번호
    @Column(nullable = false)
    private int zipcode;

    @Column(nullable = false)
    private boolean active = true; // 소프트 딜리트를 위해 추가

    // 빌더 사용하는 방향으로 수정
    @Builder
    public User(String email, String address, int zipcode) {
        this.email = email;
        this.address = address;
        this.zipcode = zipcode;
    }

    public void modify(String email, String address, int zipcode) {
        this.email = email;
        this.address = address;
        this.zipcode = zipcode;
    }

    // 유저 비활성화
    public void deactivate() {
        this.active = false;
    }

    // 유저 활성화
    public void activate() {
        this.active = true;
    }
}
