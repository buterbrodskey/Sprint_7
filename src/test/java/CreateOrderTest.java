import client.OrderClient;
import model.OrderColor;
import model.OrderCreateDto;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static utils.OrderGenerator.createRandomOrderDto;

public class CreateOrderTest {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    public void createOrderWithOnlyRequiredFieldTest() {
        OrderCreateDto orderCreateDto = createRandomOrderDto();

        orderClient.create(orderCreateDto)
                .then()
                .statusCode(201)
                .and()
                .body("track", is(notNullValue()));
    }

    @Test
    public void createOrderWithTwoColors() {
        OrderCreateDto orderCreateDto = createRandomOrderDto();
        List<OrderColor> colors = new ArrayList<>();
        colors.add(OrderColor.GREY);
        colors.add(OrderColor.BLACK);
        orderCreateDto.color(colors);

        orderClient.create(orderCreateDto)
                .then()
                .statusCode(201)
                .and()
                .body("track", is(notNullValue()));
    }
}
