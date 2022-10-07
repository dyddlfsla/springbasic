package com.example.springbasic;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class),
    basePackages = "com.example.springbasic")
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
  * 1. 탐색위치와 기본 스캔 대상
  * @ComponentScan 이 @Component 가 붙은 클래스를 찾아 스프링 빈을 등록하게 되는데, 이 때 모든 자바 클래스를 스캔하면서 @Component 가 붙어 있는지 아닌지 확인하는 것은 시간이 오래 걸리고 비효율적인 작업이 된다.
  * 그래서 추가적인 코드를 통해 스캔을 시작할 위치를 지정할 수 있다.
  *
  * ① basePackages = {"com.example.springbasic.member"} 와 같이 탐색할 패키지의 위치를 지정한다. 그러면 스프링은 이 패키지를 포함해서 하위 패키지를 모두 탐색한다.
  * ② basePackageClasses: 지정한 클래스가 존재하는 패키지부터 탐색하게 된다.
  *
  * 만약 패키지를 지정하지 않으면 기본값은, @ComponentScan 이 붙은 클래스를 가진 패키지가 탐색 시작 위치가 된다.
  *
  * ③ 권장하는 방식: 패키지 탐색 위치를 별도로 지정하지 말고, 설정 정보 클래스(@ComponentScan 이 붙은 클래스)를 프로젝트의 최상단으로 두고 자동으로 모든 하위 패키지가 탐색되도록 하는 것이 좋다.
  *                현재 스프링부트 역시 이 방식을 제공하고 있다.(@SpringBootApplication 이 붙은 메인 클래스를 기본적으로 생성한다.)
  *
  *
  * 2. @ComponentScan 의 탐색 대상
  * 위에서는 빈 등록 대상이 @Component 가 붙은 클래스라고 했지만, 사실 @Component 가 붙은 클래스 뿐만 아니라 다음 애너테이션이 붙은 클래스도 탐색 대상이 된다.
  *
  * ① @Controller: 스프링 MVC 에서 사용된다.
  * ② @Service: 스프링 비지니스 로직에서 사용된다.
  * ③ @Repository: 스프링 데이터 접근 계층에서 사용된다.
  * ④ @Configuration: 스프링 설정 정보에서 사용한다.
  *
  * ※ 사실 자바 애너테이션은 문법적으로 상속의 개념을 가지고 있지 않다.
  *   그래서 어떤 애너테이션이 또 다른 애너테이션 기능을 가지고 있는 것은 자바 언어에서 지원하는 것이 아니라 스프링이 지원하는 것이다.
  *
  * 3. 필터 기능
  * @ComponentScan 에서 추가적으로 필터 기능을 사용할 수 있다.
  *
  *  ① includeFilters: 컴포넌트 스캔 대상을 추가로 지정한다.
  *  ② excludeFilters: 컴포넌트 스캔 대상에서 제외할 대상을 지정한다.
  *
  *
  * 4. @ComponentScan 에서 중복 등록과 충돌
  *
  * 컴포넌트 스캔을 적용할 때, 같은 빈 이름을 등록하면 어떻게 될까?
  * 일반적으로 다음의 두 가지 상황이 있을 수 있다.
  *
  *  ① 자동으로 등록된 빈 vs 자동으로 등록된 빈이 충돌하는 경우
  *  컴포넌트 스캔에 의해 자동으로 등록된 두 스프링 빈이 만약 이름이 같다면 스프링은 다음과 같은 예외를 발생시킨다.
  *  → ConflictingBeanDefinitionException 발생.
  *
  *  ② 수동으로 등록된 빈 vs 자동으로 등록된 빈이 충돌하는 경우
  *  예를 들어, @Bean 애너테이션을 사용하여 자동으로 등록된 스프링 빈과 이름이 같은 스프링 빈을 추가하면 어떻게 될까?
  *  이런 경우 수동으로 등록된 스프링 빈이 우선권을 가지게 된다. 즉, 수동으로 등록된 빈이 자동으로 등록된 빈을 오버라이딩 해버리는 것이다.
  *
  *  그러나 이러한 방식이 오히려 오류를 확인하기 어렵게 만들고 문제를 복잡하게 만든다는 의견이 제기되었고
  *  이에 따라, 최근 스프링부트에서는 수동으로 등록된 빈에게 우선권을 주는게 아니라 그냥 예외를 발생시키고 개발자가 직접 조정하도록 변경하였다.
  *
  *
  *
  * */
}
