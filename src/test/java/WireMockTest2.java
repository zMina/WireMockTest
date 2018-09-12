import com.github.tomakehurst.wiremock.junit.WireMockRule;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class WireMockTest2 {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(4040);

    @Before
    public void setupStub() {

        stubFor(post(urlMatching("/my/([a-z]*)"))
                .withRequestBody(matchingJsonPath("$[?(@.data == 'important')]"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/json")
                        .withStatus(200)
                        .withBodyFile("myfile.json")));
    }


    @Test
    public void testStatusCode() {

        given().contentType("text/json").body("{\"data\":\"important\"}").
        when().
            post("http://localhost:4040/my/endpoint").
        then().
            statusCode(200).
        assertThat().body("name", org.hamcrest.Matchers.equalTo("zina"));
    }

}