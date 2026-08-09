package com.example.demo.strategy;

import org.springframework.stereotype.Component;

@Component("SEASONAL")
public class SeasonalSaleStrategy implements DiscountStrategy {
    @Override
    public double calculatePrice(double price) {
        return price * 0.80;
    }

    @Override
    public String getDiscountName() {
        return "ส่วนลดเทศกาล (20%)";
    }
}