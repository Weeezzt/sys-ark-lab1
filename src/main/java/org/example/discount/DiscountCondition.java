package org.example.discount;
import org.example.model.Product;

@FunctionalInterface
public interface DiscountCondition {
    boolean isApplicable(Product product);
}
