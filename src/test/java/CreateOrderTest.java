import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import model.OrderColor;
import model.OrderDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


@RunWith(Parameterized.class)
public class CreateOrderTest {

    OrderColor orderColor;

    public CreateOrderTest(OrderColor orderColor) {
        this.orderColor = orderColor;
    }

    @Parameterized.Parameters
    public static OrderColor[] getAllowedOrderColor() {
        return new OrderColor[] {
                OrderColor.BLACK,
                OrderColor.GREY,
                null
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    private static OrderDto createRandomOrderDto() {
        return new OrderDto()
                .firstName(random(10))
                .lastName(random(10))
                .address(random(10))
                .metroStation(random(10))
                .phone("+" + random(11, false, true))
                .rentTime(nextInt())
                .deliveryDate("2020-09-1")
                .comment(random(12));
    }

    @Test
    public void createOrderWithOnlyRequiredFieldTest() {
        OrderDto orderDto = createRandomOrderDto();

        given()
                .header("Content-type", "application/json")
                .and()
                .body(orderDto, ObjectMapperType.GSON)
                .when()
                .post("/api/v1/orders")
                .then()
                .statusCode(201)
                .and()
                .body("track", is(notNullValue()));
    }

    @Test
    public void createOrderWithAllowedColorTest() {
        OrderDto orderDto = createRandomOrderDto();
        List<OrderColor> colors = new ArrayList<>();
        colors.add(orderColor);

        given()
                .header("Content-type", "application/json")
                .and()
                .body(orderDto, ObjectMapperType.GSON)
                .when()
                .post("/api/v1/orders")
                .then()
                .statusCode(201)
                .and()
                .body("track", is(notNullValue()));
    }
}
