package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.OrderCreateDto;

import static io.restassured.RestAssured.given;


public class OrderClient extends RestClient {
    public final static String ORDERS = "orders";

    @Step("Создание заказа")
    public Response create(OrderCreateDto orderCreateDto) {
        return given()
                .spec(getDefaultRequestSpec())
                .body(orderCreateDto)
                .when()
                .post(ORDERS);
    }

    @Step("запрос всех заказов")
    public Response getAll() {
        return given()
                .spec(getDefaultRequestSpec())
                .get(ORDERS);
    }
}
