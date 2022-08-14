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

  @Test
  void configurationDeep() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    AppConfig appConfig = ac.getBean(AppConfig.class);

    System.out.println("appConfig = " + appConfig.getClass());
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
* 사실, 스프링은 클래스의 바이트코드를 조작하는 라이브러리를 사용한다.
* ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class); 에서
* 매개변수로 넘긴 AppConfig.class 역시 스프링 bean 이 된다.
* AppConfig 을 조회해서 클래스 정보를 출력해보자.
*
* appConfig = class com.example.springbasic.AppConfig$$EnhancerBySpringCGLIB$$34e66f64
*
* 위의 출력 내용을 잘 살펴보면, 클래스 이름에  ~ CGLIB 라는 것이 붙으면서 복잡한 이름이 되어있는 것을 알 수 있다.
* 사실 이렇게 되는 이유는, 앞서 만든 AppConfig.class 가 직접 스프링 bean 이 되는 것이 아니라
* 스프링이 CGLIB 라는 바이트코드 변환 라이브러리를 사용하여 AppConfig.class 를 상속받은 또 다른 임의의 클래스를 만들고 그 클래스가 스프링 bean 으로 등록된 것이기 때문이다.
*
* 그리고 그 임의의 클래스가 바로 객체의 싱글톤을 보장해준다.
* 아마도 그 임의의 클래스 안에는 다음과 같은 내용이 작성되어 있을 것이다.
*
* @Bean
* public MemberRepository memberRepository() {
*   if (memoryMemberRepository 가 이미 스프링 컨테이너에 등록되어 있으면) {
*     return 스프링 컨테이너에서 객체를 찾아 반환
*   } else {
*     기존 부모 클래스 AppConfig 에 있는 로직을 호출하여 새로운 MemoryMemberRepository 객체를 생성한 후 스프링 컨테이너에 등록 후 반환
*   }
* }
*
* ※ 실제 CGLIB 의 작동 방식은 매우 복잡하다.
*
*
* 이때, @Configuration 을 적용하지 않고 @Bean 만 적용하여 실행해보면 어떻게 될까?
*
* 만약 @Configuration 을 적용하지 않는다면 위 테스트코드 출력 결과는
* appConfig = class com.example.springbasic.AppConfig 이 되고
* 이것은 곧 CGLIB 를 사용하지 않고 순수한 AppConfig.class 가 스프링 bean 으로 등록되었다는 것을 뜻한다.
* 또한, 처음에 생각했던 자바 코드 내용대로 new MemoryMemberRepository 가 3번 호출되어 객체가 3개 만들어지는 것도 확인할 수 있다.
*
* 결론:
* 스프링 컨테이너가 객체의 싱글톤을 보장하기 위해서는 CGLIB 라는 기술이 사용되고 있고
* 이 기술은 @Configuration 이 적용된 클래스에만 사용된다는 것이다.
* 따라서 @Configuration 을 사용하지 않고 @Bean 만 적용한다면 해당 객체가 스프링 bean 이 되긴 하겠지만 싱글톤을 보장하지는 않을 것이다.
* 스프링 설정 정보를 담고 있는 클래스에는 항상 @Configuration 을 사용하자.
*
*
*
*
*
*
* */
