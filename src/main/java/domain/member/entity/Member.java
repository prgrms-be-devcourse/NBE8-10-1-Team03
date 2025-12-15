package domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;

    @Column(unique = true, nullable = false)
    private String email;

    // 역할 USER/ADMIN
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRoll role;

    @Column(nullable = false)
    private String address;

    // 우편번호
    @Column(nullable = false)
    private int zipcode;
}
