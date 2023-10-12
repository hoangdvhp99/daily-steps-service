package vn.momo.dailysteps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.momo.dailysteps.dto.leaderboard.LeaderboardItem;
import vn.momo.dailysteps.dto.step.WeeklyTotalStepsRS;
import vn.momo.dailysteps.entity.DailyStepEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface DailyStepRepository extends JpaRepository<DailyStepEntity, Long> {
    @Query(value = "SELECT u.id as UserId, u.name, u.email, d.steps " +
            "FROM daily_steps d JOIN users u ON d.user_id = u.id " +
            "WHERE d.created_date = :date " +
            "ORDER BY d.steps DESC " +
            "LIMIT :limit", nativeQuery = true)
    List<LeaderboardItem> getLeaderboard(Date date, int limit);

    DailyStepEntity findByUserIdAndCreatedDate(Long userId, Date date);

    @Query(value = "SELECT d.user_id AS userId, SUM(d.steps) AS totalSteps " +
            "FROM daily_steps d " +
            "WHERE (d.created_date BETWEEN :fromDate AND :toDate) AND d.user_id = :userId " +
            "GROUP BY d.user_id", nativeQuery = true)
    WeeklyTotalStepsRS getTotalStepsByRangeDate(long userId, LocalDate fromDate, LocalDate toDate);

}
