package com.example.springbasic;

import com.example.springbasic.discount.DiscountPolicy;
import com.example.springbasic.discount.RateDiscountPolicy;
import com.example.springbasic.member.MemberRepository;
import com.example.springbasic.member.MemberService;
import com.example.springbasic.member.MemberServiceImpl;
import com.example.springbasic.member.MemoryMemberRepository;
import com.example.springbasic.order.OrderService;
import com.example.springbasic.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public MemberService memberService() {
    return new MemberServiceImpl(getMemberRepository());
  }

  @Bean
  public OrderService orderService() {
    return new OrderServiceImpl(getMemberRepository(), getDiscountPolicy());
  }

  @Bean
  public MemberRepository getMemberRepository() {
    return new MemoryMemberRepository();
  }

  @Bean
  public DiscountPolicy getDiscountPolicy() {
//    return new FixDiscountPolicy();
    return new RateDiscountPolicy();
  }

}
