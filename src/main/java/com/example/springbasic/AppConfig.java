package com.example.springbasic;

import com.example.springbasic.discount.DiscountPolicy;
import com.example.springbasic.discount.RateDiscountPolicy;
import com.example.springbasic.member.MemberService;
import com.example.springbasic.member.MemberServiceImpl;
import com.example.springbasic.member.MemoryMemberRepository;
import com.example.springbasic.order.OrderService;
import com.example.springbasic.order.OrderServiceImpl;

public class AppConfig {

  public MemberService memberService() {
    return new MemberServiceImpl(getMemberRepository());
  }

  public OrderService orderService() {
    return new OrderServiceImpl(getMemberRepository(), getDiscountPolicy());
  }

  private MemoryMemberRepository getMemberRepository() {
    return new MemoryMemberRepository();
  }

  private DiscountPolicy getDiscountPolicy() {
//    return new FixDiscountPolicy();
    return new RateDiscountPolicy();
  }

}
