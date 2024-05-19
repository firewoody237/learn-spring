package firewoody.learnspring.typeconverter.converterinterface.classconverter;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode // 모든 필드를 사용해서 `equals()`와 `hashcode()`를 생성한다.
public class IpPort {

    private String ip;
    private int port;

    public IpPort(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
}
