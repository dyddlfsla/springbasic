package com.example.springbasic.singleton;

public class StatefulService {

  private int price;

  public void order(String name, int price) {
    System.out.println("name = " + name + ", price = " + price);
    this.price = price;
  }

  public int getPrice() {
    return price;
  }

}

/*
* StatefulService 의 price 필드는 공유되는 필드이다.
*
*
* */