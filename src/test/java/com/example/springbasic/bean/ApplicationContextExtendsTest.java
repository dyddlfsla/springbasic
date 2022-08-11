package com.example.springbasic.bean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.springbasic.discount.DiscountPolicy;
import com.example.springbasic.discount.FixDiscountPolicy;
import com.example.springbasic.discount.RateDiscountPolicy;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ApplicationContextExtendsTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

  @Test
  void findBeanByParentTypeDuplicate() {
    assertThrows(NoUniqueBeanDefinitionException.class,
        () -> ac.getBean(DiscountPolicy.class));
  }

  @Test
  void findBeanByParentTypeBeanName() {
    DiscountPolicy foundBean = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
    assertThat(foundBean).isInstanceOf(RateDiscountPolicy.class);
  }

  @Test
  void findBeanBySubType() {
    DiscountPolicy foundBean = ac.getBean(RateDiscountPolicy.class);
    assertThat(foundBean).isInstanceOf(RateDiscountPolicy.class);
  }

  @Test
  void findAllBeanByParentType() {
    Map<String, DiscountPolicy> foundBeans = ac.getBeansOfType(DiscountPolicy.class);
    assertThat(foundBeans.size()).isEqualTo(2);
    for (String key : foundBeans.keySet()) {
      System.out.println("key = " + key + ", value = " + foundBeans.get(key));
    }
  }

  @Test
  void findAllBeanByObjectType() {
    Map<String, Object> foundBeans = ac.getBeansOfType(Object.class);
    for (String key : foundBeans.keySet()) {
      System.out.println("key = " + key + ", value = " + foundBeans.get(key));
    }
  }

  @Configuration
  static class TestConfig {

    @Bean
    public DiscountPolicy rateDiscountPolicy() {
      return new RateDiscountPolicy();
    }

    @Bean
    public DiscountPolicy FixDiscountPolicy() {
      return new FixDiscountPolicy();
    }

  }

  /*
  * ◆ 스프링 Bean 조회 - 상속 관계인 경우
  *
  * 부모 타입으로 조회하면, 자식 타입도 모두 함께 조회된다.
  * 그렇기 때문에 만약 Object 타입으로 Bean 을 조회하면 존재하는 모든 스프링 Bean 을 찾게 된다.
  *
  *
  * */

}
