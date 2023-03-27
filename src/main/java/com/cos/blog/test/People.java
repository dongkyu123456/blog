package com.cos.blog.test;

public class People {
    int hungryState = 50; // 100
    void eat(){
        hungryState += 10;
    }

    public static void main(String[] args) {
        People p = new People();
        p.hungryState = 100; //변수에 직접 접근 => 객체지향과 맞지 않음
    }
}
