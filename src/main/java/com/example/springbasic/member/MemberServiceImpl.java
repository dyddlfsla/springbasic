package com.example.springbasic.member;

public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;

  public MemberServiceImpl(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Override
  public void join(Member member) {
    memberRepository.save(member);
  }

  @Override
  public Member findMember(Long memberId) {
    return memberRepository.findById(memberId);
  }

  public MemberRepository getMemberRepository() {
    return memberRepository;
  }
}

/*
 * private final MemberRepository memberRepository = new MemoryMemberRepository();
 * 설계 변경으로 MemberServiceImpl 은 이제 더 이상 MemoryMemberRepository 를 의존하지 않는다.
 * 단지 MemberRepository 인터페이스에만 의존한다.
 * MemberServiceImpl 입장에서는 생성자를 통해 어떤 구현 객체가 들어올지(주입될지)는 알 수 없다.
 * MemberServiceImpl 의 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부(AppConfig) 에서 결정된다.
 * MemberServiceImpl 은 이제부터 의존관계에 대한 고민은 외부에 맡기고 자신의 역할 실행에만 집중하면 된다.
 *
 * 객체의 생성과 연결(주입)은 AppConfig 가 담당한다.(공연 기획자)
 * DIP 완성: MemberServiceImpl 은 인터페이스 MemberRepository 에만 의존하면 된다. 이제 구현 클래스를 몰라도 된다.
 * 관심사의 분리: 객체를 생성하고 연결하는 역할과 실행하는 역할이 명확히 분리되었다.
 *
 * */
