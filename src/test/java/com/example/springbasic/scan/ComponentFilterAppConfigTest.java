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

/*
* includeFilters 에 MyIncludeComponent 를 지정함으로써 @MyIncludeComponent 가 지정된 클래스를 컴포넌트 스캔 대상에 추가한다.
* excludeFilters 에 MyExcludeComponent 를 지정함으로써 @MyExcludeComponent 가 지정된 클래스를 컴포넌트 스캔 대상에서 제외시켰다.
*
* ※ 일반적으로 @Component 애너테이션만으로도 충분하기 때문에 includeFilters 를 사용할 일은 거의 없다.
*    excludeFilters 역시 간혹 사용하기는 하지만 그런 경우가 많지 않다.
*
*
* */
