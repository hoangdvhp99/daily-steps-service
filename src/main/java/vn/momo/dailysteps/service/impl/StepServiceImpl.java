package vn.momo.dailysteps.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.momo.dailysteps.dto.leaderboard.LeaderboardItem;
import vn.momo.dailysteps.dto.step.StepRecordRQ;
import vn.momo.dailysteps.entity.DailyStepEntity;
import vn.momo.dailysteps.entity.UserEntity;
import vn.momo.dailysteps.repository.DailyStepRepository;
import vn.momo.dailysteps.repository.UserRepository;
import vn.momo.dailysteps.service.StepService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StepServiceImpl implements StepService {
    private final UserRepository userRepository;
    private final DailyStepRepository dailyStepRepository;

    @Override
    public void recordStep(StepRecordRQ request) throws Exception {
        Optional<UserEntity> optionalUser = userRepository.findById(request.getUserId());
        if (optionalUser.isEmpty()) {
            throw new Exception("User not exist!");
        }
        DailyStepEntity dailyStepEntity = dailyStepRepository.findByUserIdAndCreatedDate(request.getUserId(), new Date());
        if (dailyStepEntity != null) {
            dailyStepEntity.setSteps(dailyStepEntity.getSteps() + request.getSteps());
        } else {
            dailyStepEntity = DailyStepEntity.builder()
                    .createdDate(new Date())
                    .steps(request.getSteps())
                    .userId(request.getUserId())
                    .build();
        }

        dailyStepRepository.save(dailyStepEntity);
    }

    @Override
    public List<LeaderboardItem> getLeaderboard(String date, int limit) throws Exception {
        Date dateParam = new Date();
        if (!date.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            try {
                dateParam = dateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
                dateParam = new Date();
            }
        }

        return dailyStepRepository.getLeaderboard(dateParam, limit);
    }
}
