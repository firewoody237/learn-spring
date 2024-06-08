package firewoody.learnspring.boot.properties.system_properties;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

// VM옵션과 같은 곳에 옵션을 넣고, 읽을 수 있다.
// 옵션을 넣을 때 `-D` 를 앞에 넣고 사용한다.

@Slf4j
public class JavaSystemProperties {

    public static void main(String[] args) {
        Properties properties = System.getProperties();
        for (Object key : properties.keySet()) {
            log.info("prop {}={}", key, System.getProperty(String.valueOf(key)));
        }
    }
}
