package firewoody.learnspring.typeconverter.formatter;

// FormattingConversionService 는 포맷터를 지원하는 컨버전 서비스이다.
// 동일한 타입의 컨버터와 포맷터가 동시에 있을 경우, 컨버터가 우선 적용된다.

import firewoody.learnspring.typeconverter.converterinterface.classconverter.IpPortToStringConverter;
import firewoody.learnspring.typeconverter.converterinterface.classconverter.StringToIpPortConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class FormattingConversionServiceWebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());

        registry.addFormatter(new MyNumberFormatter());
    }
}
