package tests.listorder;

import io.restassured.RestAssured;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GetOrderTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void getOrderList(){
        int limit = 2;

        List<Object> responceServer = given()
            .header("Content-type", "application/json")
            .when().param("limit", limit)
            .get("/api/v1/orders")
            .body().path("orders");

        MatcherAssert.assertThat("", !(responceServer.isEmpty()));
    }
}
