package firewoody.learnspring.validation;

import firewoody.learnspring.validation.validator.domain.Item;
import firewoody.learnspring.validation.validator.ItemValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/validation")
public class ValidationController {

    private final ItemValidator itemValidator;

    @GetMapping("/success")
    public String success() {
        return "validation/success";
    }

    // 방법 1. 순수 Map으로 넣는 방법
    @PostMapping("/v1/validation")
    public String validationV1(@ModelAttribute Item item, RedirectAttributes redirectAttributes, Model model) {

        Map<String, String> errors = new HashMap<>();

        if (!StringUtils.hasText(item.getItemName())) {
            errors.put("itemName", "상품 이름은 필수입니다.");
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.put("price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.");
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
        }

        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice);
            }
        }

        // 검증 실패 시
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "validation/v1/failed";
        }

        // 검증 성공 시
        return "redirect:/validation/success";
    }

    // 방벙 2. BindingResult를 사용하는 방법
    @PostMapping("/v2/validation")
    public String validationV2(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // `BindingResult`를 사용하면, `@ModelAttribute`에 데이터 바인딩 시 오류가 발생해도 컨트롤러가 호출된다. (FieldError)
        //      타입 오류로 바인딩에 실패하면 스프링은 FieldError를 생성하면서 사용자가 입력한 값을 넣어둔다. 그리고 해당 오류를 `BindingResult`에 담아서 컨트롤러를 호출한다.
        // `BindingResult`는 검증할 대상 바로 다음에 나와야 한다.(파라미터 순서 주의)
        // `BindingResult`는 `Errors` 인터페이스를 상속받으므로, `Errors`를 사용해도 된다.
        if (!StringUtils.hasText(item.getItemName())) {
            // FieldError()
            // objectName : 오류가 발생한 객체 이름
            // field : 오류 필드
            // rejectedValue : 사용자가 입력한 값(거절된 값)
            // bindingFailure : 타입 오류 같은 바인딩 실패인지, 검증 실패인지 구분 값
            // codes : 메시지 코드
            // arguments : 메시지에서 사용하는 인자
            // defaultMessage : 기본 오류 메시지
            bindingResult.addError( new FieldError("item", "itemName", item.getItemName(), false, null, null, "상품 이름은 필수입니다."));
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError( new FieldError("item", "price", item.getPrice(), false, null, null, "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError( new FieldError("item", "quantity", item.getQuantity(), false, null, null, "수량은 최대 9,999 까지 허용합니다."));
        }

        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError( new ObjectError("item", null, null, "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
            }
        }

        // 검증 실패 시
        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "validation/v2/failed";
        }

        // 검증 성공 시
        return "redirect:/validation/success";
    }

    // 방법 3. BindingResult를 사용하면서, errors.properties를 사용하는 방법
    @PostMapping("/v3/validation")
    public String validationV3(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // codes에 errors.properties의 코드를 넣을 수 있다.
        if (!StringUtils.hasText(item.getItemName())) {
            // FieldError()
            // objectName : 오류가 발생한 객체 이름
            // field : 오류 필드
            // rejectedValue : 사용자가 입력한 값(거절된 값)
            // bindingFailure : 타입 오류 같은 바인딩 실패인지, 검증 실패인지 구분 값
            // codes : 메시지 코드
            // arguments : 메시지에서 사용하는 인자
            // defaultMessage : 기본 오류 메시지
            bindingResult.addError( new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName"}, null, null));
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError( new FieldError("item", "price", item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError( new FieldError("item", "quantity", item.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]{9999}, null));
        }

        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError( new ObjectError("item", new String[]{"totalPriceMin"}, new Object[]{10000, resultPrice}, null));
            }
        }

        // 검증 실패 시
        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "validation/v3/failed";
        }

        // 검증 성공 시
        return "redirect:/validation/success";
    }

    // 방법 4. BindingResult의 rejectValue 등을 사용하는 방법
    @PostMapping("/v4/validation")
    public String validationV4(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // BindingResult는 검증해야 하는 target 뒤에 오므로, target 객체를 이미 알고 있다.
        // FieldError, ObjectError는 MessageCodesResolver를 통해 생성된 순서대로 오류 코드를 보관한다.

        log.info("objectName={}", bindingResult.getObjectName());
        log.info("target={}", bindingResult.getTarget());

        if (!StringUtils.hasText(item.getItemName())) {
            // rejectValue()
            // field : 오류 필드명
            // errorCode : 오류 코드(messageResolver를 위한 오류 코드 - 테스트 코드 참고)
            // errorArgs : 오류 메시지에서 {0}을 치환하기 위한 값
            // defaultMessage : 오류 메시지를 찾을 수 없을 때 사용하는 기본 메시지
            bindingResult.rejectValue("itemName", "required");
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        // 검증 실패 시
        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "validation/v4/failed";
        }

        // 검증 성공 시
        return "redirect:/validation/success";
    }

    // 방법 5. ValidationUtils를 사용하는 방법 (Empty, 공백과 같은 단순한 기능만 제공)
    @PostMapping("/v5/validation")
    public String validationV5(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (!StringUtils.hasText(item.getItemName())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "itemName", "required");
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        // 검증 실패 시
        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "validation/v5/failed";
        }

        // 검증 성공 시
        return "redirect:/validation/success";
    }

    // 방법 6. 직접 만든 Validator를 사용하는 방법
    @PostMapping("/v6/validation")
    public String validationV6(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // 복잡한 Validation이 필요할 경우 이 방법을 사용해야 함
        itemValidator.validate(item, bindingResult);

        // 검증 실패 시
        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "validation/v6/failed";
        }

        // 검증 성공 시
        return "redirect:/validation/success";
    }
}
