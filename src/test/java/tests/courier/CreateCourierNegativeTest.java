package tests.courier;

import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import tests.serialization.CourierMethods;
import tests.serialization.DataCourier;

import static io.restassured.RestAssured.given;
import static tests.serialization.Config.BASE_URL;

public class CreateCourierNegativeTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void chekCreateCourierWithoutLogin() {
        String password = RandomStringUtils.randomAlphabetic(8);
        CourierMethods.createCourierAndAssertCode(new DataCourier(null, password, "Piter"), HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void chekCreateCourierWithoutPassword() {
        String login = RandomStringUtils.randomAlphabetic(10);
        CourierMethods.createCourierAndAssertCode(new DataCourier(login, null, "Piter"), HttpStatus.SC_BAD_REQUEST);
    }
}
