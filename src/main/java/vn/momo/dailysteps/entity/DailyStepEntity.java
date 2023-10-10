package vn.momo.dailysteps.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.Date;

@Entity(name = "DailySteps")
@Table(name = "daily_steps")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyStepEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "steps")
    private Integer steps;
}
