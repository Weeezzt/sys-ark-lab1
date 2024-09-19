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
        if(nextDiscount != null) {
            discount += nextDiscount.applyDiscount(product);
        }
        return discount;
    }

    @Override
    public String getDescription(Product product) {
        if (condition.isApplicable(product) && nextDiscount == null) {
            return description;
        } else if(condition.isApplicable(product) && nextDiscount != null) {
            return description + " + " + nextDiscount.getDescription(product);
        }
        return "";
    }

}
