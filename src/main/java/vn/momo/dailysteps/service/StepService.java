package vn.momo.dailysteps.service;

import vn.momo.dailysteps.dto.leaderboard.LeaderboardItem;
import vn.momo.dailysteps.dto.step.StepRecordRQ;
import vn.momo.dailysteps.dto.step.WeeklyTotalStepsRS;

import java.util.List;

public interface StepService {
    void recordStep(StepRecordRQ request) throws Exception;
    List<LeaderboardItem> getLeaderboard(String date, int limit) throws Exception;
    WeeklyTotalStepsRS getWeeklyTotalSteps(long userId) throws Exception;
    WeeklyTotalStepsRS getMonthlyTotalSteps(long userId) throws Exception;
}
