package com.example.demo.strategy;

import org.springframework.stereotype.Component;

@Component("NONE")
public class NoDiscountStrategy implements DiscountStrategy {
    @Override
    public double calculatePrice(double price) {
        return price;
    }

    @Override
    public String getDiscountName() {
        return "ราคาปกติ (0%)";
    }
}