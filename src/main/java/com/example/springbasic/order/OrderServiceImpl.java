package com.example.springbasic.order;

import com.example.springbasic.discount.DiscountPolicy;
import com.example.springbasic.member.Member;
import com.example.springbasic.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

//  @Autowired private MemberRepository memberRepository;
//  @Autowired private DiscountPolicy discountPolicy;

  private final MemberRepository memberRepository;
  private final DiscountPolicy discountPolicy;

//  @Autowired
//  public void setMemberRepository(MemberRepository memberRepository) {
//    this.memberRepository = memberRepository;
//  }
//
//  @Autowired
//  public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//    this.discountPolicy = discountPolicy;
//  }

  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {
    Member member = memberRepository.findById(memberId);
    int discountPrice = discountPolicy.discount(member, itemPrice);
    return new Order(memberId, itemName, itemPrice, discountPrice);
  }

  public MemberRepository getMemberRepository() {
    return memberRepository;
  }
}

/*
 * private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
 * 설계 변경으로 인해 OrderServiceImpl 은 FixDiscountPolicy 에 의존하지 않게 되었다.
 * 단지 DiscountPolicy 인터페이스에만 의존할 뿐이다.
 * OrderServiceImpl 입장에서는 어떤 DiscountPolicy 의 구현 객체가 올지는 모른다.
 * DiscountPolicy 이제 생성자를 통해서 들어오게 될텐데 어떤 DiscountPolicy 가 들어오는지는 OrderServiceImpl 이 정하지 않는다.
 * 오직 외부(AppConfig) 에서 일방적으로 결정한다.
 *
 * 이제 OrderServiceImpl 은 자신의 역할 수행에만 집중하면 된다.
 * OrderServiceImpl 에는 MemoryMemberRepository, FixDiscountPolicy 객체의 의존관계가 주입된다.
 *
 * */
