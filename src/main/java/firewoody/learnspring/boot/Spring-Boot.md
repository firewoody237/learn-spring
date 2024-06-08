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
