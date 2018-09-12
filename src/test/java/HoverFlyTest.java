import io.specto.hoverfly.junit.rule.HoverflyRule;
import static io.restassured.RestAssured.given;
import static io.specto.hoverfly.junit.core.SimulationSource.dsl;
import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
import static org.hamcrest.core.IsEqual.equalTo;
import org.junit.ClassRule;
import org.junit.Test;

public class HoverFlyTest
{
    @ClassRule
    public static HoverflyRule hoverflyRule = HoverflyRule.inSimulationMode(dsl(
            service("www.my-test.com")
                    .post("/api/data/important")
                    .willReturn(success("{\"data\":\"important\"}", "application/json"))
    ));

    @Test
    public void shouldBeAbleToGetData()
    {
        given().
                when().
                post("http://www.my-test.com/api/data/important").
                then().
                assertThat().
                statusCode(200).
            and().
                body("data", equalTo("important"));
    }

}