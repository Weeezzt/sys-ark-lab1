package org.example.discount;

import org.example.model.Product;

public abstract class BaseDiscount implements Discount{
    Discount nextDiscount;
    String description;
    public BaseDiscount(Discount nextDiscount, String description) {
        this.nextDiscount = nextDiscount;
        this.description = description;
    }

    @Override
    public String getDescription(Product product) {
        if (nextDiscount != null) {
            return description + " + " + nextDiscount.getDescription(product);
        }
        return description;
    }

    @Override
    public double applyDiscount(Product product) {
        double discount = 0;
        if (isApplicable(product)) {
            discount = calculateDiscount(product);
        }

        if (nextDiscount != null) {
            discount += nextDiscount.applyDiscount(product);
        }

        return discount;
    }

    protected abstract boolean isApplicable(Product product);

    protected abstract double calculateDiscount(Product product);

}
