package com.example.springbasic.singleton;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

  @Test
  void statefulServiceSingleton() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
    StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
    StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

    //ThreadA: 사용자(A) 1000원 주문
    statefulService1.order("userA", 10000);
    //ThreadB: 사용자(B) 2000원 주문
    statefulService2.order("userB", 20000);

    //사용자(A) 의 주문 금액을 조회한다.
    int price = statefulService1.getPrice();
    System.out.println("price = " + price);

    assertThat(statefulService1.getPrice()).isEqualTo(20000);
  }

  static class TestConfig {

    @Bean
    public StatefulService statefulService() {
      return new StatefulService();
    }

  }

}

/*
* ThreadA 가 사용자(A) 코드를 호출하고 ThreadB 가 사용자(B) 코드를 호출한다고 하자.
* 사용자(A)의 주문 금액은 10000원이 되어야 하지만, 실제로는 20000원이라는 결과가 나왔다.
* 현재 price 라는 필드가 공유되고 있기 때문에 각 쓰레드가 수행될때마다 필드의 값이 계속 변하고 있는 것이다.
* 실무에서 종종 이런 경우가 발생하는데, 이로인해 정말 큰 장애가 발생하는 경우가 많다.
* 스프링 bean 설계 시, 공유 필드에 항상 주의해야 한다.
* 항상 무상태(stateless)하게 만들자.
*
*
*
*
* */