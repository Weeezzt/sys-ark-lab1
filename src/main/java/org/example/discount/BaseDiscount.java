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
        // Only append this discount's description if it is applicable
        String appliedDescription = "";
        if (isApplicable(product)) {
            appliedDescription = description;
        }

        if (nextDiscount != null) {
            String nextDescription = nextDiscount.getDescription(product);

            if (!appliedDescription.isEmpty() && !nextDescription.isEmpty()) {
                appliedDescription += " + " + nextDescription;
            } else if (!nextDescription.isEmpty()) {
                appliedDescription = nextDescription;
            }
        }

        return appliedDescription;
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
