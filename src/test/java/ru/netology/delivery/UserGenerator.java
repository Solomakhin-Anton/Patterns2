package ru.netology.delivery;

import com.github.javafaker.Faker;

import java.util.Locale;

public class UserGenerator {

    private Faker faker;

    public String makeLogin() {
        faker = new Faker(new Locale("en"));
        return faker.name().firstName();
    }

    public String makePassword() {
        faker = new Faker(new Locale("en"));
        return faker.internet().password();
    }

}