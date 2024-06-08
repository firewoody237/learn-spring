package firewoody.learnspring.boot.springproperties.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayService {

    protected final PayClient payClient;

    public void order(int money) {
        payClient.pay(money);
    }
}
