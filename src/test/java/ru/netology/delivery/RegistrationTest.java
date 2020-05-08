package ru.netology.delivery;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static ru.netology.delivery.UserGenerator.requestSpecification;


public class RegistrationTest {
    UserGenerator userGenerator = new UserGenerator();

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
        $("[name=login]").setValue(userGenerator.getActiveLogin());
        $("[name=password]").setValue(userGenerator.getActivePassword());
        $(".button__text").click();
        $(".notification_status_error").shouldBe(hidden);
        SelenideElement response = $(".body");
    }

    @Test
    void shouldGetErrorMessageIfWeSubmitBlockedUser() {
        open("http://localhost:9999");
        $("[name=login]").setValue(userGenerator.getBlockedLogin());
        $("[name=password]").setValue(userGenerator.getBlockedPassword());
        $(".button__text").click();
        $(".notification_status_error").shouldBe(visible);
        SelenideElement response = $(withText("Ошибка"));
    }

    @Test
    void shouldGetErrorMessageIfWeSubmitWithIncorrectPassword() {
        open("http://localhost:9999");
        $("[name=login]").setValue(userGenerator.getActiveLogin());
        $("[name=password]").setValue(userGenerator.getPassword());
        $(".button__text").click();
        $(".notification_status_error").shouldBe(visible);
    }

    @Test
    void shouldGetErrorMessageIfWeSubmitWithIncorrectLogin() {
        open("http://localhost:9999");
        $("[name=login]").setValue(userGenerator.getLogin());
        $("[name=password]").setValue(userGenerator.getActivePassword());
        $(".button__text").click();
        $(".notification_status_error").shouldBe(visible);
    }

    @Test
    void shouldGetErrorMessageIfWeSubmitWithIncorrectLoginAndPassword() {
        open("http://localhost:9999");
        $("[name=login]").setValue(userGenerator.getLogin());
        $("[name=password]").setValue(userGenerator.getPassword());
        $(".button__text").click();
        $(".notification_status_error").shouldBe(visible);
    }

}