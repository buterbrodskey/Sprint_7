package utils;

import io.qameta.allure.Step;
import model.Courier;

import static org.apache.commons.lang3.RandomStringUtils.random;

public class CourierGenerator {

    @Step("генерация данных курьера")
    public static Courier createRandomCourier() {
        return
                new Courier(
                        random(10, true, true),
                        random(10, true, true),
                        random(10, true, false));
    }
}
