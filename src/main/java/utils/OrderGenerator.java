package utils;

import io.qameta.allure.Step;
import model.OrderCreateDto;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class OrderGenerator {
    @Step("генерация данных заказа")
    public static OrderCreateDto createRandomOrderDto() {
        return new OrderCreateDto()
                .firstName(random(10))
                .lastName(random(10))
                .address(random(10))
                .metroStation(random(10))
                .phone("+" + random(11, false, true))
                .rentTime(nextInt())
                .deliveryDate("2020-09-1")
                .comment(random(12));
    }
}
