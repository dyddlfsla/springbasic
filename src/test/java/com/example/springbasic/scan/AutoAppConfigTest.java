package com.example.springbasic.scan;

import com.example.springbasic.AutoAppConfig;
import com.example.springbasic.member.MemberService;
import com.example.springbasic.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoAppConfigTest {

  @Test
  void basicScan() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

    MemberService memberService = ac.getBean(MemberService.class);
    Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

  }

  /*
  * new AnnotationConfigApplicationContext(AutoAppConfig.class);
  * 설정 정보로 AppConfig.class 가 아닌 @ComponentScan 이 적용된 AutoAppConfig.class 를 넘겨준다.
  * 스프링 빈 등록이 이전처럼 잘 되는 것을 확인할 수 있다.
  *
  * - @ComponentScan 과 의존관계 주입이 동작하는 과정
  * 1. @ComponentScan 은 @Component 가 붙은 모든 클래스들을 찾아내어 스프링 빈으로 등록한다.
  * 2. 이때 스프링 빈의 이름은 클래스명을 사용하되 맨처음 글자는 소문자로 변환하여 사용한다. 쉽게 말해, 클래스 이름에 캐멀 케이스를 적용한다.
  *    또는 @Component("이름") 과 같은 형태로 이름을 직접 지정할 수도 있다.
  * 3. 생성자에 @Autowired 를 지정하면, 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아서 의존관계를 주입한다. getBean(클래스타입) 과 동일하다고 할 수 있다.
  *    생성자에 파라미터 수가 많더라도 다 찾아서 주입할 수 있다.
  *
  *
  *
  *
  * */

}
