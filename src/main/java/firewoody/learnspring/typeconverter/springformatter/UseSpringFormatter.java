package firewoody.learnspring.typeconverter.springformatter;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDateTime;

@Data
public class UseSpringFormatter {

    // `@NumberFormat` :  숫자 관련 형식 지정 포맷터 사용
    @NumberFormat(pattern = "###,###")
    private Integer integer;

    // `@DateTimeFormat` : 날짜 관련 형식 지정 포맷터 사용
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;
}
