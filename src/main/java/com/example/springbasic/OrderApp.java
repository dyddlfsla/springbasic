package com.example.springbasic;

import com.example.springbasic.member.Grade;
import com.example.springbasic.member.Member;
import com.example.springbasic.member.MemberService;
import com.example.springbasic.order.Order;
import com.example.springbasic.order.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {

  public static void main(String[] args) {
//    AppConfig appConfig = new AppConfig();
//    MemberService memberService = appConfig.memberService();
//    OrderService orderService = appConfig.orderService();

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

    MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
    OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

    Long memberId = 1L;
    Member member = new Member(memberId, "memberA", Grade.VIP);
    memberService.join(member);

    Order order = orderService.createOrder(memberId, "itemA", 10000);
    System.out.println("order = " + order);
    System.out.println("order.calculatePrice() = " + order.calculatePrice());
  }

}

/*
 * 'ApplicationContext' 를 스프링 컨테이너라 한다.
 * 기존에는 개발자가 'AppConfig' 를 사용해서 직접 객체를 생성하고 DI 를 했지만, 이제부터는 스프링 컨테이너를 통해서 사용한다.
 * 스프링 컨테이너는 '@Configuration' 이 붙은 'AppConfig' 를 설정 정보로 사용한다.
 * 여기서 @Bean 이라 적힌 메서드를 모두 호출한 뒤 반환된 객체를 스프링 컨테이너에 등록한다. 이렇게 스프링 컨테이너에 등록된 객체를 스프링 빈이라 한다.
 * 스프링 빈은 '@Bean' 이 붙은 메서드의 이름을 스프링 빈의 이름으로 사용한다.
 * 이전에는 개발자가 필요한 객체를 'AppConfig' 안에서 직접 조회해서 사용했지만, 이제부터는 스프링 컨테이너를 통해서 필요한 스프링 빈(객체)를 찾아야 한다.
 * 스프링 빈은 applicationContext 의 getBean() 메서드를 통해 찾을 수 있다.
 * 개발자가 직접 자바코드로 직접 객체를 관리하고 조회했다면 이제는 객체를 스프링 컨테이너에 빈으로 등록한 뒤 필요한 객체를 스프링 컨테이너에서 찾아서 사용하도록 변경되었다.
 *
 * ◆ 스프링 컨테이너 생성
 * ApplicationContext 는 인터페이스이다.
 * 스프링 컨테이너는 XML 을 기반으로 만들 수 있고, Annotation 기반의 자바 클래스를 이용해서 만들수도 있다.
 * new AnnotationConfigApplicationContext(AppConfig.class); → Annotation 기반의 자바 클래스를 이용하여 스프링 컨테이너를 만들겠다는 뜻이다.
 * 해당 클래스는 ApplicationContext 인터페이스의 구현체이다.
 *
 * 참고: 스프링 컨테이너를 부를 때 BeanFactory, ApplicationContext 로 구분해서 이야기한다. 그러나 BeanFactory 를 직접 조작하는 경우는 드물고
 * 일반적으로 ApplicationContext 를 스프링 컨테이너라고 한다.
 *
 * 스프링 컨테이너를 생성할 때는 구성 정보를 지정해주어야 한다.
 * AppConfig.class 를 구성 정보로 지정했다.
 * 스프링 컨테이너는 매개변수로 넘어온 구성 정보를 이용하여 스프링 Bean 을 등록한다.
 *
 * Bean 의 이름은 관례적으로 메서드의 이름을 사용한다.
 * 아니면 Bean 이름을 직접 부여할 수도 있다. 예) @Bean(name = "memberService2")
 * 주의: 스프링 Bean 의 이름은 항상 다른 이름을 부여해야 한다. 만약 같은 이름의 스프링 Bean 이 두 개 이상 존재한다면, 다른 Bean 이 무시되거나 기존 Bean 을 덮어버릴수 있고
 * 예기치 않은 오류를 발생시킨다.
 *
 * 스프링 컨테이너는 설정 정보를 참고해서 의존관계를 주입(DI) 한다.
 * 일반적으로 스프링은 Bean 을 생성하고, 의존관계를 주입하는 단계가 나누어져 있다. 그러나 이렇게 Annotation 기반의 자바 클래스를 이용하여 스프링 컨테이너를 만들면
 * 생성자를 호출하면서 의존관계 주입도 한번에 처리하게 된다.
 *
 *
 *
 * */
