import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import model.OrderList;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

public class OrderListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void getAllOrdersTest() {
        Response response = given()
                .get("api/v1/orders");
        OrderList orders = response.getBody().as(OrderList.class, ObjectMapperType.GSON);
        response.then()
                .statusCode(200);
        assertThat(orders.getOrders(), not(empty()));
        assertThat(orders.getAvailableStations(), not(empty()));
    }
}
