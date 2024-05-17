package firewoody.learnspring.login.session;

// 세션 동작 방식
// 1. 사용자가 로그인 정보를 보낸다.
// 2. 서버에서 사용자 정보를 검증한다.
// 3. 사용자 정보를 검증한 후, 추정 불가능한 세션 ID를 생성한다.
// 4. 생성한 세션 정보를 세션 저장소에 보관한다.
// 5. 세션 ID를 쿠키에 담아 클라이언트에게 보낸다.
// 6. 클라이언트는 다음 요청에 세션 ID를 쿠키에 담아 보낸다.

// 세션 관리는 3가지 기능을 제공한다.
// 1. 세션 생성
// 2. 세션 조회
// 3. 세션 만료

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomSessionManager {

    public static final String SESSION_COOKIE_NAME = "mySessionId";
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    public void createSession(Object value, HttpServletResponse response) {
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);
    }

    public Object getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie == null) {
            return null;
        }

        return sessionStore.get(sessionCookie.getValue());
    }

    public void expire(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie != null) {
            sessionStore.remove(sessionCookie.getValue());
        }
    }

    private Cookie findCookie(HttpServletRequest request, String sessionCookieName) {
        if (request.getCookies() == null) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(sessionCookieName))
                .findAny()
                .orElse(null);
    }
}
