package firewoody.learnspring.fileupload.springfileupload;

// 스프링은 MultipartFile 인터페이스를 제공한다.

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/file-upload/spring")
public class SpringUploadController {

    @PostMapping("/upload")
    public String saveFile(@RequestParam String itemName, @RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        log.info("request={}", request);
        log.info("itemName={}", itemName);
        log.info("MultipartFile={}", file);

        if (!file.isEmpty()) {
            String fullPath = "/root/aa/" + file.getOriginalFilename();
            log.info("file save={}", fullPath);
            file.transferTo(new File(fullPath));
        }

        return "ok";
    }
}
