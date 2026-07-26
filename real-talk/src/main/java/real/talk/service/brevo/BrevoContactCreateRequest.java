package real.talk.service.brevo;

import java.util.List;

record BrevoContactCreateRequest(
        String email,
        List<Integer> listIds,
        boolean updateEnabled
) {
}

