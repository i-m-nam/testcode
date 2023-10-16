package simple.testcode.header;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class PlainControllerTest {

    @Autowired
    PlainController plainController;
    HttpServletRequest request;

    @BeforeEach
    public void init () {
        request = mock(HttpServletRequest.class);
    }

    @DisplayName("headerTest")
    @Test
    void headerTest() {
        // given
        String auth = "DEV";
        when(request.getHeader("Authorization")).thenReturn(auth);


        // when
        String result = plainController.receiveMethod(request);


        // then
        assertThat(result).isEqualTo("this_is_" + auth + "_env");
    }

    @DisplayName("cookieTest")
    @Test
    void cookieTest() {
        // given
        String cookieValue = "world";
        Cookie mockCookie = mock(Cookie.class);
        when(mockCookie.getName()).thenReturn("hello");
        when(mockCookie.getValue()).thenReturn(cookieValue);
        when(request.getCookies()).thenReturn(new Cookie[]{mockCookie});


        // when
        String result = plainController.receiveMethod(request);


        // then
        assertThat(result).isSameAs(cookieValue);
    }

    @DisplayName("sessionTest")
    @Test
    void sessionTest() {
        // given
        String attributeNumValue = "world-session";

        HttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("hello-session", "world-session");
        when(request.getSession()).thenReturn(mockSession);


        // when
        String result = plainController.receiveMethod(request);


        // then
        assertThat(result).isSameAs(attributeNumValue);
    }

}