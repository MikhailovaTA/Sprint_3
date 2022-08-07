package tests.serialization;

import tests.deserialization.ResponceServerForCreateOrder;

import static io.restassured.RestAssured.given;

public class OrderMethods {

    public static final String ORDERS_METHOD = "/api/v1/orders";

    public static void createOrderAndAssertCode(DateOrdersRequest dateOrdersRequest, int expectedCode) {
        given()
            .header("Content-type", "application/json")
            .body(dateOrdersRequest)
            .when()
            .post(ORDERS_METHOD)
            .then().assertThat().statusCode(expectedCode);
    }

    public static ResponceServerForCreateOrder createOrder(DateOrdersRequest dateOrdersRequest) {
        ResponceServerForCreateOrder responceServer = given()
            .header("Content-type", "application/json")
            .body(dateOrdersRequest)
            .post(ORDERS_METHOD)
            .body()
            .as(ResponceServerForCreateOrder.class);
        return responceServer;
    }
}
