package vn.momo.dailysteps.dto.step;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StepRecordRQ {
    private long userId;
    private int steps;
}
