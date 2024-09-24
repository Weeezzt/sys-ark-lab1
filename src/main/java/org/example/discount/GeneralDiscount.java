package org.example.discount;
import org.example.model.Product;

public class GeneralDiscount implements Discount {
    private final DiscountCondition condition;
    private final DiscountCalculator calculator;
    private final String description;
    Discount nextDiscount;

    public GeneralDiscount(DiscountCondition condition, DiscountCalculator calculator, String description, Discount nextDiscount) {
        this.condition = condition;
        this.calculator = calculator;
        this.description = description;
        this.nextDiscount = nextDiscount;
    }

    @Override
    public double applyDiscount(Product product) {
        double discount = 0;
        if (condition.isApplicable(product)) {
            discount = calculator.calculateDiscount(product);
        }

        discount += nextDiscount.applyDiscount(product);
        return discount;
    }

    @Override
    public String getDescription(Product product) {
        String appliedDescription = "";
        if (condition.isApplicable(product)) {
            appliedDescription = description;
        }

        String nextDescription = nextDiscount.getDescription(product);
        if (!appliedDescription.isEmpty() && !nextDescription.isEmpty()) {
            appliedDescription += " + " + nextDescription;
        } else if (!nextDescription.isEmpty()) {
            appliedDescription = nextDescription;
        }

        return appliedDescription;
    }
}
