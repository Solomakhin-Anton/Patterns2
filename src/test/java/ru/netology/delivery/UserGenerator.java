package ru.netology.delivery;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class UserGenerator {

    public static RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void makeRequest (Registration registration){

        given()
                .spec(requestSpecification)
                .body(new Registration("vasya", "password", "active"))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);

        given()
                .spec(requestSpecification)
                .body(new Registration("name", "anotherpassword", "blocked"))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }


    public static Registration getValidActiveUser(){
        String login = "vasya";
        String password = "password";
        String status = "active";
        Registration registration = new Registration(login,password,status);
        makeRequest(registration);
        return registration;
    }

    public static Registration getValidBlockedUser(){
        String login = "name";
        String password = "anotherpassword";
        String status = "blocked";
        Registration registration = new Registration(login,password,status);
        makeRequest(registration);
        return registration;
    }

    public static Registration getUserWithIncorrectPassword(){
        Faker faker = new Faker(new Locale("en"));
        String login = "vasya";
        String password = faker.internet().password();
        String status = "active";
        Registration registration = new Registration(login,password,status);
        makeRequest(registration);
        return registration;
    }

    public static Registration getUserWithIncorrectLogin(){
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();
        String password = "password";
        String status = "active";
        Registration registration = new Registration(login,password,status);
        makeRequest(registration);
        return registration;
    }

    public static Registration getUserWithIncorrectLoginAndPassword(){
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();
        String password = faker.internet().password();
        String status = "active";
        Registration registration = new Registration(login,password,status);
        makeRequest(registration);
        return registration;
    }

}