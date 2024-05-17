package firewoody.learnspring.session;

import firewoody.learnspring.login.session.CustomSessionManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class CustomSessionManagerTest {

    CustomSessionManager sessionManager = new CustomSessionManager();

    @Test
    void sessionTest() {
        // 세션 생성
        MockHttpServletResponse response = new MockHttpServletResponse();
        sessionManager.createSession("mySession", response);

        // 요청에 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        // 세션 조회
        Object result = sessionManager.getSession(request);
        Assertions.assertThat(result).isEqualTo("mySession");

        // 세션 만료
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        Assertions.assertThat(expired).isNull();
    }
}
