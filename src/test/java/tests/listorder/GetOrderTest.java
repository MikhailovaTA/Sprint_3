package tests.listorder;

import io.restassured.RestAssured;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static tests.serialization.Config.BASE_URL;
import static tests.serialization.OrderMethods.ORDERS_METHOD;

public class GetOrderTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void getOrderList(){
        int limit = 2;

        List<Object> responceServer = given()
            .header("Content-type", "application/json")
            .when().param("limit", limit)
            .get(ORDERS_METHOD)
            .body().path("orders");

        MatcherAssert.assertThat("", !(responceServer.isEmpty()));
    }
}
