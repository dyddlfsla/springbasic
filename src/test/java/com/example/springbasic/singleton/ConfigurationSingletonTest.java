package com.example.springbasic.singleton;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springbasic.AppConfig;
import com.example.springbasic.member.MemberRepository;
import com.example.springbasic.member.MemberServiceImpl;
import com.example.springbasic.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

  @Test
  void configurationTest() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
    OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
    MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

    MemberRepository memberRepository1 = memberService.getMemberRepository();
    MemberRepository memberRepository2 = orderService.getMemberRepository();

    System.out.println("memberService → memberRepository = " + memberRepository1);
    System.out.println("orderService → memberRepository = " + memberRepository2);
    System.out.println("memberRepository = " + memberRepository);

    assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
    assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);

  }

}


/*
* ◆ @Configuration 과 싱글톤
*
* 다음 AppConfig 코드를 다시 보자.
* memberService() 메서드는 결과적으로 new MemoryMemberRepository() 를 호출한다.
* orderService() 메서드 역시 new MemoryMemberRepository() 를 호출한다.
* new 연산자는 객체를 생성시키는 연산자이므로 자바 코드로 본다면 결국 2개의 MemoryMemberRepository 객체가 생성되는 것이고
* 이것은 앞서 설명한 스프링의 싱글톤 패턴과 모순되는 것처럼 보인다.
*
* 그러나 테스트 코드를 통해 확인해보면, 실제 생성된 MemoryMemberRepository 객체는 하나뿐이며 이 객체가 MemberServiceImpl 과 OrderServiceImpl 객체에서 공유되어 사용되고 있다.
* 어떻게 이런 일이 가능한 것일까?
*
*
*
* */
