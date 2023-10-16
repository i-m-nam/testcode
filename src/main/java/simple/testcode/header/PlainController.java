package simple.testcode.header;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
public class PlainController {

    @Autowired PlainService plainService;

    @GetMapping("/api/v1/plain/check-header")
    public String receiveMethod(HttpServletRequest httpServletRequest) {
        Assert.notNull(httpServletRequest, "Request must not be null");

        String authorization = httpServletRequest.getHeader("Authorization");

        if (httpServletRequest.getCookies() != null) {

            // cookieTest
            Cookie cookies[] = httpServletRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("hello".equals(cookie.getName())) {
                        // for cookie test
                        return cookie.getValue();
                    }
                }
            }
        }

        // sessionTest
        HttpSession session = httpServletRequest.getSession();
        if (session != null) {
            System.out.println(session.getId());
            Object sessionAttribute = session.getAttribute("hello-session");
            if (sessionAttribute != null) {
                return sessionAttribute.toString();
            } else {
                return plainService.doService(authorization);
            }
        } else if (authorization == null){
            return "wrong";
        }

        // headerTest
        return plainService.doService(authorization);
    }

}
