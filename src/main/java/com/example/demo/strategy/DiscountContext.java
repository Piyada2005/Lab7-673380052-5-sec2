package com.example.demo.strategy;

import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class DiscountContext {

    private final Map<String, DiscountStrategy> strategies;

    // Inject Strategies ทั้งหมดผ่าน Map อัตโนมัติด้วย Spring DI (ตามหลัก OCP & DIP)
    public DiscountContext(Map<String, DiscountStrategy> strategies) {
        this.strategies = strategies;
    }

    public DiscountStrategy getStrategy(String discountType) {
        if (discountType == null) {
            return strategies.get("NONE");
        }
        return strategies.getOrDefault(discountType.toUpperCase(), strategies.get("NONE"));
    }

    public double calculatePrice(String discountType, double price) {
        return getStrategy(discountType).calculatePrice(price);
    }

    public String getDiscountName(String discountType) {
        return getStrategy(discountType).getDiscountName();
    }
}