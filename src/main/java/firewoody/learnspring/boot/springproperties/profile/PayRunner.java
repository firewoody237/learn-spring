package firewoody.learnspring.boot.springproperties.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PayRunner implements ApplicationRunner {

    private final PayService payService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        payService.order(1000);
    }
}
