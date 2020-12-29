package com.markusdel.cloudparking.controller;

import com.markusdel.cloudparking.dto.ParkingCreateDTO;
import io.restassured.RestAssured;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Matches;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingControllerTest extends AbstractContainerBase{

    @LocalServerPort
    private int randowPort;

    @BeforeEach
    public void setUpTest(){
        System.out.println(randowPort);
        RestAssured.port = randowPort;
    }

    @Test
    void whenFindAllCheckResult() {
        RestAssured.given()
                .auth()
                .basic("user", "123")
                .when().get("/parking")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void whenCreateThenCheckIsCreated() {
        ParkingCreateDTO parkingCreated = new ParkingCreateDTO();
        parkingCreated.setColor("AMARELO");
        parkingCreated.setLicense("WRT-5555");
        parkingCreated.setModel("BRASILIA");
        parkingCreated.setState("SP");


        RestAssured.given()
                .auth()
                .basic("user", "123")
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(parkingCreated)
                .post("/parking")
                .then()
                .statusCode(201)
                .body("license", Matchers.equalTo("WRT-5555"));
    }
}