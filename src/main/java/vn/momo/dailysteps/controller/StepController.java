package vn.momo.dailysteps.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.momo.dailysteps.dto.ResultDTO;
import vn.momo.dailysteps.dto.step.StepRecordRQ;
import vn.momo.dailysteps.service.StepService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/steps")
public class StepController extends BaseController {
    private final StepService stepService;

    @PostMapping("/record-steps")
    public ResponseEntity<?> recordSteps(@RequestBody StepRecordRQ request) {
        try {
            stepService.recordStep(request);
            return ResponseEntity.ok(new ResultDTO(200, "SUCCESS"));
        } catch (Exception exception) {
            return response(error(exception));
        }
    }

    @GetMapping("/leaderboard/daily")
    public ResponseEntity<?> leaderboard(
            @RequestParam(value = "date", defaultValue = "") String date,
            @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        try {
            return ResponseEntity.ok(new ResultDTO(200, "SUCCESS", stepService.getLeaderboard(date, limit)));
        } catch (Exception exception) {
            return response(error(exception));
        }
    }
}
