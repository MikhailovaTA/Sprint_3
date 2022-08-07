package tests.authtorization;

import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.hamcrest.MatcherAssert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import tests.serialization.CourierMethods;
import tests.serialization.DataCourier;

import static org.hamcrest.Matchers.*;
import static tests.serialization.Config.BASE_URL;

public class AuthorizationCourierTest {

    private static String login;
    private static String password;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(8);
        CourierMethods.createCourier(new DataCourier(login, password, null));
    }

    @Test
    public void checkAuthorizationCourier(){
        CourierMethods.authorizationCourierAndAssertCode(new DataCourier(login, password, "123"), HttpStatus.SC_OK);
    }

    @Test
    public void checkAuthorizationWithoutLogin(){
        CourierMethods.authorizationCourierAndAssertCode(new DataCourier(null, password, null), HttpStatus.SC_BAD_REQUEST);
    }

    //баг, возвращает 504 ошибку
    @Test
    public void checkAuthorizationWithoutPassword(){
        CourierMethods.authorizationCourierAndAssertCode(new DataCourier(login, null, null), HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void checkAuthorizationWithWrongPassword(){
        CourierMethods.authorizationCourierAndAssertCode(new DataCourier(login, "parcer", null), HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void checkAuthorizationWithWrongLogin(){
        CourierMethods.authorizationCourierAndAssertCode(new DataCourier("piter_1997", password, null), HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void checkBodyMessageForSuccessfulAuthorization() {
        int courierId = CourierMethods.authorizationCourier(new DataCourier(login, password, null));
        MatcherAssert.assertThat(courierId, not(equalTo(0)));
    }

    @AfterClass
    public static void clear(){
        CourierMethods.deleteCourier(new DataCourier(login, password, null));
    }
}
