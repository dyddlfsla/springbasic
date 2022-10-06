package com.example.springbasic;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class),
    basePackages = "com.example.springbasic.member")
public class AutoAppConfig {

//  @Bean(name = "memoryMemberRepository")
//  MemberRepository memberRepository() {
//    return new MemoryMemberRepository();
//  }


  /*
  * ◆ 컴포넌트 스캔 - 컴포넌트 스캔과 의존관계 자동 주입
  *
  * 지금까지 스프링 빈을 등록할 때는 자바 코드의 @Bean 을 이용하거나 XML 의 <bean> 을 통해서 설정 정보에 직접 등록할 빈을 나열했다.
  * 그러나 이렇게 등록해야할 스프링 빈이 점점 늘어나 수십, 수백개가 되면 일일이 등록하기도 귀찮고, 설정 정보도 커지고, 스프링 빈을 누락될수도 있는 문제가 발생한다.
  * 그렇기 때문에 스프링 프레임워크에서는 설정 정보가 없어도 자동으로 스프링 빈을 등록할 수 있도록 컴포넌트 스캔이라는 기능을 제공한다.
  * 또한 의존 관계를 표현할 수 있도록 @Autowired 라는 기능도 제공한다.
  *
  * 컴포넌트 스캔을 사용하려면 설정 정보 클래스에 @ComponentScan 이라는 애너테이션을 붙여주면 된다.
  * 기존의 AppConfig 와는 다르게 @Bean 을 이용한 코드가 존재하지 않는다.
  * ※ 원래대로라면 @Configuration 이 붙은 클래스도 스프링 빈으로 등록된다.
  *    그러나 기존의 예제 코드를 최대한 남기기 위해 excludeFilter 를 사용하여 기존의 AppConfig 클래스와 그 내용이 스프링 빈으로 등록되지 않게 하였다.
  *
  * @ComponentScan 은 말 그대로 @Component 가 붙은 클래스들을 모두 찾아서 스프링 빈으로 등록한다.
  *  스프링 빈으로 등록되어야 하는 클래스들(RateDiscountPolicy, MemberServiceImpl, MemoryMemberRepository, OrderServiceImpl)에게 @Component 를 붙여준다.
  *
  * 참고로, @Configuration 이 스캔의 대상이 되는 이유는 @Configuration 의 소스코드 안에 @Component 가 정의되어 있기 때문이다.
  *
  * 이전에 AppConfig 에서는 @Bean 애너테이션을 이용해 직접 설정 정보를 작성했고, 의존관계 역시 직접적으로 명시했다. 그러나 이제는 이러한 설정 정보 자체가 없기 때문에
  * 의존관계 주입 역시 @Component 가 지정된 클래스 안에서 직접 해결해야 한다. 이를 대비해 @Autowired 를 사용할 수 있다.
  *
  *
  *
  *
  *
  *
  * */
}
