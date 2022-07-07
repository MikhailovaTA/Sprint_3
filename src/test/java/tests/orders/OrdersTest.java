package tests.orders;

import io.restassured.RestAssured;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tests.serialization.DateOrdersRequest;
import tests.deserialization.ResponceServerForCreateOrder;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class OrdersTest {
    private final DateOrdersRequest dateOrdersRequest;

    public OrdersTest(DateOrdersRequest dateOrdersRequest){
        this.dateOrdersRequest = dateOrdersRequest;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        List<String> colorList = List.of("BLACK", "GREY");
        List<String> colorList_1 = List.of("BLACK");
        List<String> colorList_2 = List.of("GREY");
        return new Object[][]{
            {
                new DateOrdersRequest("Питер", "Паркер", "Ленина, 1", "Российская", "+79999999999", 2, "01.08.2022", "Спасибо", colorList)
            }, {
                new DateOrdersRequest("Питер", "Паркер", "Ленина, 1", "Российская", "+79999999999", 2, "01.08.2022", "Спасибо", colorList_1)
        }, {
                new DateOrdersRequest("Питер", "Паркер", "Ленина, 1", "Российская", "+79999999999", 2, "01.08.2022", "Спасибо", colorList_2)
        }, {
                new DateOrdersRequest("Питер", "Паркер", "Ленина, 1", "Российская", "+79999999999", 2, "01.08.2022", "Спасибо", null),
            }
        };
    }

    @Test
    public void checkCreateOrder(){
        given()
            .header("Content-type", "application/json")
                .body(dateOrdersRequest)
                .when()
                .post("/api/v1/orders")
                .then().assertThat().statusCode(201);
        }

    @Test
    public void checkBodyMessageForSuccessfulOrder() {
        ResponceServerForCreateOrder responceServer = given()
            .header("Content-type", "application/json")
            .body(dateOrdersRequest)
            .post("/api/v1/orders")
            .body()
            .as(ResponceServerForCreateOrder.class);
        MatcherAssert.assertThat(responceServer.getTrack(), not(equalTo(0)));
    }
}
