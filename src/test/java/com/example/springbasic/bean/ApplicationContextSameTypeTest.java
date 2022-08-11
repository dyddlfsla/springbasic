package com.example.springbasic.bean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.springbasic.member.MemberRepository;
import com.example.springbasic.member.MemoryMemberRepository;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ApplicationContextSameTypeTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TemporaryConfig.class);

  @Test
  void findBeansInDuplicateType() {
    assertThrows(NoUniqueBeanDefinitionException.class,
        () -> ac.getBean(MemberRepository.class));
  }

  @Test
  void findBeanByNameInDuplicateType() {
    MemberRepository memberRepository = ac.getBean("memberRepository1", MemberRepository.class);
    assertThat(memberRepository).isInstanceOf(MemberRepository.class);
  }

  @Test
  void findAllBeanInType() {
    Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
    for (String key : beansOfType.keySet()) {
      System.out.println("key = " + key + ", value = " + beansOfType.get(key));
    }
    System.out.println("beansOfType = " + beansOfType);
    assertThat(beansOfType.size()).isEqualTo(2);
  }

  @Configuration
  static class TemporaryConfig {

    @Bean
    public MemberRepository memberRepository1() {
      return new MemoryMemberRepository();
    }

    @Bean
    public MemberRepository memberRepository2() {
      return new MemoryMemberRepository();
    }

  }

  /*
  * ◆ 스프링 Bean 조회 - 동일한 타입이 두 개 이상인 경우
  * 타입으로 Bean 을 조회 시 같은 타입의 Bean 이 두 개 이상이면 오류가 발생한다.
  * ac.getBeansOfType(): 해당 타입과 일치하는 모든 Bean 을 조회한다.
  *
  * */
}
