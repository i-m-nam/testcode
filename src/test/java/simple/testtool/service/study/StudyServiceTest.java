package simple.testtool.service.study;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import simple.testtool.domain.member.Member;
import simple.testtool.domain.study.Study;
import simple.testtool.domain.study.StudyRepository;
import simple.testtool.enums.study.StudyStatus;
import simple.testtool.service.member.MemberService;

import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@Slf4j
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Testcontainers
class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Autowired
    StudyRepository studyRepository;

//    @Value("${container.port}")
//    int port;

//    @Container
//    static DockerComposeContainer composeContainer =
//            new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"))
//                    .withExposedService("study-db", 5432);


    /**
     * 1. 직접 컨테이너 생명 주기 관리하기 (여러 테스트에서 사용하기 위해 static 선언)
     */
//    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
//            .withDatabaseName("studytest");
//
//
//    @BeforeAll
//    static void beforeAll() {
//        postgreSQLContainer.start();
//    }
//
//    @BeforeEach
//    void beforeEach() {
//        studyRepository.deleteAll();
//    }
//
//    @AfterAll
//    static void afterAll() {
//        postgreSQLContainer.stop();
//    }


    /**
     * 선언적으로 컨테이너 관리
     */
    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
            .withDatabaseName("studytest");
//            .withExposedPorts(5432)
//            .withEnv("POSTGRES_DB", "studytest");

    @BeforeAll //이렇게 하면 도커 안에 로그를 볼 수 있음
    static void beforeAll() {
        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(log);
        postgreSQLContainer.followOutput(logConsumer); //둘 중에 하나만 써도 됨

    }

    @BeforeEach
    void beforeEach() {
//        System.out.println("=================");
//        System.out.println(postgreSQLContainer.getMappedPort(5432));
//        System.out.println(postgreSQLContainer.getLogs()); //이렇게 하면 각 테스트 마다 도커 로그를 볼 수 있음, 둘 중에 하나만 써도 됨

        studyRepository.deleteAll();
    }


    @Test
    void createNewStudy() {

        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = Member.builder()
                .id(1L)
                .email("keesun@email.com")
                .build();

        Study study = Study.builder()
                .limitCount(10)
                .name("테스트")
                .build();


        given(memberService.findById(1L)).willReturn(Optional.of(member));

        // When
        studyService.createNewStudy(1L, study);

        // Then
        assertEquals(1L, study.getOwnerId());
        then(memberService).should(times(1)).notify(study);
        then(memberService).shouldHaveNoMoreInteractions();
    }

    @DisplayName("다른 사용자가 볼 수 있도록 스터디를 공개한다.")
    @Test
    void openStudy() {
        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);

        Study study = Study.builder()
                .limitCount(10)
                .name("더 자바, 테스트")
                .build();

        assertNull(study.getOpenedDateTime());

        // When
        studyService.openStudy(study);

        // Then
        assertEquals(StudyStatus.OPENED, study.getStatus());
        assertNotNull(study.getOpenedDateTime());
        then(memberService).should().notify(study);
    }


}