package com.demo.practice.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api")
public class AuthController {

    // HttpServletRequest에 넣을 쿠키에 담길 name 값을 상수로 설정
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // 쿠키 만들기
    @GetMapping("/create-cookie")
    public String createCookie(HttpServletResponse res) {
        addCookie("Kim Auth", res);

        return "createCookie";
    }

    // 쿠키 가져오기
    @GetMapping("/get-cookie")  // 가지고 오고싶은 쿠키 이름 넣어주면 해당하는 값을 찾을 수 있음
    public String getCookie(@CookieValue(AUTHORIZATION_HEADER) String value) {
        System.out.println("value = " + value); // 예상 값 : Kim Auth

        return "getCookie : " + value;
    }

    // 세션 만들기
    @GetMapping("/create-session")  // 서블릿에서 요청 들어왔을 때 Request 객체 만들어짐
                                      // ->  그 객체를 파라미터로 받아올 수 있음!
    public String createSession(HttpServletRequest req) {
        // 세션이 존재할 경우 세션 반환, 없을 경우 새로운 세션을 생성한 후 반환
        HttpSession session = req.getSession(true);

        // 세션에 저장될 정보 Name - Value 를 추가합니다.
        session.setAttribute(AUTHORIZATION_HEADER, "Kim Auth");

        return "createSession";
    }

    // 세션 가져오기
    @GetMapping("/get-session")
    public String getSession(HttpServletRequest req) {
        // 세션이 존재할 경우 세션 반환, 없을 경우 null 반환 -> 가지고 오는 경우엔 또 생성해줄 필요가 없으므로 ! -> 없을 경우를 if문으로 제어 해주면 됨
        HttpSession session = req.getSession(false);
        // HTTP 세션 타입이 오브젝트이므로 String으로 캐스팅
        String value = (String) session.getAttribute(AUTHORIZATION_HEADER); // 가져온 세션에 저장된 Value 를 Name 을 사용하여 가져옵니다.
        System.out.println("value = " + value);

        return "getSession : " + value;
    }



    // 쿠키 저장하는 메서드                             // 서블릿에서 만들어 준 response 객체 -> 여기에 데이터 담으면 클라이언트로 반환됨 !
    public static void addCookie(String cookieValue, HttpServletResponse res) {
        try {
            // Cookie Value 에는 공백이 불가능해서 encoding 진행 ! - > 공백을 이렇게 바꾸겠다 라는 표현식
            cookieValue = URLEncoder.encode(cookieValue, "utf-8").replaceAll("\\+", "%20");
                                        // name              // value
            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, cookieValue); // Name-Value
            cookie.setPath("/");
            cookie.setMaxAge(30 * 60);

            // Response 객체에 만든 쿠키 추가
            res.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}