package real.talk.service.brevo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
@TestPropertySource(properties = {
        "brevo.base-url=https://api.brevo.com/v3",
        "brevo.api-key=test-api-key",
        "brevo.list-id=3"
})
class BrevoClientTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    BrevoClient brevoClient;

    MockRestServiceServer server;

    @BeforeEach
    void setUp() {
        server = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void upsertContactToList_sendsExpectedRequest() {
        server.expect(once(), requestTo("https://api.brevo.com/v3/contacts"))
                .andExpect(method(org.springframework.http.HttpMethod.POST))
                .andExpect(header("api-key", "test-api-key"))
                .andExpect(header(CONTENT_TYPE, "application/json"))
                .andExpect(content().json("""
                        {"email":"user@example.com","listIds":[3],"updateEnabled":true}
                        """))
                .andRespond(withSuccess("{}", org.springframework.http.MediaType.APPLICATION_JSON));

        brevoClient.upsertContactToList("user@example.com");

        server.verify();
    }
}

