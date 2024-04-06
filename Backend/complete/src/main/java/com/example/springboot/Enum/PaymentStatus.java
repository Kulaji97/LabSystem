package com.example.springboot.Enum;

public final class PaymentStatus {
    public static final int AWAITING_PAYMENT = 0;
    public static final int PARTIAL = 1;
    public static final int PAID = 2;

    // If you have only static members and want to simulate a static
    // class in Java, then you can make the constructor private.
    private PaymentStatus() {}
}