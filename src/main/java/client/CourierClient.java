package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Courier;
import model.Login;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient {

    public static final String COURIER = "courier";
    public static final String COURIER_LOGIN = "courier/login";
    public static final String COURIER_DELETE = "/api/v1/courier/{id}";

    @Step("создание курьера")
    public Response create(Courier courier) {
        return given()
                .spec(getDefaultRequestSpec())
                .body(courier)
                .post(COURIER);
    }

    @Step("логин курьера")
    public Response login(Login login) {
        return given()
                .spec(getDefaultRequestSpec())
                .body(login)
                .post(COURIER_LOGIN);
    }

    @Step("удаление курьера")
    public Response delete(int id) {
        return given()
                .spec(getDefaultRequestSpec())
                .pathParam("id", id)
                .delete(COURIER_DELETE);
    }
}
