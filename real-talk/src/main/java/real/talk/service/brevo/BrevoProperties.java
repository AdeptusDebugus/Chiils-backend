package real.talk.service.brevo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "brevo")
public record BrevoProperties(
        @NotBlank String baseUrl,
        @NotBlank String apiKey,
        @NotNull Integer listId
) {
}

