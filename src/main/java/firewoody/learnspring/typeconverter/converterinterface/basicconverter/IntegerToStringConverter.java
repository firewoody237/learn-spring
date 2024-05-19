package firewoody.learnspring.typeconverter.converterinterface.basicconverter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

// 이 방식은 Utils처럼 그냥 가져다가 사용한다. IntegerToStringConverter.covert(~!~)

@Slf4j
public class IntegerToStringConverter implements Converter<Integer, String> {

    @Override
    public String convert(Integer source) {
        log.info("convert source={}", source);
        return String.valueOf(source);
    }
}
