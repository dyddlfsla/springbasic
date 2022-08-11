package com.example.springbasic.bean;

import com.example.springbasic.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

  @Test
  void findAllBean() {
    String[] beanDefinitionNames = ac.getBeanDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames) {
      Object bean = ac.getBean(beanDefinitionName);
      System.out.println("name = " + beanDefinitionName + ", Object = " + bean);
    }
  }

  @Test
  void findApplicationBean() {
    String[] beanDefinitionNames = ac.getBeanDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames) {
      BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
      /*
      * ROLE_APPLICATION: 직접 등록한 빈
      * ROLE_INFRASTRUCTURE: 스프링이 내부적으로 사용하는 빈
      *
      * */
      if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
        Object bean = ac.getBean(beanDefinitionName);
        System.out.println("name = " + beanDefinitionName + ", Object = " + bean);
      }
    }
  }

  /*
  * ◆ 스프링 컨테이너에 등록된 모든 Bean 조회
  *
  * ac.getBeanDefinitionNames(): 스프링에 등록된 모든 Bean 의 이름을 조회한다.
  * ac.getBean(): Bean 의 이름으로 Bean 객체(인스턴스)를 조회한다.
  * 스프링 내부에서 사용하는 Bean 은 getRole() 메서드를 통해 그 역할로 구분할 수 있다.
  * 1) getRole() == BeanDefinition.ROLE_APPLICATION : 일반적으로 사용자가 직접 정의한 Bean
  * 2) getRole() == BeanDefinition.INFRASTRUCTURE: 스프링 프레임워크에서 자체적으로 생성하고 만든 Bean
  *
  *
  * 1. 스프링 Bean 을 찾는 기본적인 방법
  * getBean(Bean 이름, 타입): 이름과 일치하는 Bean 조회
  * getBean(타입): 타입과 일치하는 Bean 조회
  * 만약 결과로 스프링 Bean 이 없다면 예외가 발생한다. → NoSuchBeanDefinitionException: No bean named 'xxxx' available
  *
  * */

}
