package vn.momo.dailysteps.service;

import vn.momo.dailysteps.dto.leaderboard.LeaderboardItem;
import vn.momo.dailysteps.dto.step.StepRecordRQ;

import java.util.List;

public interface StepService {
    void recordStep(StepRecordRQ request) throws Exception;
    List<LeaderboardItem> getLeaderboard(String date, int limit) throws Exception;
}
