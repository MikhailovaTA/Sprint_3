package tests.serialization;

import org.apache.http.HttpStatus;
import tests.deserialization.ResponceServer;
import tests.deserialization.ResponceServerForAuthorization;

import static io.restassured.RestAssured.given;

public class CourierMethods {

    private static final String CREATE_COURIER_METHOD = "/api/v1/courier";
    private static final String LOGIN_COURIER_METHOD = "/api/v1/courier/login";
    private static final String DELETE_COURIER_METHOD = "/api/v1/courier/{id}";

    public static int authorizationCourier(DataCourier dataCourier) {
        ResponceServerForAuthorization responceServer = given()
            .header("Content-type", "application/json")
            .body(dataCourier)
            .post(LOGIN_COURIER_METHOD)
            .body()
            .as(ResponceServerForAuthorization.class);
        return responceServer.getId();
    }

    public static void authorizationCourierAndAssertCode(DataCourier dataCourier,  int expectedCode) {
        given()
            .header("Content-type", "application/json")
            .body(dataCourier)
            .post(LOGIN_COURIER_METHOD)
            .then()
            .assertThat()
            .statusCode(expectedCode);
    }

    public static void deleteCourier(int id) {
        given()
            .header("Content-type", "application/json")
            .when().pathParam("id", id)
            .delete(DELETE_COURIER_METHOD)
            .then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    public static void deleteCourier(DataCourier dataCourier) {
        int id = authorizationCourier(dataCourier);
        deleteCourier(id);
    }

    public static ResponceServer createCourier(DataCourier dataCourier) {
        ResponceServer responceServer = given()
            .header("Content-type", "application/json")
            .body(dataCourier)
            .post(CREATE_COURIER_METHOD)
            .body()
            .as(ResponceServer.class);
        return responceServer;
    }

    public static void createCourierAndAssertCode(DataCourier dataCourier,  int expectedCode) {
        given()
            .header("Content-type", "application/json")
            .body(dataCourier)
            .when()
            .post(CREATE_COURIER_METHOD)
            .then().assertThat().statusCode(expectedCode);
    }
}
