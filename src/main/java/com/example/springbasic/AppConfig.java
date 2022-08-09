package com.example.springbasic;

import com.example.springbasic.discount.DiscountPolicy;
import com.example.springbasic.discount.RateDiscountPolicy;
import com.example.springbasic.member.MemberRepository;
import com.example.springbasic.member.MemberService;
import com.example.springbasic.member.MemberServiceImpl;
import com.example.springbasic.member.MemoryMemberRepository;
import com.example.springbasic.order.OrderService;
import com.example.springbasic.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  /*
   * 객체 지향의 핵심은 다형성이다.
   * 그러나 다형성만으로는 객체 지향 설계 원칙을 모두 지키며 개발할 수 없다.
   * 구현 객체를 변경하는 경우 클라이언트 코드도 같이 변경해야 하기 때문이다. OCP, DIP 가 지켜지지 않는 것이다.
   * '무엇'인가가 더 필요하다.
   *
   * 이 문제를 해결하기 위해서는 결국 누군가가 클라이언트인 OrderServiceImpl 에 DiscountPolicy 의 구현 객체를 생성하고 주입해야 한다.
   *
   * ◆ 관심사의 분리
   * 애플리케이션을 하나의 공연이라고 생각하자. 각각의 인터페이스는 배역(역할)이 된다. 그런데 이 때 실제 배역에 맞는 배우(구현)는 누가 선택하는가?
   * 로미오와 줄리엣 공연에서 로미오 역할을 누가 맡을지 줄리엣 역할을 누가 맡을지는 배우(구현)들이 직접 정하는 것이 아니다.
   * 기존의 코드는 마치 로미오 역할을 맡고 있는 배우가 줄리엣 역할을 맡을 배우를 직접 선택하고 있다.
   * 즉, 자신의 역할도 수행함과 동시에 상대 배우도 선택하는 다양한 책임을 지고 있는 것이다.
   *
   * - 배우는 자신의 역할 수행에만 집중해야 한다.
   * - 배우는 자신의 상대 배역으로 어떤 배우가 오더라도 똑같이 공연할 수 있어야 한다.
   * - 공연을 구성하고, 담당 배우를 섭외하고, 역할에 맞는 배우는 지정하는 책임은 배우가 아닌 제3자가 맡아야 한다.
   *   즉, 별도의 공연 기획자가 필요한 것이다.
   * - 공연 기획자를 만들고 배우와 공연 기획자의 책임을 분리한다.
   *
   * ◆ AppConfig 의 등장
   * 애플리케이션의 전체 동작 방식을 구성(config) 하기 위해 구현 객체를 생성하고, 연결하는 책임을 가지는 별도의 설정 클래스를 만든다.
   * AppConfig 는 애플리케이션의 실제 동작에 필요한 구현 객체들을 생성한다.
   * - MemberServiceImpl, MemoryMemberRepository, OrderServiceImpl, FixDiscountPolicy
   * AppConfig 는 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해서 주입(연결)한다.
   *
   * 클라이언트인 memberServiceImpl 입장에서 보면 의존관계를 마치 외부에서 주입해주는 것과 같다고 해서
   * DI(Dependency Injection), 즉 의존관계 주입 또는 의존성 주입이라고 한다.
   *
   *
   * */

  @Bean
  public MemberService memberService() {
    return new MemberServiceImpl(MemberRepository());
  }

  @Bean
  public OrderService orderService() {
    return new OrderServiceImpl(MemberRepository(), DiscountPolicy());
  }

  @Bean
  public MemberRepository MemberRepository() {
    return new MemoryMemberRepository();
  }

  @Bean
  public DiscountPolicy DiscountPolicy() {
//    return new FixDiscountPolicy();
    return new RateDiscountPolicy();
  }

}
