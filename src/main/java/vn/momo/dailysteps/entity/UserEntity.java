package vn.momo.dailysteps.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity(name = "Users")
@Table(name = "users")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;
}
