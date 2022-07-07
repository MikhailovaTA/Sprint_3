package tests.courier;

import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tests.serialization.CourierUtils;
import tests.serialization.DataCourier;
import tests.deserialization.ResponceServer;

import static io.restassured.RestAssured.given;

public class CourierCreateTest {

    private String login;
    private String password;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(8);
    }

    @Test
    public void checkCreateCourier() {
        DataCourier dataCourier = new DataCourier(login, password, "Piter");
        given()
            .header("Content-type", "application/json")
            .body(dataCourier)
            .when()
            .post("/api/v1/courier")
            .then().assertThat().statusCode(201);
    }

    @After
    public void clear(){
        CourierUtils.deleteCourier(login, password);
    }

    @Test
    public void checkSecondCourierIsNotCreate() {
        DataCourier dataCourier = new DataCourier(login, password, "Piter");
        given()
            .header("Content-type", "application/json")
            .body(dataCourier)
            .when()
            .post("/api/v1/courier")
            .then().assertThat().statusCode(201);

        DataCourier dataDoubleCourier = new DataCourier(login, password, "Piter");
        given()
            .header("Content-type", "application/json")
            .body(dataDoubleCourier)
            .when()
            .post("/api/v1/courier")
            .then().assertThat().statusCode(409);
    }

    @Test
    public void chekCreateCourierWithoutFirstName() {
        DataCourier dataCourier = new DataCourier(login, password, null);
        given()
            .header("Content-type", "application/json")
            .body(dataCourier)
            .when()
            .post("/api/v1/courier")
            .then().assertThat().statusCode(201);
    }

    @Test
    public void checkBodyMessage() {
        DataCourier dataCourier = new DataCourier(login, password, null);
        ResponceServer responceServer = given()
            .header("Content-type", "application/json")
            .body(dataCourier)
            .post("/api/v1/courier")
            .body()
            .as(ResponceServer.class);
        Assert.assertEquals(true, responceServer.getOk());
    }
}
