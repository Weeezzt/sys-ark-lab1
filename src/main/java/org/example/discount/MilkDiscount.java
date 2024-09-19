package org.example.discount;

import org.example.model.Product;
public class MilkDiscount extends BaseDiscount {
    public MilkDiscount(Discount nextDiscount) {
        super(nextDiscount, "5% Milk Discount");
    }

    @Override
    protected boolean isApplicable(Product product) {
        return product.getName().equals("Milk");
    }

    @Override
    protected double calculateDiscount(Product product) {
        return product.getPrice() * 0.05;
    }

}
