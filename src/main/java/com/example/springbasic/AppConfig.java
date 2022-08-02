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
    return new MemberServiceImpl(MemberRepository());
  }

  @Bean
  public OrderService orderService() {
    return new OrderServiceImpl(MemberRepository(), DiscountPolicy());
  }

  @Bean
  public MemberRepository MemberRepository() {
    return new MemoryMemberRepository();
  }

  @Bean
  public DiscountPolicy DiscountPolicy() {
//    return new FixDiscountPolicy();
    return new RateDiscountPolicy();
  }

}
