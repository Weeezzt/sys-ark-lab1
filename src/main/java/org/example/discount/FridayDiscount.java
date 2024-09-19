package org.example.discount;

import org.example.model.Product;
import java.time.LocalDate;
import java.time.DayOfWeek;



public class FridayDiscount extends BaseDiscount{

    public FridayDiscount(Discount nextDiscount) {
        super(nextDiscount, "10% Friday Discount");
    }
    @Override
    protected boolean isApplicable(Product product) {
        LocalDate today = LocalDate.now();
        return today.getDayOfWeek() == DayOfWeek.FRIDAY;
    }

    @Override
    protected double calculateDiscount(Product product) {
        return product.getPrice() * 0.1;
    }
}
