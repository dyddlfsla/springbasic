package com.example.springbasic.autowired;

import com.example.springbasic.member.Member;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

public class AutowiredTest {

  @Test
  void AutowiredOption() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
        TestBean.class);
  }

  static class TestBean{

    @Autowired(required = false)
    public void setNoBean(Member member) {
      System.out.println("member = " + member);
    }

    @Autowired
    public void setNoBean2(@Nullable Member member) {
      System.out.println("member = " + member);
    }

    @Autowired
    public void setNoBean3(Optional<Member> memberOptional) {
      System.out.println("memberOptional = " + memberOptional);
    }
  }

}
