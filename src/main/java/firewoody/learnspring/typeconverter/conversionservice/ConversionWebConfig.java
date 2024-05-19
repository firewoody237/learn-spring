package firewoody.learnspring.typeconverter.conversionservice;

import firewoody.learnspring.typeconverter.converterinterface.basicconverter.IntegerToStringConverter;
import firewoody.learnspring.typeconverter.converterinterface.basicconverter.StringToIntegerConverter;
import firewoody.learnspring.typeconverter.converterinterface.classconverter.IpPortToStringConverter;
import firewoody.learnspring.typeconverter.converterinterface.classconverter.StringToIpPortConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConversionWebConfig implements WebMvcConfigurer {

    // 스프링은 내부에서 `ConversionService`를 제공한다.
    // `addFormatters` 를 통해 사용하려는 컨버터를 등록하기만 하면 된다.

    // 참고로, HttpMessageConverter와 ConversionService는 상관없다.
    // HttpMessageConverter의 작동은 라이브러리에 달렸다.
    // 컨버전 서비스는 `@RequestParam`, `@ModelAttribute`, `@PathVariable`, 뷰 템플릿 등에서 사용 가능하다.

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToIntegerConverter());
        registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());
    }
}
