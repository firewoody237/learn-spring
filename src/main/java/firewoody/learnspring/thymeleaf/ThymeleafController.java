package firewoody.learnspring.thymeleaf;

import firewoody.learnspring.thymeleaf.domain.DeliveryCode;
import firewoody.learnspring.thymeleaf.domain.Item;
import firewoody.learnspring.thymeleaf.domain.ItemType;
import firewoody.learnspring.thymeleaf.domain.User;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafController {

    @GetMapping("/text-basic")
    public String textBasic(Model model) {
        model.addAttribute("data", "Hello Spring");
        return "thymeleaf/text-basic";
    }

    @GetMapping("/text-unescaped")
    public String textUnescaped(Model model) {
        model.addAttribute("data", "Hello <b>String</b>");
        return "thymeleaf/text-unescaped";
    }

    @GetMapping("/variable")
    public String variable(Model model) {
        // Thymeleaf에서 객체를 사용하는 방법
        User userA = new User("userA", 10);
        User userB = new User("userB", 20);

        List<User> userList = new ArrayList<>();
        userList.add(userA);
        userList.add(userB);

        Map<String, User> userMap = new HashMap<>();
        userMap.put("userA", userA);
        userMap.put("userB", userB);

        model.addAttribute("user", userA);
        model.addAttribute("userList", userList);
        model.addAttribute("userMap", userMap);

        return "thymeleaf/variable";
    }

    @GetMapping("/basic-objects")
    public String basicObjects(HttpSession session) {
        // Thymeleaf에서 기본적으로 지원하는 객체들
        session.setAttribute("sessionData", "Hello Session");
        return "thymeleaf/basic-objects";
    }

    @Component("helloBean")
    static class HelloBean {
        public String hello(String data) {
            return "Hello " + data;
        }
    }

    @GetMapping("/date")
    public String date(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "thymeleaf/date";
    }

    @GetMapping("/link")
    public String link(Model model) {
        model.addAttribute("param1", "data1");
        model.addAttribute("param2", "data2");
        return "thymeleaf/link";
    }

    @GetMapping("/literal")
    public String literal(Model model) {
        model.addAttribute("data", "Spring!");
        return "thymeleaf/literal";
    }

    @GetMapping("/operation")
    public String operation(Model model) {
        model.addAttribute("nullData", null);
        model.addAttribute("data", "Spring!");
        return "thymeleaf/operation";
    }

    @GetMapping("/attribute")
    public String attribute() {
        return "thymeleaf/attribute";
    }

    @GetMapping("/each")
    public String each(Model model) {
        List<User> list = new ArrayList<>();
        list.add(new User("userA", 10));
        list.add(new User("userB", 20));
        list.add(new User("userC", 30));
        model.addAttribute("users", list);

        return "thymeleaf/each";
    }

    @GetMapping("/condition")
    public String condition(Model model) {
        List<User> list = new ArrayList<>();
        list.add(new User("userA", 10));
        list.add(new User("userB", 20));
        list.add(new User("userC", 30));
        model.addAttribute("users", list);

        return "thymeleaf/condition";
    }

    @GetMapping("/comments")
    public String comments(Model model) {
        model.addAttribute("data", "Spring!");
        return "thymeleaf/comments";
    }

    @GetMapping("/block")
    public String block(Model model) {
        List<User> list = new ArrayList<>();
        list.add(new User("userA", 10));
        list.add(new User("userB", 20));
        list.add(new User("userC", 30));
        model.addAttribute("users", list);

        return "thymeleaf/block";
    }

    @GetMapping("/template/fragment")
    public String template() {
        return "thymeleaf/template/fragment/fragmentMain";
    }

    @GetMapping("/template/layout")
    public String layout() {
        return "thymeleaf/template/layout/layoutMain";
    }

    @GetMapping("/template/layoutExtend")
    public String layoutExtends() {
        return "thymeleaf/template/layoutExtend/layoutExtendMain";
    }

    @GetMapping("/input-form")
    public String inputForm(Model model) {
        // 체크 박스
        // 멀티 체크 박스
        // 라디오 버튼
        // 셀렉트
        model.addAttribute("item", new Item());
        return "thymeleaf/inputForm";
    }

    // `@ModelAttribute`를 사용하면, Controller의 Model에 자동으로 해당 요소를 추가한다.
    @ModelAttribute("regions")
    public Map<String, String> regions() {
        // 체크 박스 용도
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        return regions;
    }

    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {
        // 라디오 버튼 용도
        return ItemType.values();
    }

    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {
        // 셀렉트 용도
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
        return deliveryCodes;
    }
}
