package simple.testcode.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import simple.testcode.domain.User;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// beforeAll -> beforeEach -> afterEach -> afterAll 순서로 동작한다.
class UserServiceTest {

    public static MockWebServer mockWebServer;
    private UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setUp() throws IOException {
        //1. mockWebServer 생성 후 시작
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @BeforeEach
    void initialize() {
        //2. WebClient 생성과 MockWebServer URL 설정을 해준다.
        String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        WebClient webClient = WebClient.create(baseUrl);

        //3. 서비스에 클라이언트 주입
        userService = new UserService(webClient);
    }

    @AfterAll
    static void tearDown() throws IOException {
        //4. 테스트가 종료되면 MockWebServer 도 종료시킨다.
        mockWebServer.shutdown();
    }

    @DisplayName("유저 id를 통해 유저 정보를 조회할 수 있다.")
    @Test
    void getUserById() throws Exception {
        // given
        User mockUser = User.builder()
                .id(1)
                .name("Man")
                .build();

        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(mockUser))
                .addHeader("Content-Type", "application/json"));

        // when
        Mono<User> findUser = userService.getUserById(1);

        // then
        StepVerifier.create(findUser)
                .expectNextMatches(user -> user.getName().equals("Man"))
                .verifyComplete();

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertAll(
                () -> assertEquals("GET", recordedRequest.getMethod()),
                () -> assertEquals("/users/1", recordedRequest.getPath())
        );
    }

}