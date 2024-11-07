## 🔧 기능 구현 목록

### 서비스 
- 주문 서비스 ( 최종 주문을 만들어주기까지 해주면됌) input output 다필요하지만.
- 영수증 서비스 ( 최종 주문을 받아서 영수증을 발행해줌) 여긴 output만 있어도 괜찮을거 같다.

### 주문

- 주문은 만들어진 시간을 담고 있어야 한다
- 주문의 요구들을 담고 있다.
- 주문은 최소 한개의 요구라도 존재해야 한다

### 요구

- 요구는 상품의 이름과 수량이 존재한다

### 상품 카테고리
- 상품 카테고리는 상품을 가지고 있다.

### 편의점
- 편의점에는 상품 카테고리들이 있다.

### 상품

- 상품은 이름, 가격, 프로모션 , 프로모션 재고 수량, 일반 재고 수량을 가진다 
- 상품을 이름을 기반으로 같은 상품이라고 인식할 수 있다
- 상품은 프로모션 주문을 처리하여 재고를 줄일 수 있어야 한다
- 상품은 반드시 이름.가격,재고 수량, Promotion이 존재해야 한다. 상품의 정체성은 이 값들의 존재다
- 상품은 재고와 관련 로직을 Promotion 인터페이스에게 위임한다.
- ~~상품은 수량을 리필할수도 있어야 한다는 넣지말자~~

### 프로모션

- 프로 모션은 이름을 가지고 있어야 한다
- 프로 모션은 재고를 주는 로직이 있어야한다


객체 자체의 의미가 없는 값 객체는 getter를 열어준다.

---
## VO
### Amount( 주문, 재고) 수량

- 재고 수량은 0보다 작아질 수 없다
- 재고 수량은 int이여야 한다
- getter가 있다
- 재고 수량은 늘리고 줄일 수 있어야 한다
- 재고보다 높은 숫자를 줄일려고 하면 error가 난다

### 상품의 이름

- 상품의 이름은 반드시 존재해야한다
- 상품의 이름이 공백만으로 존재할 수는 없다
- 이름의 제한을 두자 10자
- getter가 있다

### 돈

- 가격은 int 이여야 한다 
- int 이므로 null인 것에 대한 점검은 불필요하다
- 가격은 0보다 작을 수 없다(이건 가격만 가격은 0이 안돼) 
- getter가 있다

### 

---

## ✅ FeedBack 체크 리스트

## 1️⃣ 가독성

- [ ]  작명의 의미 전달력 + Number 같이 다른것과 겹치는거 사용 x
- [ ]  코드 포맷팅 (IntelliJ IDEA: ⌥⌘L)
- [ ]  요약 설명은 Comment에, ReadMe는 꼼꼼히하고 상시 업데이트
- [ ]  구현 순서를 깔끔하게 하였는가

## 2️⃣ 코딩 습관

- [ ]  필요하다면 싱글톤을 적용하기
- [ ]  안 쓰는 Importation 삭제
- [ ]  변수명에 자료형을 쓰지 않는다
- [ ]  변수명에 불용어를 넣지 않는다
- [ ]  if 안에 부정문을 줄여보자
- [ ]  와일드 카드를 지양한다
- [ ]  commit의 가독성을 놓치지 말자

## 3️⃣ 설계 중점 사항

- [ ]  Config를 써보자
- [ ]  참조로 추적되는 데이터형 getter 지양 → DTO 사용 (record 타입), 방어적 복사 대안, 수정 불가 객체 return
- [ ]  DTO는 의미있게 사용한다(+ Dto 내부에서 생성 처리)
- [ ]  구현 방식에 집중해서 과제 자체에 대한 예외들을 빼먹지 말 것
- [ ]  상수가 연관성이 있다면 enum 사용
- [ ]  메서드는 한 가지 일만 처리한다 (10 줄 이하 이건 메인 코드도 동일)
- [ ]  마지막 프로그래밍 요구 사항 지켰는지 check (예외를 잘보자)
- [ ]  많이 의존 당한다면 interface의 근거가 충분하다
- [ ]  객체는 객체에서의 역할에 충실하고 나머지는 service에 존재한다
- [ ]  validation은 객체 자체의 정체성, 유효성 검사, 파싱, 그리고 정제를 분리해서 생각한다.
- [ ]  view의 역할을 침범하지 말자 ex) 포메팅
- [ ]  getter를 되도록 이면 지양한다 캡슐화에 조금 더 신경 써야 할 것 같다.
- [ ]  필드를 줄이기 위해 노력하자

## 4️⃣ Test

- [ ]  test coverage check (comment에 첨부해보기?)
- [ ]  테스트 자체에서 메서드를 만들거나 여러 기능을 사용해 중복 테스트 줄이기
- [ ]  2주차 feedback 테스트 메서드 참고 자료 활용
- [ ]  UI(System.out, System.in, Scanner) 로직은 제외한다 (굳이 사용할 필요가 없었다)
- [ ]  Interface 적극 사용 → 테스트 대역이라면 따로 분리하자
- [ ]  테스트를 하기 용이하게 코드를 작성한다
- [ ]  TDD에 대한 시도를 해본다
- [ ]  공백 테스트는 \n \t 같은 것도 하자
- [ ]  예외 테스트를 집중적으로 하자
- [ ]  테스트 코드도 가독성을 신경써야 한다

## 5️⃣ 기타

- [ ]  stackOverflow를 고려한다
- [ ]  enum Map을 고려한다
- [ ]  Java 8의 인터페이스 default 기능을 고려한다 (like 상속)
- [ ]  필요하다면 상속을 사용해보자
- [ ]  꼭 필요한 경우가 아니라면 상수 처리할 필요가 없을 것 같다
- [ ]  숫자 구분자 1_000_000 를 사용하면 가독성이 좋다
- [ ]  서비스가 필드를 가지지 않도록 만들어보자
- [ ]  줄 바꿈이 필요하다면 `System.lineSeparator()` 을 써보자
- [ ]  예외 처리를 흐름 제어용으로 사용하지 말 것


존재하지 않는 상품에 대한 예외를 추가해야할 것


놀랍게도 getter를 닫는것만으로 설계가 학정이 됐다

중요한것은 기본에 출실하는 것

적어도 값 객체를 인터페이스화 하는 것은 미친 짓이다

큰 부분이 아니라 명확한 작은 부분을 특정할 수 있어야 시작하기 용이하다.
