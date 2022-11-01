import client.OrderClient;
import model.OrderColor;
import model.OrderCreateDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static utils.OrderGenerator.createRandomOrderDto;

@RunWith(Parameterized.class)
public class CreateOrderParametrizedTest {

    private OrderColor orderColor;
    private OrderClient orderClient;

    public CreateOrderParametrizedTest(OrderColor orderColor) {
        this.orderColor = orderColor;
    }

    @Parameterized.Parameters
    public static OrderColor[] getAllowedOrderColor() {
        return new OrderColor[]{
                OrderColor.BLACK,
                OrderColor.GREY,
                null
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    public void createOrderWithAllowedColorTest() {
        OrderCreateDto orderCreateDto = createRandomOrderDto();
        List<OrderColor> colors = new ArrayList<>();
        colors.add(orderColor);
        orderCreateDto.color(colors);

        orderClient.create(orderCreateDto)
                .then()
                .statusCode(201)
                .and()
                .body("track", is(notNullValue()));
    }
}
