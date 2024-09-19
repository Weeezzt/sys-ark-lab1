package org.example;

import org.example.discount.*;
import org.example.model.Product;

import java.time.DayOfWeek;
import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {
            // Create a product
            Product milk = new Product("Milk", 500, 10);  // Price: 500, Quantity: 10

            DiscountCondition fridayCondition = product -> LocalDate.now().getDayOfWeek() == DayOfWeek.FRIDAY;
            DiscountCalculator fridayCalculator = product -> product.getPrice() * 0.1;
            Discount fridayDiscount = new GeneralDiscount(fridayCondition, fridayCalculator, "10% Friday Discount", null);


            DiscountCondition priceAbove400Condition = product -> product.getPrice() > 400;
            DiscountCalculator priceAbove400Calculator = product -> product.getQuantity() * 5;
            Discount priceAbove400Discount = new GeneralDiscount(priceAbove400Condition, priceAbove400Calculator, "5kr off per item", fridayDiscount);


            DiscountCondition milkCondition = product -> product.getName().equals("Milk");
            DiscountCalculator milkCalculator = product -> product.getPrice() * 0.05;
            Discount combinedDiscount = new GeneralDiscount(milkCondition, milkCalculator, "5% of on milk", priceAbove400Discount);

            // Get the description of all discounts
            String combinedDescription = combinedDiscount.getDescription(milk);

            // Apply the discount chain
            double combinedDiscountValue = combinedDiscount.applyDiscount(milk);

            // Print results
            System.out.println("Product: " + milk.getName());
            System.out.println("Original Price: " + milk.getPrice());
            System.out.println("Quantity: " + milk.getQuantity());
            System.out.println("Description of Applied Discounts: " + combinedDescription);
            System.out.println("Total Discount Applied: " + combinedDiscountValue);
            System.out.println("Price After Discount: " + (milk.getPrice() - combinedDiscountValue));
    }
}