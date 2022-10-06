package com.example.springbasic.scan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.context.annotation.ComponentScan.Filter;

import com.example.springbasic.scan.filter.BeanA;
import com.example.springbasic.scan.filter.BeanB;
import com.example.springbasic.scan.filter.MyExcludeComponent;
import com.example.springbasic.scan.filter.MyIncludeComponent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

public class ComponentFilterAppConfigTest {

  @Test
  void filterScan() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);

    BeanA beanA = ac.getBean("beanA", BeanA.class);
    assertThat(beanA).isNotNull();

    assertThrows(NoSuchBeanDefinitionException.class,
        () -> ac.getBean("beanB", BeanB.class));
  }

  @Configuration
  @ComponentScan(
      includeFilters = @Filter(classes = MyIncludeComponent.class),
      excludeFilters = @Filter(classes = MyExcludeComponent.class)
  )
  static class ComponentFilterAppConfig {}
}
