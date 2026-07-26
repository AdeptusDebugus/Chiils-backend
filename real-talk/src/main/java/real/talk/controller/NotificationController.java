package real.talk.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import real.talk.service.brevo.BrevoClient;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/notifications")
@Validated
class NotificationController {

    private final BrevoClient brevoClient;

    record SubscribeEmailRequest(
            @NotBlank @Email String email
    ) {
    }

    @PostMapping("/subscribe-email")
    ResponseEntity<Void> subscribeEmail(@Valid @RequestBody SubscribeEmailRequest request) {
        brevoClient.upsertContactToList(request.email());
        return ResponseEntity.noContent().build();
    }
}

