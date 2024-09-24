package org.example.discount;

import org.example.model.Product;

public class NoDiscount implements Discount{
    @Override
    public double applyDiscount(Product product) {
        return 0;
    }

    @Override
    public String getDescription(Product product) {
        return "";
    }
}
