package tests.courier;

import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tests.serialization.CourierMethods;
import tests.serialization.DataCourier;
import tests.deserialization.ResponceServer;

import static io.restassured.RestAssured.given;
import static tests.serialization.Config.BASE_URL;

public class CourierCreateTest {

    private String login;
    private String password;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(8);
    }

    @Test
    public void checkCreateCourier() {
        CourierMethods.createCourierAndAssertCode(new DataCourier(login, password, "Piter"), HttpStatus.SC_CREATED);
    }

    @After
    public void clear(){
        CourierMethods.deleteCourier(new DataCourier(login, password, null));
    }

    @Test
    public void checkSecondCourierIsNotCreate() {
        CourierMethods.createCourierAndAssertCode(new DataCourier(login, password, "Piter"), HttpStatus.SC_CREATED);

        CourierMethods.createCourierAndAssertCode(new DataCourier(login, password, "Piter"), HttpStatus.SC_CONFLICT);
    }

    @Test
    public void chekCreateCourierWithoutFirstName() {
        CourierMethods.createCourierAndAssertCode(new DataCourier(login, password, null), HttpStatus.SC_CREATED);
    }

    @Test
    public void checkBodyMessage() {
        ResponceServer responceServer = CourierMethods.createCourier(new DataCourier(login, password, null));
        Assert.assertEquals(true, responceServer.getOk());
    }
}
