package utils;

import model.Courier;

import static org.apache.commons.lang3.RandomStringUtils.random;

public class TestScooterSupportMethods {

    public static Courier createRandomCourier() {
        return
                new Courier(
                        random(10, true, true),
                        random(10, true, true),
                        random(10, true, false));
    }
}
