### ThreadLocal
- 해당 쓰레드만 접근할 수 있는 저장소 (`java.lang.ThreadLocal`)
- 테스트 : `ThreadLocalService.java`, `ThreadLocalServiceTest.java`
- `ThreadLocal<T>`를 사용한다.
- **주의사항** : 사용 후 `remove()`를 통해 제거하지 않았을 경우, 새로운 요청이 기존 요청의 ThreadLocal 값을 읽는 위험한 일이 발생할 수 있다.
