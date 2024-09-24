package org.example.discount;

import org.example.model.Product;

public class QuantityDiscount extends BaseDiscount {

    public QuantityDiscount(Discount nextDiscount) {
        super(nextDiscount, "Quantity Discount 10kr/stk");
    }

    @Override
    protected boolean isApplicable(Product product) {
        return product.getQuantity() >= 5;
    }

    @Override
    protected double calculateDiscount(Product product) {
        return product.getQuantity() * 10;
    }
}
