package tests.courier;

import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import tests.serialization.DataCourier;

import static io.restassured.RestAssured.given;

public class CreateCourierNegativeTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void chekCreateCourierWithoutLogin() {
        String password = RandomStringUtils.randomAlphabetic(8);
        DataCourier dataCourier = new DataCourier(null, password, "Piter");
        given()
            .header("Content-type", "application/json")
            .body(dataCourier)
            .when()
            .post("/api/v1/courier")
            .then().assertThat().statusCode(400);
    }

    @Test
    public void chekCreateCourierWithoutPassword() {
        String login = RandomStringUtils.randomAlphabetic(10);
        DataCourier dataCourier = new DataCourier(login, null, "Piter");
        given()
            .header("Content-type", "application/json")
            .body(dataCourier)
            .when()
            .post("/api/v1/courier")
            .then().assertThat().statusCode(400);
    }
}
