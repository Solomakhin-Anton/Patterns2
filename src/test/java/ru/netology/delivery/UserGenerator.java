package ru.netology.delivery;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

public class UserGenerator {
    private String login;
    private String password;
    private String activeLogin = "vasya";
    private String activePassword = "password";
    private String blockedLogin = "name";
    private String blockedPassword = "anotherpassword";

    public String getLogin() {
        login = makeLogin();
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        password = makePassword();
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getActiveLogin() {
        return activeLogin;
    }

    public String getActivePassword() {
        return activePassword;
    }

    public String getBlockedLogin() {
        return blockedLogin;
    }

    public String getBlockedPassword() {
        return blockedPassword;
    }

    private Faker faker;
    public String makeLogin() {
        faker = new Faker(new Locale("en"));
        return faker.name().firstName();
    }

    public String makePassword() {
        faker = new Faker(new Locale("en"));
        return faker.internet().password();
    }

    public static RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
}