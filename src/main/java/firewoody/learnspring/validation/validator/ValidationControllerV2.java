package firewoody.learnspring.validation.validator;

import firewoody.learnspring.validation.validator.domain.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/validation")
public class ValidationControllerV2 {

    private final ItemValidator itemValidator;

    // `WebDataBinder`로 `Validator`를 등록하면 이 컨트롤러에서는 검증기를 자동으로 적용할 수 있다.
    // `@InitBinder`는 현재 컨트롤러에만 영향을 준다.
    @InitBinder
    public void init(WebDataBinder dataBinder) {
        log.info("init binder {}", dataBinder);
        dataBinder.addValidators(itemValidator);
    }

    // 방법 7. 현재 컨트롤러 전체에 Validator를 사용해야 하는 경우
    // `@Validated` 어노테이션을 통해 검증기를 실행시킬 수 있다.
    // 이 어노테이션이 붙으면 `WebDataBinder`에 등록한 검증기를 찾아서 실행한다.
    // 여러 검증기가 있을 경우, `supports()`가 사용된다.
    @PostMapping("/v7/validation")
    public String validationV7(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // 검증 실패 시
        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "validation/v7/failed";
        }

        // 검증 성공 시
        return "redirect:/validation/success";
    }
}
