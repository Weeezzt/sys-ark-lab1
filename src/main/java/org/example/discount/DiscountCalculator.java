package org.example.discount;
import org.example.model.Product;

@FunctionalInterface
public interface DiscountCalculator {
    double calculateDiscount(Product product);
}
