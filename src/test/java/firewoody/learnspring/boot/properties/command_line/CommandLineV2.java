package firewoody.learnspring.boot.properties.command_line;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;

import java.util.List;
import java.util.Set;

// 커맨드라인 인수를 --url=devdb --username=dev_user --password=dev-pw mode=on 입력

@Slf4j
public class CommandLineV2 {

    public static void main(String[] args) {
        for (String arg : args) {
            log.info("arg {}", arg); // 커맨드라인 입력 결과를 그대로 출력한다 --url=devdb
        }

        ApplicationArguments appArgs = new DefaultApplicationArguments(args);
        log.info("SourceArgs = {}", List.of(appArgs.getSourceArgs())); // 커맨드 라인 인수 전부를 출력한다. [--url=devdb, --username=dev_user, --password=dev-pw, mode=on]
        log.info("NonOptionArgs = {}", appArgs.getNonOptionArgs()); // 옵션인수가 아닌(key=value 형식으로 파싱되지 않는) 인수 [mode=on]
        log.info("OptionNames = {}", appArgs.getOptionNames()); // 옵션인수 [password, url, username]

        Set<String> optionNames = appArgs.getOptionNames();
        for (String optionName : optionNames) {
            log.info("option args {}={}", optionName, appArgs.getOptionValues(optionName));
        }

        List<String> url = appArgs.getOptionValues("url");
        List<String> username = appArgs.getOptionValues("username");
        List<String> password = appArgs.getOptionValues("password");
        List<String> mode = appArgs.getOptionValues("mode");
        log.info("url={}", url);
        log.info("username={}", username);
        log.info("password={}", password);
        log.info("mode={}", mode); // 옵션인수가 아니므로 null
    }
}
