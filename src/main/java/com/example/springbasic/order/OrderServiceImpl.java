package com.example.springbasic.order;

import com.example.springbasic.annotation.MainDiscountPolicy;
import com.example.springbasic.discount.DiscountPolicy;
import com.example.springbasic.member.Member;
import com.example.springbasic.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor ← 이 애너테이션은 클래스가 가진 필드 중 final 이 붙은 필드들을 모아서 생성자를 자동으로 만들어준다..!
// ◆ lombok 과 최신 트렌드
// 실무에서 개발을 진행하다 보면, 대부분의 의존관계가 다 불변이고 그에 따라 final 키워드를 사용하게 된다.
// 그런데 매번 생성자로 만들어야 하고, 주입 받은 값을 대입하는 코드도 만들어야 하고..
// 지루하고 반복적인 작업은 개발자를 힘들게 한다..
// 그러나 이런 상황에서 반복적인 코드를 대신 만들어주는 라이브러리가 있다면 어떨까?
// lombok 을 적용해보자. 코드가 정말 간결해진다.
// lombok 은 자바의 애너테이션 프로세서라는 기능을 이용해서 컴파일 시점에 자바 코드를 대신 생성해준다.
// 실제로 컴파일된 파일을 살펴보면 코드가 생성되어 있는 것을 확인할 수 있다.
// 최근 애플리케이션 개발에서는 클래스에 생성자를 하나만 만들고 이에 따라 @Autowired 를 생략하는 방법을 주로 사용한다.
public class OrderServiceImpl implements OrderService{

//  @Autowired private MemberRepository memberRepository;
//  @Autowired private DiscountPolicy discountPolicy;ㅁ
//  ↑ 필드 주입 방식

  private final MemberRepository memberRepository;
  private final DiscountPolicy discountPolicy;


  @Autowired // ← @Autowired 은 기본적으로 주입할 대상이 없으면 오류를 발생시킨다. 주입할 대상이 없어도 동작하게 하려면 @Autowired(required = false) 를 지정한다.
  public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
  }
  // ↑ 생성자 주입 방식. 말그대로 생성자를 통해 의존관계를 주입하고 있다.
  // 생성자가 하나만 존재하는 상황이라면 @Autowired 애너테이션을 생략할 수도 있다.

//  @Autowired
//  public void setMemberRepository(MemberRepository memberRepository) {
//    this.memberRepository = memberRepository;
//  }
//
//  @Autowired
//  public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//    this.discountPolicy = discountPolicy;
//  }
//  ↑ 수정자(setter) 주입. setter 메서드라 불리는 메서드를 이용해 의존관계를 주입하는 방식이다.


//  public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//    this.memberRepository = memberRepository;
//    this.discountPolicy = discountPolicy;
//  }
//  ↑ 일반 메서드를 이용해 주입하는 방식. 4가지 방식 중 가장 사용되지 않는다.

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


/*
* ◆ 의존관계 자동 주입
*
* 다양한 의존관계 주입 방법
* 의존관계 주입에는 크게 4가지 방법이 존재한다.
*
* 1. 생성자 주입
* 생성자 주입은 말 그대로 생성자를 통해서 의존관계를 주입하는 방법이다.
* 지금까지 진행해왔던 방식이 바로 이 방식인 것이다.
* 생성자 주입은 다음과 같은 특징을 가지고 있다.
* => 생성자 호출 시점에 딱 한번만 의존관계가 주입된다.
* => 불변, 필수와 같은 의존관계에 사용한다
*
* 2. 수정자 주입
* setter 메서드를 이용해 의존관계를 주입하는 방식이다.
* => 선택, 변경 가능성이 있는 의존관계에서 사용한다.
* => 자바빈 프로퍼티 규약의 수정자 메서드 방식을 이용하는 방법이다.
*
* 3. 필드 주입
* 별도의 메서드를 사용하지 않고 필드 그 자체에 바로 주입하는 방식이다.
* => 다른 주입에 비해 코드가 아주 간결하므로 좋은 방식처럼 보이지만, 실제로는 외부에서의 변경 가능성을 아예 차단하므로 단점이 발생한다.
* => 의존 주입을 오로지 스프링 프레임워크에 맡기는 방식이므로 개발자가 주입 과정에 전혀 개입할 수 없다.
* => 예를 들어, 테스트 환경에서 임시로 주입 객체를 바꾸고 싶어도 바꿀 수가 없다.
* => 사용하지 않는 것이 권장된다.
*
* 4. 일반 메서드 주입
* 클래스가 가진 일반 메서드를 이용해 의존 주입하는 방식이다.
* => 한번에 여러 필드를 주입 받을 수 있다.
* => 대체적으로 잘 사용되지 않는다.
*
*
* ◆ 옵션 처리
*
* 프로그램을 개발하다 보면 스프링 빈이 주입되지 않아도 동작해야 하는 경우가 있다.
* 그런데 @Autowired 을 이용해서 의존 주입을 하게되면 자동 주입 대상이 없을 때 오류가 발생하도록 되어 있다.
* 이런 경우, 주입 되는 대상이 존재하는지에 따라 별도의 처리를 추가할 수 있다.
*
* 1) @Autowired(require = false): 자동 주입할 대상이 존재하지 않으면 수정자 메서드 자체가 호출되지 않고 에러가 발생하지 않는다.
* 2) @Nullable: 주입 받는 참조변수 타입에 붙여주면 자동 주입할 대상이 없을 시 null 이 입력된다.
* 3) Optional<T>: Optional 래퍼 클래스를 사용하면 자동 주입할 대상이 없을 시 Optional.empty() 가 호출된다.
*
*
* ◆ 4가지의 의존관계 주입 방식 중 어떤 것을 사용해야 할까?
*
* => 항상 생성자 주입을 선택해라!
* 과거에는 수정자 주입과 필드 주입을 많이 사용했지만 최근에는 스프링을 포함한 DI 프레임워크 대부분이 생성자 주입을 권장한다.
* 1) 대부분의 의존관계는 한번 주입되고 나면 애플리케이션 종료시점까지 다시 변경될 일이 없다. 오히려 한번 설정된 의존관계는 변경되면 안된다.(불변해야 한다)
* 2) 수정자 주입을 사용한다면, 불필요한 setter 메서드를 만들고 또 public 으로 열어두기까지 해야한다. setter 메세드를 만드는 건 항상 신중해야 하며 저런 방식은 옳지 못하다.
* 3) 생성자 주입의 경우, 객체를 생성할 때 딱 한번 주입되고 그 이후 주입되지 않으므로, 불변하게 설계할 수 있다.
* 4) 생성자 주입을 사용하면 부수적으로 final 키워드도 사용할 수 있게 되고, 추후 생성자에서 값이 설정되지 않는 오류를 컴파일 시점에서 미리 막을 수 있다. (에러 중 가장 좋은 에러는 컴파일 시점에서 발생하는 오류이다.)
*    ※ 수정자 주입을 포함한 나머지 주입 방식들은 모두 생성자 호출 이후 별도로 호출되므로, 필드에 final 키워드를 사용할 수 없다. 오로지 생성자 주입만이 final 키워드를 사용할 수 있다.
*
* 생성자 주입 방식을 선택하는 이유는 여러 가지가 있지만, 생성자 주입 방식은 프레임워크에 의존하지 않고, 순수한 자바의 특징을 잘 살리는 방법이기도 하다.
* 애플리케이션을 설계할 때에는, 기본적으로 생성자 주입 방식을 사용하고, 필수 값이 아닌 객체에 대해서는 수정자 주입 방식을 이용해서 선택적으로 주입하면 된다.
*
* 항상 생성자 주입을 선택해라! 그리고 가끔 선택적 처리가 필요하다면 그때 수정자 주입을 사용하라.
* 필드 주입은 절대 사용하지 않는다!
*
*
*
*
*
*
*
*
*
*
*
*
* */