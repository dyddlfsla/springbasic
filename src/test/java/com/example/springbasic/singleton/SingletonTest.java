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
  * 싱글톤 컨테이너
  *
  * 스프링 컨테이너는 개발자가 직접 싱글톤 패턴을 적용하지 않아도, 내부적으로 객체 인스턴스를 싱글톤으로 관리한다.
  * 스프링 컨테이너는 싱글톤 컨테이너 역할을 한다. 이렇게 싱글톤 객체를 생성하고 관리하는 기능을 싱글톤 레지스트리라고 한다.
  *
  *
  *
  * */


}
