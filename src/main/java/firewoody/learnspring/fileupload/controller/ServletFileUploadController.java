package firewoody.learnspring.fileupload.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Collection;

// multipart/form-data는 다른 종류의 여러 파일과 폼의 내용을 함께 전송할 수 있다.

@Slf4j
@Controller
@RequestMapping("/file-upload")
public class ServletFileUploadController {

    // `getParts()`로 `multipart/form-data`에서 나누어진 부분을 받을 수 있다.
    // 사이즈 제한은 `application.properties`에서 아래와 같이 가능하다.
    // spring.servlet.multipart.max-file-size=1MB
    // spring.servlet.multipart.max-request-size=10MB
    @PostMapping("/upload")
    public String saveFile1(HttpServletRequest request) throws ServletException, IOException {
        String itemName = request.getParameter("itemName");
        Collection<Part> parts = request.getParts();

        return "ok";
    }
}
