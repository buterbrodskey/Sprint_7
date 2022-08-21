import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class OrderListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void getAllOrdersTest() {
        Response response = given()
                .get("v1/orders");
        response.then()
                .statusCode(200)
                .and()
                .body("orders", is(not(empty())));
    }
}
