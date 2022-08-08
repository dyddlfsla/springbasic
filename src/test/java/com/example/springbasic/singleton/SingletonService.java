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

}
