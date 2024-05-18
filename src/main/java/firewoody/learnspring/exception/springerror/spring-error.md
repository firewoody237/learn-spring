- 스프링 부트는 `ErrorPage`를 자동으로 등록한다.
  - `/error`라는 경로로 기본 오류 페이지를 설정한다.
  - `ErrorMvcAutoConfiguration`이 오류 페이지를 자동으로 등록한다.
- `BasicErrorController`라는 스프링 컨트롤러를 자동으로 등록한다.
  - `ErrorPage`에서 등록한 `/error`를 매핑해서 처리하는 컨트롤러다.
- 개발자는 오류 페이지만 등록하면 된다.

### `BasicErrorController`의 처리 순서
1. 뷰 템플릿
   - `resources/templates/error/500.html`
   - `resources/templates/error/5xx.html`
2. 정적 리소스
   - `resources/static/error/400.html`
   - `resources/static/error/404.html`
   - `resources/static/error/4xx.html`
3. 적용 대상이 없을 떄 뷰 이름
   - `resources/static/error.html`
