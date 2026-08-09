package com.example.demo.strategy;

public interface DiscountStrategy {
    double calculatePrice(double price);
    String getDiscountName();
}