package tests.authtorization;

import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.MatcherAssert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import tests.serialization.CourierUtils;
import tests.serialization.DataCourier;
import tests.deserialization.ResponceServerForAuthorization;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthorizationCourierTest {

    private static String login;
    private static String password;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(8);
        CourierUtils.createCourier(login, password);
    }

    @Test
    public void checkAuthorizationCourier(){
        DataCourier dataCourier = new DataCourier(login, password, "123");
        given()
            .header("Content-type", "application/json")
            .body(dataCourier)
            .post("/api/v1/courier/login")
            .then().assertThat().statusCode(200);
    }

    @Test
    public void checkAuthorizationWithoutLogin(){
        DataCourier dataCourier = new DataCourier(null, password, null);
        given()
            .header("Content-type", "application/json")
            .body(dataCourier)
            .when()
            .post("/api/v1/courier/login")
            .then().assertThat().statusCode(400);
    }

    //баг, возвращает 504 ошибку
    @Test
    public void checkAuthorizationWithoutPassword(){
        DataCourier dataCourier = new DataCourier(login, null, null);
        given()
            .header("Content-type", "application/json")
            .body(dataCourier)
            .when()
            .post("/api/v1/courier/login")
            .then().assertThat().statusCode(404);
    }

    @Test
    public void checkAuthorizationWithWrongPassword(){
        DataCourier dataCourier = new DataCourier(login, "parcer", null);
        given()
            .header("Content-type", "application/json")
            .body(dataCourier)
            .when()
            .post("/api/v1/courier/login")
            .then().assertThat().statusCode(404);
    }

    @Test
    public void checkAuthorizationWithWrongLogin(){
        DataCourier dataCourier = new DataCourier("piter_1997", password, null);
        given()
            .header("Content-type", "application/json")
            .body(dataCourier)
            .when()
            .post("/api/v1/courier/login")
            .then().assertThat().statusCode(404);
    }

    @Test
    public void checkBodyMessageForSuccessfulAuthorization() {
        DataCourier dataCourier = new DataCourier(login, password, null);
        ResponceServerForAuthorization responceServer = given()
            .header("Content-type", "application/json")
            .body(dataCourier)
            .post("/api/v1/courier/login")
            .body()
            .as(ResponceServerForAuthorization.class);
        MatcherAssert.assertThat(responceServer.getId(), not(equalTo(0)));
    }

    @AfterClass
    public static void clear(){
        CourierUtils.deleteCourier(login, password);
    }
}
