package com.example.test.operatoroverloading;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        BigInteger x1 = BigInteger.TEN;
        BigInteger x2 = BigInteger.valueOf(120);
        BigInteger x3 = x1 + x2;
        //BigInteger x3 = x1.add(x2);
        BigInteger x4 = BigInteger.valueOf(2) * x3;
        //BigInteger x4 = BigInteger.valueOf(2).multiply(x3);
        BigInteger x5 = x4 - x2 / x1 + x3;
        //BigInteger x5 = x4.subtract(x2.divide(x1)).add(x3);
        System.out.println("x3: " + x3); // x3: 130
        System.out.println("x4: " + x4); // x4: 260
        System.out.println("x5: " + x5); // x5: 378
    }

}
