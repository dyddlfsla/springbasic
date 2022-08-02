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
  * */
}
