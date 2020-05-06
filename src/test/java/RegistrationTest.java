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
        $$(".button__content").find(exactText("Продолжить")).click();
        $(withText("Ошибка")).shouldBe(hidden);
        SelenideElement response = $(".body");
    }

    @Test
    void shouldSubmitBlockedUser() {
        open("http://localhost:9999");
        $("[name=login]").setValue("name");
        $("[name=password]").setValue("anotherpassword");
        $$(".button__content").find(exactText("Продолжить")).click();
        $(withText("Ошибка")).shouldBe(visible);
        SelenideElement response = $(withText("Ошибка"));
    }

    @Test
    void shouldSubmitWithIncorrectPassword() {
        open("http://localhost:9999");
        $("[name=login]").setValue("vasya");
        $("[name=password]").setValue("incorrectpassword");
        $$(".button__content").find(exactText("Продолжить")).click();
        $(withText("Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldSubmitWithIncorrectLogin() {
        open("http://localhost:9999");
        $("[name=login]").setValue("logname");
        $("[name=password]").setValue("password");
        $$(".button__content").find(exactText("Продолжить")).click();
        $(withText("Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldSubmitWithIncorrectLoginAndPassword() {
        open("http://localhost:9999");
        $("[name=login]").setValue("logname");
        $("[name=password]").setValue("incorrectpassword");
        $$(".button__content").find(exactText("Продолжить")).click();
        $(withText("Неверно указан логин или пароль")).shouldBe(visible);
    }

}