package firewoody.learnspring.validation.beanvalidator;

import firewoody.learnspring.validation.beanvalidator.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/validation/bean-validation")
public class BeanValidationController {

    @PostMapping
    public String beanValidation(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // spring-boot-starter-validation은 자동으로 Bean Validator를 인지하고 스프링에 통합한다.
        // `LocalValidatorFactoryBean`을 글로벌 Validator로 등록한다.
        // 그렇기에 `@Valid`, `@Validated`만 적용하면 된다.
        //      `@Valid` : 자바 표준 검증 애노테이션
        //      `@Validated` : 스프링 전용 검증 애노테이션(group 기능 존재)
        // 순서
        //      1. `@ModelAttribute` 각각의 필드에 타입 변환 시도
        //          성공하면 메서드 내부 내용 수행
        //          실패하면 typeMismatch로 FieldError 추가
        //      2. Validator 적용 (바인딩에 성공한 필드만 BeanValidation 적용)
        if (bindingResult.hasErrors()) {
            log.info("erros={}", bindingResult);
            return "validation/bean/v1/failed";
        }

        return "redirect:/validation/success";
    }

    // 글로벌 오류의 경우 따로 오브젝트 오류 부분만 잡 코드로 작성하는 것이 좋다.
    @PostMapping("/object-error")
    public String objectError(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // 특적 필드 예외가 아닌 전체 예외
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("erros={}", bindingResult);
            return "validation/bean/v1/failed";
        }

        return "redirect:/validation/success";
    }

    // 등록과 수정 시 검증 조건이 다를 때
    // 1. Bean Validation - groups 사용 (잘 사용하지 않음)
    @PostMapping("/groups-save")
    public String groupSave(@Validated(SaveCheck.class) @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // 특적 필드 예외가 아닌 전체 예외
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("erros={}", bindingResult);
            return "validation/bean/v1/failed";
        }

        return "redirect:/validation/success";
    }

    // 등록과 수정 시 검증 조건이 다를 때
    // 1. Bean Validation - groups 사용 (잘 사용하지 않음)
    @PostMapping("/groups-update")
    public String groupUpdate(@Validated(UpdateCheck.class) @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // 특적 필드 예외가 아닌 전체 예외
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("erros={}", bindingResult);
            return "validation/bean/v1/failed";
        }

        return "redirect:/validation/success";
    }

    // 등록과 수정 시 검증 조건이 다를 때
    // 2. ItemSaveForm, ItemUpdateForm과 같이 Form을 나눠서 사용 (주로 사용)
    @PostMapping("/form-save")
    public String formSave(@Validated @ModelAttribute("product") ProductSaveForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // 특적 필드 예외가 아닌 전체 예외
        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("erros={}", bindingResult);
            return "validation/bean/v1/failed";
        }

        Product product = new Product();
        product.setItemName(form.getItemName());
        product.setPrice(form.getPrice());
        product.setQuantity(form.getQuantity());
        // product 활용

        return "redirect:/validation/success";
    }

    // 등록과 수정 시 검증 조건이 다를 때
    // 2. ItemSaveForm, ItemUpdateForm과 같이 Form을 나눠서 사용 (주로 사용)
    @PostMapping("/{productId}/form-update")
    public String formUpdate(@PathVariable Long productId, @Validated @ModelAttribute("product") ProductUpdateForm form, BindingResult bindingResult) {
        // 특적 필드 예외가 아닌 전체 예외
        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("erros={}", bindingResult);
            return "validation/bean/v1/failed";
        }

        Product product = new Product();
        product.setItemName(form.getItemName());
        product.setPrice(form.getPrice());
        product.setQuantity(form.getQuantity());
        // product 활용

        // `@ModelAttribute("product")`로 인해 뷰 템플릿에서 `product`으로 사용한 객체가 MVC Model에 담기게 된다.

        return "redirect:/validation/success";
    }

    // `@Valid`, `@Validated`의 HttpMessageConverter (@RequestBody) 사용
    // `@ModelAttribute`는 각각의 필드 단위로 적용된다.
    //      그래서 특정 필드 타입에 맞지 않는 오류가 발생해도 나머지 필드는 정상 처리할 수 있었다.
    // `HttpMessageConverter (@RequestBody)`는 전체 객체 단위로 적용된다.
    //      그래서 메시지 컨버터의 작동이 성공해서 Item 객체를 만들어야 `@Valid`, `@Validated`가 적용된다.
    @PostMapping("/request-body")
    @ResponseBody
    public Object addProduct(@RequestBody @Validated ProductSaveForm form, BindingResult bindingResult) {
        // HttpMessageConverter 단계에서 문제가 발생할 경우 `@Validated` 혹은 여기까지 진입되지도 않는다.
        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 errors={}", bindingResult);
            // 실제로는 오류 객체에서 필요한 데이터만 뽑아서 API 스펙을 정의하고 그에 맞는 객체를 반환한다.
            return bindingResult.getAllErrors();
        }

        return form;
    }
}
