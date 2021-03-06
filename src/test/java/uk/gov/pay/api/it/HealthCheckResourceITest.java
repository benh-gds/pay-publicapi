package uk.gov.pay.api.it;

import com.jayway.restassured.RestAssured;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static uk.gov.pay.api.resources.HealthCheckResource.HEALTHCHECK;

public class HealthCheckResourceITest extends PaymentResourceITestBase {

    @Test
    public void getAccountShouldReturn404IfAccountIdIsUnknown() throws Exception {
        RestAssured.given().port(app.getLocalPort())
                .get(HEALTHCHECK)
                .then()
                .statusCode(200)
                .body("ping.healthy", is(true))
                .body("deadlocks.healthy", is(true));
    }
}
