### 핵심기능
- WAS : 웹 서버를 내장해서 별도의 웹 서버 설치 X
- 라이브러리 관리 : 손쉬운 빌드, 스타터 종속성
- 자동 구성 : 프로젝트 시작에 필요한 스프링과 외부 라이브러리의 빈을 자동 등록
- 외부 설정 : 환경에 따라 달렺야 하는 외부 설정 공통화
- 프로덕션 준비 : 모니터링을 위한 메트릭, 상태 확인 기능 제공

### 웹 서버와 서블릿 컨테이너
- 과거
  - 서버에 톰캣(혹은 WAS)를 설치 후 서블릿 스펙에 맞춰 코드를 작성하고, WAR형식으로 빌드하여 war 파일을 만들었다.
  - 그리고 이 war파일을 WAS에 옮겨 배포하였다.
- 현재
  - 스프링 부트가 내장 톰캣을 포함하고 있다.
  - 개발자는 코드를 작성하고 JAR로 빌드 후 JAR를 원하는 위치에서 실행시키면 WAS도 함께 실행된다.
- JAR
  - 여러 클래스와 리소스를 묶어 `Java Archive`라는 압축 파일로 만든 것
  - JVM위에서 직접 실행되거나 라이브러리로 제공할 수 있음
  - 실행 : `java -jar abc.jar`
- WAR
  - 웹 애플리케이션 배포용 파일
  - WAR는 웹 애플리케이션 서버 위에서 실행됨
  - 구조를 지켜야 함
    - `WEB-INF`
      - `classes` : 실행 클래스 모음
      - `lib` : 라이브러리 모음
      - `web.xml` : 웹 서버 배치 설정 파일
    - `index.html` : 정적 리소스 

### 서블릿 컨테이너 초기화
- 서블릿은 `ServletContainerInitializer`라는 초기화 인터페이스를 제공, 서블릿 컨테이너는 실행 시점에 `onStartup()` 호출
  - 실행 인터페이스를 `resources/META-INF/services/jakarta.servlet.ServletContainerInitializer`에 등록 해야 함
- 애플리케이션 등록 방법
  1. `AppInit` 인터페이스의 `onStartup()`에 구현하는 방법(프로그래밍 방식)
    - ![ServletContainerInitializer + AppInit](./images/image001.png)
    - `@HandlesTypes`로 애플리케이션 등록 필요
  2. `@WebServlet` 애노테이션을 사용하는 방법 -> 유연하지 못하다는 단점 존재
- 단계
  1. `ServletContainerIntializer`를 등록
  2. `ServletContainerIntializer`코드에 `@HandlesTypes`로 초기화할 애플리케이션 추가
  3. `ServletContainerInitializer`를 서블릿 컨테이너 초기화 클래스 경로로 등록

### 스프링 MVC 서블릿 컨테이너 초기화 지원
- `WebApplicationInitializer` 인터페이스를 구현하면 된다.
- `spring-web` 라이브러리에서 서블릿 컨테이너 초기화를 위한 파일 등록을 하고 있다.(`SpringServletContainerInitializer`)
  - `SpringServletContainerInitializer`의 내부를 보면, `@HandlesTYpes(WebApplicationInitializer.class)` 코드가 존재한다.
  - ![스프링 컨테이너 초기화](./images/image002.png)
    - 초록색은 스프링이 이미 만들어서 제공하는 영역

### 스프링 부트와 내장 톰캣
- 기존 방식은, 톰캣을 별도로 설치해야 하고, 개발 환경이 복잡하며, 배포가 복잡하고, 톰캣 버전에 대한 민감성이 큼
- 내장 톰캣 라이브러리 : `implementation 'org.apache.tomcat.embed:tomcat-embed-core:10.1.5'`
- 내장 톰캣 기본 : `EmbedTomcatServletMain.java`
- 내장 톰캣 + 스프링 : `EmbedTomcatSpringMain.java`
- 내장 톰캣 빌드, 배포
  - `META-INF/MANIFEST.MF` 파일에 실행할 `main()` 메서드의 클래스를 지정, `jar` 형식으로 빌드
  - **중요** : `jar` 파일은 `jar` 파일을 포함할 수 없다. -> `FatJar` 방식 사용
  - `FatJar` : `Jar`안의 클래스들을 새로 만드는 `Jar`에 포함시키는 것
    - 단점
      - 어떤 라이브러리가 포함되어 있는지 알기 어렵다
      - 파일명 중복을 해결할 수 없다
- 간단하게 만들어보는 스프링 부트 시작점 : `MySpringBootMain.java` + `MySpringApplication.java` + `MySpringBootApplication.java`
  - 핵심
    - 스프링 컨테이너를 생성 : `ServletWebServerApplicationContextFactory.java` -> `AnnotationConfigServletWebServerApplicationContext()`(컨테이너 생성)
    - WAS(내장 톰캣)를 생성 : `TomcatServletWebServerFactory.java`의 `getWebServrer` -> `new Tomcat()`으로 톰캣을 만들고, 커넥터를 등록하고, 디스패처 서블릿을 등록
- 스프링 부트의 JAR(`Executable Jar`)를 푼 결과
  - ![스프링 부트 JAR](./images/image003.png)
- 스프링 부트는 "Executable Jar"를 사용한다.
  - `jar` 내부에 `jar`를 포함할 수 있다. -> `Fat Jar`의 문제점을 해결
  - 실행 동작 순서
    1. `META-INF/MANIFEST.MF` 파일을 찾는다.
    2. 여기에 있는 `Main-Class`를 읽어서 `main()` 메서드를 실행한다. (`JarLauncher`)
    3. `JarLauncher`가 특별한 구조에 맞게 클래스 정보를 읽어준다. (`BOOT-INF/classes/`, `BOOT-INF/lib/` 인식)
    4. `JarLauncher`의 작업이 끝난 후, `META-INF/MANIFEST.MF`에서 `Start-Class`의 `main()`를 실행한다.

### 스프링 부트 스타터와 라이브러리 관리
#### 라이브러리 관리
- 라이브러리를 직접 넣으면, 버전에 따른 관리가 발생
- 스프링 부트는 `io.spring.dependency-management` 플러그인으로 버전을 관리
- `bom(bill of materials)`정보에 버전이 명시되어 있고, 이 파일을 사용하여 버전이 관리 됨
- 스프링 부트의 버전에 맞게 자동으로 버전 관리
#### 스타터
- `starter`는 의존성을 모아둔 세트
- [스타터 목록](https://docs.spring.io/spring-boot/reference/using/build-systems.html#using.build-systems.starters)
- 외부 라이브러리의 버전을 변경하고 싶을 때(build.gradle) : ext['tomcat.version'] = '10.1.4' 
  - [버전 속성 값](https://docs.spring.io/spring-boot/appendix/dependency-versions/properties.html#appendix.dependency-versions.properties)

### 자동 구성(Auto Configuration)
- 스프링 부트는 일반적으로 자주 사용하는 빈들을 자동으로 등록해 준다.
- `spring-boot-autoconfigure` 프로젝트에 자동으로 등록하는 빈들이 존재한다.
  - ex. `JdbcTemplateAutoConfiguration.java`
  - `@ConditionalXXX`는 주로 특정 조건에 맞을 때 설정이 동작하도록 한다
  - 내부 애노테이션
    - `@AutoConfiguration` : 자동 구성을 위해 등록
      - 내부에 `@Configuration` 존재
      - `after=XXXAutoConfiguration.class`를 통해 순서 조정 가능
    - `@ConditionalOnClass({XXX.class})` : 이 클래스가 있는 경우에만 설정이 동작
    - `@Import` : 자바 설정을 추가 시 사용
    - `@ConditionalOnMissingBean(JdbcOperations.class)`
      - `JdbcOptions` 빈이 없을 때 동작
      - 개발자가 빈을 등록하지 않으면 자동으로 등록하기 위해 사용

#### `@Conditional`
- 스프링 프레임워크의 기능
- 특정 상황일 때만 특정 빈들을 등록해서 사용하도록 도와 준다.
- `@Condition`을 구현하는 클래스를 활용하여 조건을 지정할 수 있다.

#### `@ConditionalOnXxx`
- 스프링 부트의 기능
- 개발자가 편하게 사용할 수 있도록 스프링에서 제공
- 대표적인 몇가지
  - `@ConditionalOnClass`, `@ConditionalOnMissingClass` : 클래스가 있는 경우 동작O/X
  - `@ConditionalOnBean`, `@ConditionalOnMissingBean` : 빈이 등록되어있는 경우 동작O/X
  - `@ConditionalOnProperty` : 환경 정보가 있는 경우 동작
  - `@ConditionalOnResource` : 리소스가 있는 경우 동작
  - `@ConditionalOnWebApplication`, `@ConditionalOnNotWebApplication` : 웹 애플리케이션인 경우 동작
  - `@ConditionalOnExpression` : SpEL 표현식에 만족하는 경우 동작
- [ConditionalOn 가이드](https://docs.spring.io/spring-boot/reference/features/developing-auto-configuration.html#features.developing-auto-configuration.condition-annotations)

#### 직접 만든 라이브러리를 자동 구성에 추가
##### 라이브러리 프로젝트
- `MemoryAutoConfig.java`처럼, `@AutoConfiguration`으로 자동 구성설정임을 선언
- `src/main/resources/META-INF/spring/org.springframework.boot.autoconfiguration.AutoConfiguration.imports` 파일에 자동구성을 등록 해 주어야 한다.
  - 스프링 부트는 시작 지점에 위 파일의 정보를 읽어서 자동 구성으로 사용한다. (그 결과 내부의 `MemoryAutoConfig`가 자동으로 실행 됨)
##### 사용하는 프로젝트
- 추가하려는 jar파일을 사용하려는 프로젝트에 추가
- `build.gradle`의 `dependencies`에, `implementation files('libs/memory-v1.jar')`처럼 라이브러리 파일을 추가
- 자동 설정을 했기 때문에, 조건에 맞으면 라이브러리의 빈이 등록 됨

#### 스프링 부트 자동 구성 동작
- `@Import`
  - 정적인 방법
    - ex. `@Import({AConfig.class})`
    - 설정 사용할 대상을 동적으로 변경 불가
  - 동적인 방법
    - `ImportSelector`를 구현한 클래스를 동적으로 등록
- 구성 동작 순서
  1. `@SpringBootApplication` : 여러 설정 정보들이 존재
     - 이 컴포넌트 스캔에는 `@AutoConfiguration`을 제외하는 `AutoConfigurationExcludeFilter`가 존재 -> `META-INF/spring/org.springframework.boot.autoconfiguration.AutoConfiguration.imports`  파일에 등록해서 사용해야 함
  2. `@EnableAutoConfiguration` : 자동 구성 활성화
     - 내부에 `@Import(AutoConfigurationImportSelector.class)`가 존재
  3. `@Import(AutoConfigurationImportSelector.class)`
     - `AutoConfigurationImportSelector`는 `META-INF/spring/org.springframework.boot.autoconfiguration.AutoConfiguration.imports` 경로의 파일을 확인
     - 파일 내에 있는 설정 정보가 스프링 컨테이너에 등록되고 사용 됨

### 외부설정과 프로필
#### 외부설정 4가지
1. OS 환경 변수 : OS에서 지원하는 외부 설정, 해당 OS를 사용하는 모든 프로세스에서 사용
   - 테스트 : `OsEnv.java`
   - 전역변수의 느낌이기 때문에 해당 자바 프로그램만 사용하지 않는다.
2. 자바 시스템 속성 : 자바에서 지원하는 외부 설정, 해당 JVM 안에서 사용
   - 테스트 : `JavaSystemProperties.java`
3. 자바 커맨드 라인 인수 : 커맨드 라인에서 전달하는 외부 설정, 실행 시 `main(args)` 메서드에서 사용
   - `java -jar app.jar dataA dataB`와 같이 사용
   - 테스트 : `CommandLineV1.java`, `CommandLineV2.java`
   - `ApplicationArguments` 빈을 주입받아 사용할 수 있다.(스프링 부트가 자동으로 등록)
     - 테스트 : `CommandLineBean.java`
4. 외부 설정 : 프로그램에서 외부 파일을 직접 읽어서 사용

#### 스프링 외부 설정
##### 외부설정 추상화
![외부 설정 추상화](./images/image004.png)
- `Enviroment`와 `PropertySource` 추상화를 사용
  - `Environment` : 특정 외부 설정에 종속되지 않고, 일관성 있게 `key=value` 형식으로 외부 설정에 접근
  - 더 유연하거나, 범위가 좁은 설정이 우선순위를 가진다.
- 스프링 부트는 `application.properties`와 같은 설정 데이터를 통해 `PropertySource`의 구현체를 제공
  - `Environment`를 통해 조회가 가능
##### 환결별 외부설정 분리
- 환경별 설정 데이터를 나눠서, 환경에 따라 빌드되도록 함
  - ![내부 설정 파일 조회](./images/image005.png)
  - `application-dev.properties`, `application-prod.properties`
  - `spring.profiles.active`에 {환경값}을 넣으면 `application-{환경값}.properties`를 설정 데이터로 사용한다.
    - ex. `--spring.profiles.active=dev`, `-Dspring.profiles.active=dev`
##### 내부 파일 일원화
- 환경별로 `application.properties`를 `#---` 혹은 `!---`로 구분 가능
- `application.yml`의 경우 `---`로 구분
- `spring.config.activate.on-profile`로 분리된 구분의 환경값을 세팅
- 일치하는 환경값이 없을 때, 기본값으로 적용 가능
  - 스프링은 문서를 위에서 아래로 순서대로 읽으면서 설정
  - 가장 처음에 프로필 정보에 `on-profile`을 설정하지 않으면, 프로필 지정과 무관하게 사용 됨(default)
  - 단순하게 위에서 값을 읽으며 내려가기 때문에, 가장 밑에 프로필 없는 설정값이 있다면 그 값은 항상 설정되게 됨
  - 위에서 아래로 내려가며 일치하는 속성값에 대해 값을 덮어 씀
- [설정 파일 공식문서](https://docs.spring.io/spring-boot/reference/features/external-config.html#features.external-config)
- [설정 우선순위](https://docs.spring.io/spring-boot/reference/features/external-config.html#features.external-config.files)
- 자주 사용하는 우선순위
  1. 설정 데이터(`application.properties`)
  2. OS 환경변수
  3. 자바 시스템 속성
  4. 커맨드 라인 옵션 인수
  5. `@TestPropertySource` (테스트 시)
- 설정 데이터 우선순위
  1. jar 내부 `application.properties`
  2. jar 내부 `application-{profile}.properties`
  3. jar 외부 `application.properties`
  4. jar 외부 `application-{profile}.properties`
- 보통 `application.properties`를 사용하다가 일부 속성을 변경할 필요가 있다면 높은 우선순위를 가지는 **자바 시스템 속성**이나 **커맨드 라인 옵션 인수**를 사용
