package vn.momo.dailysteps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DailyStepsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyStepsApplication.class, args);
    }

}
