package firewoody.learnspring.boot.properties.command_line;

import lombok.extern.slf4j.Slf4j;

// argument를 yoyo yaya로 입력

@Slf4j
public class CommandLineV1 {
    public static void main(String[] args) {
        for (String arg : args) {
            log.info("arg {}", arg);
        }
    }
}
