package ru.netology.delivery;

import com.codeborne.selenide.SelenideElement;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;


public class RegistrationTest {
    UserGenerator userGenerator = new UserGenerator();
    private static RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @BeforeAll
    static void setUpAll() {
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

    @Test
    void shouldSubmitActiveUser() {
        open("http://localhost:9999");
        $("[name=login]").setValue("vasya");
        $("[name=password]").setValue("password");
        $(".button__text").click();
        $(".notification_status_error").shouldBe(hidden);
        SelenideElement response = $(".body");
    }

    @Test
    void shouldGetErrorMessageIfSubmitBlockedUser() {
        open("http://localhost:9999");
        $("[name=login]").setValue("name");
        $("[name=password]").setValue("anotherpassword");
        $(".button__text").click();
        $(".notification_status_error").shouldBe(visible);
        SelenideElement response = $(withText("Ошибка"));
    }

    @Test
    void shouldGetErrorMessageIfSubmitWithIncorrectPassword() {
        open("http://localhost:9999");
        $("[name=login]").setValue("vasya");
        $("[name=password]").setValue(userGenerator.makePassword());
        $(".button__text").click();
        $(".notification_status_error").shouldBe(visible);
    }

    @Test
    void shouldGetErrorMessageIfSubmitWithIncorrectLogin() {
        open("http://localhost:9999");
        $("[name=login]").setValue(userGenerator.makeLogin());
        $("[name=password]").setValue("password");
        $(".button__text").click();
        $(".notification_status_error").shouldBe(visible);
    }

    @Test
    void shouldGetErrorMessageIfSubmitWithIncorrectLoginAndPassword() {
        open("http://localhost:9999");
        $("[name=login]").setValue(userGenerator.makeLogin());
        $("[name=password]").setValue(userGenerator.makePassword());
        $(".button__text").click();
        $(".notification_status_error").shouldBe(visible);
    }

}