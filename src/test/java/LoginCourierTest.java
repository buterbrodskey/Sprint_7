import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import model.Courier;
import model.CourierId;
import model.Login;
import model.ScooterStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.hamcrest.Matchers.*;
import static utils.TestScooterSupportMethods.createRandomCourier;

public class LoginCourierTest {

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void loginCourierTest() {
        Courier courier = createRandomCourier();

        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier, ObjectMapperType.GSON)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        Login login = new Login(
                courier.getLogin(),
                courier.getPassword()
        );

        Response post = given()
                .header("Content-type", "application/json")
                .and()
                .body(login, ObjectMapperType.GSON)
                .when()
                .post("/api/v1/courier/login");
        post.then()
                .statusCode(200)
                .body("id", is(notNullValue()));


        String id = post.getBody().as(CourierId.class, ObjectMapperType.GSON).getId();
        given()
                .pathParam("id", id)
                .when()
                .delete("/api/v1/courier/{id}")
                .then()
                .statusCode(200)
                .body("ok", equalTo(true));
    }

    @Test
    public void loginWithoutLogin() {
        ScooterStatus error = ScooterStatus.LOGIN_COURIER_WITHOUT_REQUIRED_FIELD;
        Courier courier = createRandomCourier();

        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier, ObjectMapperType.GSON)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        Login login = new Login(
                null,
                courier.getPassword()
        );

        Response post = given()
                .header("Content-type", "application/json")
                .and()
                .body(login, ObjectMapperType.GSON)
                .when()
                .post("/api/v1/courier/login");
        post.then()
                .statusCode(error.getHttpCode())
                .body("message", is(error.getMessage()));
    }

    @Test
    public void loginWithoutPassword() {
        ScooterStatus error = ScooterStatus.LOGIN_COURIER_WITHOUT_REQUIRED_FIELD;
        Courier courier = createRandomCourier();

        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier, ObjectMapperType.GSON)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        Login login = new Login(
                courier.getLogin(),
                null
        );

        Response post = given()
                .header("Content-type", "application/json")
                .and()
                .body(login, ObjectMapperType.GSON)
                .when()
                .post("/api/v1/courier/login");
        post.then()
                .statusCode(error.getHttpCode())
                .body("message", is(error.getMessage()));

        String id = post.getBody().as(CourierId.class, ObjectMapperType.GSON).getId();
        given()
                .pathParam("id", id)
                .when()
                .delete("/api/v1/courier/{id}")
                .then()
                .statusCode(200)
                .body("ok", equalTo(true));
    }

    @Test
    public void loginWithNonExistLogin() {
        ScooterStatus error = ScooterStatus.LOGIN_COURIER_WITH_NON_EXIST_FIELD;
        Courier courier = createRandomCourier();

        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier, ObjectMapperType.GSON)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        Login login = new Login(
                random(10),
                courier.getPassword()
        );

        Response post = given()
                .header("Content-type", "application/json")
                .and()
                .body(login, ObjectMapperType.GSON)
                .when()
                .post("/api/v1/courier/login");
        post.then()
                .statusCode(error.getHttpCode())
                .body("message", is(error.getMessage()));
    }

    @Test
    public void loginWithNonExistPassword() {
        ScooterStatus error = ScooterStatus.LOGIN_COURIER_WITH_NON_EXIST_FIELD;
        Courier courier = createRandomCourier();

        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier, ObjectMapperType.GSON)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        Login login = new Login(
                courier.getLogin(),
                random(10)
        );

        Response post = given()
                .header("Content-type", "application/json")
                .and()
                .body(login, ObjectMapperType.GSON)
                .when()
                .post("/api/v1/courier/login");
        post.then()
                .statusCode(error.getHttpCode())
                .body("message", is(error.getMessage()));
    }
}

