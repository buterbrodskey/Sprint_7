import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import model.Courier;
import model.ScooterStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.hamcrest.Matchers.equalTo;
import static utils.TestScooterSupportMethods.createRandomCourier;

public class CreateCourierTest {

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }


    @Test
    public void createCourierTest() {
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
    }

    @Test
    public void createDuplicateLoginTest() {
        ScooterStatus error = ScooterStatus.CREATE_COURIER_WITH_DUPLICATE_LOGIN;

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

        courier.setFirstName(random(7));
        courier.setPassword(random(7));

        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier, ObjectMapperType.GSON)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(error.getHttpCode())
                .body("message", equalTo(error.getMessage()));
    }

    @Test
    public void createCourierWithoutLoginTest() {
        ScooterStatus error = ScooterStatus.CREATE_COURIER_WITHOUT_REQUIRED_FIELD;

        Courier courier = new Courier(null, random(7), random(7));

        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier, ObjectMapperType.GSON)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(error.getHttpCode())
                .body("message", equalTo(error.getMessage()));
    }

    @Test
    public void createCourierWithoutPasswordTest() {
        ScooterStatus error = ScooterStatus.CREATE_COURIER_WITHOUT_REQUIRED_FIELD;

        Courier courier = new Courier(random(7), null, random(7));

        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier, ObjectMapperType.GSON)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(error.getHttpCode())
                .body("message", equalTo(error.getMessage()));
    }
}
