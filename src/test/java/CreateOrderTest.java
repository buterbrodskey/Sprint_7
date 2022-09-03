import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import model.OrderCreateDto;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTest {

    private static OrderCreateDto createRandomOrderDto() {
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

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void createOrderWithOnlyRequiredFieldTest() {
        OrderCreateDto orderCreateDto = createRandomOrderDto();

        given()
                .header("Content-type", "application/json")
                .and()
                .body(orderCreateDto, ObjectMapperType.GSON)
                .when()
                .post("/api/v1/orders")
                .then()
                .statusCode(201)
                .and()
                .body("track", is(notNullValue()));
    }
}
