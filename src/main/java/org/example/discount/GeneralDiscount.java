package org.example.discount;
import org.example.model.Product;

public class GeneralDiscount implements Discount {
    @Override
    public double applyDiscount(Product product) {
        return 0;
    }

    @Override
    public String getDescription(Product product) {
        return "No discount";
    }

}
