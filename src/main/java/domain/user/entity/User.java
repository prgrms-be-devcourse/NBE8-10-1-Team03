package domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="user")
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
