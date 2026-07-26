package real.talk.service.brevo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrevoClient {

    private final RestTemplate restTemplate;
    private final BrevoProperties properties;

    public void upsertContactToList(String email) {
        String url = properties.baseUrl() + "/contacts";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", properties.apiKey());

        BrevoContactCreateRequest body = new BrevoContactCreateRequest(
                email,
                List.of(properties.listId()),
                true
        );

        try {
            restTemplate.postForEntity(url, new HttpEntity<>(body, headers), String.class);
        } catch (RestClientResponseException e) {
            throw new BrevoException("Brevo request failed: " + e.getRawStatusCode() + " " + e.getStatusText(), e);
        }
    }
}

