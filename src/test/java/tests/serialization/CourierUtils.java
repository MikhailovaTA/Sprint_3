package tests.serialization;

import tests.deserialization.ResponceServerForAuthorization;

import static io.restassured.RestAssured.given;

public class CourierUtils {

    public static int authorizationCourier(String login, String password) {
        DataCourier dataCourier = new DataCourier(login, password, null);
        ResponceServerForAuthorization responceServer = given()
            .header("Content-type", "application/json")
            .body(dataCourier)
            .post("/api/v1/courier/login")
            .body()
            .as(ResponceServerForAuthorization.class);
        return responceServer.getId();
    }

    public static void deleteCourier(int id) {
        given()
            .header("Content-type", "application/json")
            .when().pathParam("id", id)
            .delete("/api/v1/courier/{id}")
            .then().assertThat().statusCode(200);
    }

    public static void deleteCourier(String login, String password) {
        int id = authorizationCourier(login, password);
        deleteCourier(id);
    }

    public static void createCourier(String login, String password) {
        DataCourier dataCourier = new DataCourier(login, password, "Piter");
        given()
            .header("Content-type", "application/json")
            .body(dataCourier)
            .when()
            .post("/api/v1/courier")
            .then().assertThat().statusCode(201);
    }
}
