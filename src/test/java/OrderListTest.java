import client.OrderClient;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import model.OrderList;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OrderListTest {

    private OrderClient orderClient;
    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    public void getAllOrdersTest() {
        Response response = orderClient.getAll();
        response.then()
                .statusCode(200)
                .body("orders", notNullValue());
        OrderList orders = response.getBody().as(OrderList.class, ObjectMapperType.GSON);
        assertThat(orders.getOrders(), not(empty()));
    }
}
