package com.example.springbasic.singleton;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springbasic.AppConfig;
import com.example.springbasic.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingletonTest {

  @Test
  void pureContainer() {
    AppConfig appConfig = new AppConfig();
    MemberService memberService1 = appConfig.memberService();
    MemberService memberService2 = appConfig.memberService();

    System.out.println("memberService1 = " + memberService1);
    System.out.println("memberService2 = " + memberService2);

    assertThat(memberService1).isNotSameAs(memberService2);
  }

  @Test
  void singletonServiceTest() {
    SingletonService singletonService1 = SingletonService.getInstance();
    SingletonService singletonService2 = SingletonService.getInstance();

    System.out.println("singletonService1 = " + singletonService1);
    System.out.println("singletonService2 = " + singletonService2);

    assertThat(singletonService1).isSameAs(singletonService2);
  }

  @Test
  void springContainer() {
    AnnotationConfigApplicationContext appConfig =
        new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService1 = appConfig.getBean("memberService", MemberService.class);
    MemberService memberService2 = appConfig.getBean("memberService", MemberService.class);

    System.out.println("memberService1 = " + memberService1);
    System.out.println("memberService2 = " + memberService2);

    assertThat(memberService1).isSameAs(memberService2);
  }


  /*
  * ◆ 싱글톤 컨테이너
  *
  * 스프링 컨테이너는 개발자가 직접 싱글톤 패턴을 적용하지 않아도, 내부적으로 객체 인스턴스를 싱글톤으로 관리한다.
  * 스프링 컨테이너는 싱글톤 컨테이너 역할을 한다. 이렇게 싱글톤 객체를 생성하고 관리하는 기능을 싱글톤 레지스트리라고 한다.
  * 스프링의 이런 기능 덕분에 싱글턴 패턴의 모든 단점을 해결하면서 객체를 싱글톤으로 유지할 수 있다.
  *  - 싱글톤 패턴을 위해 추가적으로 코드를 작성하지 않아도 된다.
  *  - DIP, OCP, 테스트 지원, private 생성자로부터 자유롭게 싱글톤 사용
  *  - 스프링 컨테이너 덕분에 클라이언트의 요청이 올 때마다 객체를 생성하는 것이 아니라, 이미 만들어진 객체를 공유하여 효율적으로 재사용할 수 있다.
  * ※ 스프링의 기본 bean 등록 방식은 싱글톤이지만, 싱글톤 방식만 지원하는 것은 아니다. 요청할 때마다 새로운 객체를 생성해서 반환하도록 할수도 있다.
  *
  * 싱글톤 방식의 단점.
  *
  * 싱글톤 패턴이든, 스프링을 이용한 싱글톤 컨테이너를 사용하든 객체 인스턴스를 하나만 생성해서 공유하는 방식은 설계 시 여러 가지 부분을 주의해야 한다.
  * 하나의 같은 객체 인스턴스를 공유하기 때문에, 싱글톤 객체는 상태를 유지(stateful)하면 안된다.
  * 즉, 싱글톤 객체는 무상태(stateless)하게 설계되어야 한다는 것이다.
  *  - 특정 클라이언트에 의존적인 필드가 존재해서는 안된다.
  *  - 특정 클라이언트가 필드의 값을 변경할 수 없어야 한다.
  *  - 가급적 읽기만 가능해야 한다.
  *  - 필드 대신에 자바에서 공유되지 않는 지역변수, 파라미터, ThreadLocal 등을 사용해야 한다.
  *  - 스프링 bean 의 필드에 공유값을 설정하면 추후 장애를 유발할 수 있다.
  *
  *
  *
  * */


}
