package com.epam.schedule;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features/",
        glue = "com.epam.schedule.controller"
)
public class ScheduleIntegrationTest {
}
