package com.example.springbasic.xml;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springbasic.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

public class XmlAppContextTest {

  @Test
  void xmlAppContext() {
    GenericXmlApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
    MemberService memberService = ac.getBean("memberService", MemberService.class);
    assertThat(memberService).isInstanceOf(MemberService.class);
  }

}
