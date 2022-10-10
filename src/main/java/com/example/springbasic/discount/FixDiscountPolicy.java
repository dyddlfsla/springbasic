package com.example.springbasic.discount;

import com.example.springbasic.member.Grade;
import com.example.springbasic.member.Member;
import org.springframework.stereotype.Component;

@Component
//@Qualifier("fixDiscountPolicy")
// ↑ @Qualifier 는 추가 구분자를 붙여주는 방식이다. 의존주입 시 추가적인 방법을 제공하는 것이지, 스프링 빈의 이름을 변경하는 것은 아니다.
// 스프링 빈을 등록할 때 @Qualifier("이름") 을 붙여주고.
// 의존 주입 받는 클래스로 가서 해당 매개변수 앞에도 같은 @Qualifier("이름") 을 붙여준다.
// 이 애너테이션도 "이름" 에 해당하는 대상을 찾지 못하면 스프링 빈의 이름으로 다시 매칭하게 되는데, 이런 방식은 지양하는 것이 좋다.
// @Qualifier 을 사용할 때에는 꼭 맞는 @Qualifier 를 짝지어서 사용하도록 하자.

//@Primary
// ↑ @Primary 는 우선 순위를 정하는 방법이다. @Autowired 시 여러 빈이 매칭되면 @Primary 가 붙은 스프링 빈이 우선권을 가지게 되는 것이다.

public class FixDiscountPolicy implements DiscountPolicy {

  private int discountFixAmount = 1000;

  @Override
  public int discount(Member member, int price) {
    if (member.getGrade() == Grade.VIP) {
      return discountFixAmount;
    }
    return 0;
  }
}

/*
* ◆ 조회되는 Bean 이 2개인 경우 문제가 발생한다.
*
* 지금까지 사용했던 @Autowired 은 스프링 빈을 타입으로 조회한다.
* 즉, getBean(DiscountPolicy.class) 처럼 동작한다는 것이다.
* 앞서 배운 것처럼 스프링 빈을 타입(Type)으로 조회할 때 조회된 빈이 2개라면 문제가 발생한다.
* DiscountPolicy 타입에 해당하는 빈은 fixDisCountPolicy 과 rateDiscountPolicy 이렇게 2개가 존재하므로 NoUniqueBeanDefinitionException 에러가 발생한다.
* 이런 문제는 어떻게 해결해야 할까?
* OrderServiceImpl 클래스의 자동 주입 코드를 다음과 같이 바꾸는 것은 어떨까?
*
*  private final MemberRepository memberRepository;
*  private final RateDiscountPolicy discountPolicy;
*
*
*  @Autowired
*  public OrderServiceImpl(MemberRepository memberRepository, RateDiscountPolicy rateDiscountPolicy) {
*    this.memberRepository = memberRepository;
*    this.discountPolicy = discountPolicy;
*  }
*
* 이렇게 바꾼다면 DiscountPolicy.class 타입으로 조회하는 것이 아니라 하위타입인 RateDiscountPolicy 타입으로 조회하게 되므로 문제는 해결될 것이다.
* 그러나 이런 방식은 객체 지향 설계 원칙 중 DIP 를 위반하게 되는 것이고 유연성이 떨어진다. 또한 추후 이 하위타입의 클래스가 2개가 되면 같은 문제가 반복될 것이다.
*
* 올바른 해결 방식은 다음과 같다.
*
* 1. @Autowired 이 가진 필드명 매칭 기능 이용하기
* 2. @Qualifier 사용하기
* 3. @Primary 사용하기
*
* @Autowired 는 1차적으로 타입 매칭을 시도하고, 이때 빈이 2개 이상 존재하면 추가적으로 필드 이름과 파라미터 이름을 사용해 매칭을 시도한다.
*
* private final DiscountPolicy rateDiscountPolicy;
*
* 위와 같이 코드를 수정한다면, 필드명은 rateDiscountPolicy 가 되고 컨테이너는 등록된 스프링 빈 중 빈의 이름과 필드명이 일치하는 것을 찾아 매칭한다.
* 즉 RateDiscountPolicy 의 구현체가 선택되어 주입될 것이다.
* 이러한 필드명 매칭은 먼저 타입 매칭을 시도한 뒤 추가적으로 이루어지는 동작이다.
*
* Question. @Primary 와 @Qualifier 둘 중 어느 것을 사용해야 할까?
* 애플리케이션 구조에서 자주 사용하는 메인 DB 에 접근하는 스프링 빈이 있고, 자주 사용되지는 않고 가끔 사용되는 서브 DB 에 접근하는 스프링 빈이 있다고 하자.
* 이런 경우, 메인 DB 에 접근하는 빈에는 @Primary 를 사용하여 간편하게 사용하고, 서브 DB 에 접근하는 빈에는 @Qualifier 를 사용해 명시적으로 표현하면
* 코드를 깔끔하게 유지할 수 있다.
*
* Question. @Primary 와 @Qualifier 이 동시에 설정되어 있다면 어느 것이 우선권을 가질까?
* @Primary 는 기본값처럼 동작하는 것이고 @Qualifier 는 매우 상세적으로 동작한다. 스프링은 자동보다는 수동이, 넓은 범위의 선택보다는 좁은 범위의 선택이 우선권을 가지게 된다.
* 따라서 여기서는 좁고 명시적인 의미를 가진 @Qualifier 가 우선권을 가지게 된다.
*
* ※ @Qualifier 을 내부적으로 포함하는 애너테이션 만들기.
* @Qualifier 를 그냥 사용하는 경우, 오타가 발생해도 컴파일이 감지하지 못하는데, 이때 별도의 애너테이션을 만들어 해결할 수도 있다.
*
* @Qualifier("...")
* ...
* @interface MainDiscountPolicy {}
*
* 위의 코드와 같이 내부적으로 @Qualifier 를 가진 사용자 애너테이션을 만들어서 사용하면 오타가 발생했을 때 컴파일 에러가 발생한다.
* 단, 애너테이션이 다른 애너테이션을 내부적으로 상속받는 것은, 자바의 순수 기능이 아니라 스프링 프레임워크가 지원하는 기능이다.
*
* Question. 수동 빈 등록 vs 자동 빈 등록 어떻게 구분해서 사용해야 할까?
* 애플리케이션이 가지고 있는 로직은 크게 업무 로직과 기술 지원 로직으로 나눌 수 있다.
* 업무 로직은 숫자도 매우 많고, 한번 개발해야 하면 컨트롤러, 서비스, 레퍼지토리 등 유사한 패턴이 있다. 이럴 때 자동 빈 등록 기능을 이용하는 것이 좋다.
* 보통 문제가 발생해도 어떤 곳에서 발생하는지 명확하게 파악하기 쉽다.
*
* 기술 지원 로직은 업무 로직에 비교해서 그 수가 매우 적고, 보통 애플리케이션 전반에 걸쳐서 광범위하게 영향을 미친다. 또한 기술 지원 로직은
* 문제가 발생해도 잘 드러나지 않고 파악하기도 어려운 경우가 많다. 그래서 이런 기술지원 로직들은 가급적이면 수동 빈 등록을 사용해서 명확하게 겉으로 드러내는 것이 좋다.
*
* => 자동 빈 등록을 기본으로 사용하되,
*    애플리케이션에 전범위적으로 영향을 주는 기술지원 객체는 수동으로 등록하여 설정 정보에 명시적으로 드러나게 하는 것이 유지보수에 좋다.
*
*
*
*
*
*
*
* */
