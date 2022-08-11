package com.example.springbasic.singleton;

public class SingletonService {

  private static final SingletonService instance = new SingletonService();

  public static SingletonService getInstance() {
    return instance;
  }

  private SingletonService() {}

  public void method() {
    System.out.println("singleton object");
  }

  /*
  * static 영역에 인스턴스를 미리 하나 생성해서 메모리에 올려둔다.
  * 이 객체 인스턴스가 필요하면 오직 getInstance() 메서드를 통해서만 조회할 수 있다. 이 메서드를 호출하면 당연히 항상 같은 인스턴스를 반환한다.
  * 딱 1개의 인스턴스만 존재해야 하므로 private 키워드를 이용해 생성자를 막는다. 이렇게 하면 외부에서 new 연산자를 이용해 또 다른 인스턴스를 생성하는 것을 막을 수 있다.
  *
  * ※ 싱글톤 패턴은 하나의 패턴으로써, 그 구현 방법에는 한 가지만 있는 것이 아니라 다양한 방식이 존재한다.
  * 여기서는 객체를 미리 생성해 저장해두고 생성자를 막는 방법을 사용하였다.
  *
  *
  *
  * */
}
