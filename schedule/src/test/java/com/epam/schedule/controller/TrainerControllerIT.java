package com.epam.schedule.controller;

import com.epam.schedule.domain.ActionType;
import com.epam.schedule.dto.TrainerClientDTO;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class TrainerControllerIT {
    @LocalServerPort
    String port;
    ResponseEntity<?> lastResponse;

    @When("The Client Saves trainer using url {string}")
    public void saveTrainerUsingUrl(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        TrainerClientDTO request = TrainerClientDTO.builder()
                .username("JohnDoe")
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .dateTime(new Date())
                .duration(Duration.ofHours(2))
                .actionType(ActionType.CREATE)
                .build();

        HttpEntity<TrainerClientDTO> requestEntity = new HttpEntity<>(request, headers);

        lastResponse = new RestTemplate().exchange(
                "http://localhost:" + port + url,
                HttpMethod.POST,
                requestEntity,
                String.class);
    }

    @Then("The Client for saving trainer receives a response status {int}")
    public void receiveResponseStatus(int status) {
        assertEquals(status, lastResponse.getStatusCode().value());
    }
}
