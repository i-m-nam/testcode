package simple.testcode.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import simple.testcode.domain.User;

@Service
@RequiredArgsConstructor
public class UserService {

    private final WebClient webClient;

    public Mono<User> getUserById(Integer userId) {
        return webClient
                .get()
                .uri("/users/{id}", userId)
                .retrieve()
                .bodyToMono(User.class);
    }

}
