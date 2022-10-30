import client.CourierClient;
import model.Courier;
import model.Login;
import model.ScooterStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.hamcrest.Matchers.*;
import static utils.CourierGenerator.createRandomCourier;
import static utils.LoginGenerator.from;

public class CreateCourierTest {

    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        if (id != 0) {
            courierClient
                    .delete(id)
                    .then()
                    .statusCode(200)
                    .body("ok", equalTo(true));
        }
    }

    @Test
    public void createCourierTest() {
        Courier courier = createRandomCourier();

        courierClient.create(courier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    public void createDuplicateLoginTest() {
        ScooterStatus error = ScooterStatus.CREATE_COURIER_WITH_DUPLICATE_LOGIN;

        Courier courier = createRandomCourier();

        courierClient.create(courier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        courier.setFirstName(random(7));
        courier.setPassword(random(7));

        courierClient.create(courier)
                .then()
                .statusCode(error.getHttpCode())
                .body("message", equalTo(error.getMessage()));
    }

    @Test
    public void createCourierWithoutLoginTest() {
        ScooterStatus error = ScooterStatus.CREATE_COURIER_WITHOUT_REQUIRED_FIELD;

        Courier courier = new Courier(null, random(7), random(7));

        courierClient.create(courier)
                .then()
                .statusCode(error.getHttpCode())
                .body("message", equalTo(error.getMessage()));
    }

    @Test
    public void createCourierWithoutPasswordTest() {
        ScooterStatus error = ScooterStatus.CREATE_COURIER_WITHOUT_REQUIRED_FIELD;

        Courier courier = new Courier(random(7), null, random(7));

        courierClient.create(courier)
                .then()
                .statusCode(error.getHttpCode())
                .body("message", equalTo(error.getMessage()));
    }

    @Test
    public void loginCourierTest() {
        Courier courier = createRandomCourier();

        courierClient.create(courier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        Login login = from(courier);

        id = courierClient.login(login)
                .then()
                .statusCode(200)
                .body("id", is(notNullValue()))
                .extract()
                .path("id");
    }

    @Test
    public void loginWithoutLogin() {
        ScooterStatus error = ScooterStatus.LOGIN_COURIER_WITHOUT_REQUIRED_FIELD;
        Courier courier = createRandomCourier();

        courierClient.create(courier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        Login login = new Login(
                null,
                courier.getPassword()
        );

        courierClient.login(login).then()
                .statusCode(error.getHttpCode())
                .body("message", is(error.getMessage()));
    }

    @Test
    public void loginWithoutPassword() {
        ScooterStatus error = ScooterStatus.LOGIN_COURIER_WITHOUT_REQUIRED_FIELD;
        Courier courier = createRandomCourier();

        courierClient.create(courier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        Login login = new Login(
                courier.getLogin(),
                null
        );

        int id = courierClient.login(login)
                .then()
                .statusCode(error.getHttpCode())
                .body("message", is(error.getMessage()))
                .extract()
                .path("id");

        courierClient.delete(id)
                .then()
                .statusCode(200)
                .body("ok", equalTo(true));
    }

    @Test
    public void loginWithNonExistLogin() {
        ScooterStatus error = ScooterStatus.LOGIN_COURIER_WITH_NON_EXIST_FIELD;
        Courier courier = createRandomCourier();

        courierClient.create(courier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        Login login = new Login(
                random(10),
                courier.getPassword()
        );

        courierClient.login(login)
                .then()
                .statusCode(error.getHttpCode())
                .body("message", is(error.getMessage()));
    }

    @Test
    public void loginWithNonExistPassword() {
        ScooterStatus error = ScooterStatus.LOGIN_COURIER_WITH_NON_EXIST_FIELD;
        Courier courier = createRandomCourier();

        courierClient.create(courier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        Login login = new Login(
                courier.getLogin(),
                random(10)
        );

        courierClient.login(login)
                .then()
                .statusCode(error.getHttpCode())
                .body("message", is(error.getMessage()));
    }
}
