package com.example.springbasic.discount;

import static org.assertj.core.api.Assertions.*;

import com.example.springbasic.member.Grade;
import com.example.springbasic.member.Member;
import org.junit.jupiter.api.Test;

class RateDiscountPolicyTest {

  DiscountPolicy discountPolicy = new RateDiscountPolicy();

  @Test
  void vip_discount() {
    Member member = new Member(1L, "memberVIP", Grade.VIP);
    int discount = discountPolicy.discount(member, 10000);

    assertThat(discount).isEqualTo(1000);
  }

  @Test
  void basic_discount() {
    Member member = new Member(2L, "memberBASIC", Grade.BASIC);
    int discount = discountPolicy.discount(member, 10000);

    assertThat(discount).isEqualTo(0);
  }
}