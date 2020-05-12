package ru.netology.delivery;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.delivery.UserGenerator.*;


public class RegistrationTest {
    UserGenerator userGenerator = new UserGenerator();

    @Test
    void shouldSubmitActiveUser() {
        open("http://localhost:9999");
        Registration validValidActiveUser = getValidActiveUser();
        $("[name=login]").setValue(validValidActiveUser.getLogin());
        $("[name=password]").setValue(validValidActiveUser.getPassword());
        $(".button__text").click();
        $(".notification_status_error").shouldBe(hidden);
        SelenideElement response = $(".body");
    }

    @Test
    void shouldGetErrorMessageIfWeSubmitBlockedUser() {
        open("http://localhost:9999");
        Registration  validValidAcBlockedUser = getValidBlockedUser();
        $("[name=login]").setValue(validValidAcBlockedUser.getLogin());
        $("[name=password]").setValue(validValidAcBlockedUser.getPassword());
        $(".button__text").click();
        $(".notification_status_error").shouldBe(visible);
        SelenideElement response = $(withText("Ошибка"));
    }

    @Test
    void shouldGetErrorMessageIfWeSubmitWithIncorrectPassword() {
        open("http://localhost:9999");
        Registration  userWithIncorrectPassword = getUserWithIncorrectPassword();
        $("[name=login]").setValue(userWithIncorrectPassword.getLogin());
        $("[name=password]").setValue(userWithIncorrectPassword.getPassword());
        $(".button__text").click();
        $(".notification_status_error").shouldBe(visible);
    }

    @Test
    void shouldGetErrorMessageIfWeSubmitWithIncorrectLogin() {
        open("http://localhost:9999");
        Registration  userWithIncorrectLogin = getUserWithIncorrectLogin();
        $("[name=login]").setValue(userWithIncorrectLogin.getLogin());
        $("[name=password]").setValue(userWithIncorrectLogin.getPassword());
        $(".button__text").click();
        $(".notification_status_error").shouldBe(visible);
    }

    @Test
    void shouldGetErrorMessageIfWeSubmitWithIncorrectLoginAndPassword() {
        open("http://localhost:9999");
        Registration  userWithIncorrectLoginAndPassword = getUserWithIncorrectLoginAndPassword();
        $("[name=login]").setValue(userWithIncorrectLoginAndPassword.getLogin());
        $("[name=password]").setValue(userWithIncorrectLoginAndPassword.getPassword());
        $(".button__text").click();
        $(".notification_status_error").shouldBe(visible);
    }

}