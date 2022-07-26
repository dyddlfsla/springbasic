package com.example.springbasic.discount;

import com.example.springbasic.member.Member;

public interface DiscountPolicy {

  int discount(Member member, int price);

}
