package org.example;

import org.example.discount.*;
import org.example.model.Product;

import java.time.DayOfWeek;
import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {

            FridayDiscount fridayDiscount = new FridayDiscount(new NoDiscount());
            MilkDiscount milkDiscount = new MilkDiscount(fridayDiscount);
            QuantityDiscount quantityDiscount = new QuantityDiscount(milkDiscount);

            quantityDiscount.applyDiscount(new Product("Milk", 500, 10));
            Discount combinedDiscount1 = quantityDiscount;
            double combinedDiscountValue1 = combinedDiscount1.applyDiscount(new Product("Milk", 500, 10));

            System.out.println("-------------------------------------------------");
            System.out.println("Total Discount Applied: " + combinedDiscountValue1);
            System.out.println("Description of Applied Discounts: " + combinedDiscount1.getDescription(new Product("Milk", 500, 10)));
            System.out.println("-------------------------------------------------");


            Product milk = new Product("Milk", 500, 10);  // Price: 500, Quantity: 10

            DiscountCondition fridayCondition = product -> LocalDate.now().getDayOfWeek() == DayOfWeek.FRIDAY;
            DiscountCalculator fridayCalculator = product -> product.getPrice() * 0.1;
            Discount fridayDiscount1 = new GeneralDiscount(fridayCondition, fridayCalculator, "10% Friday Discount", new NoDiscount());


            DiscountCondition priceAbove400Condition = product -> product.getPrice() > 400;
            DiscountCalculator priceAbove400Calculator = product -> product.getQuantity() * 5;
            Discount priceAbove400Discount = new GeneralDiscount(priceAbove400Condition, priceAbove400Calculator, "5kr off per item", fridayDiscount1);


            DiscountCondition milkCondition = product -> product.getName().equals("Milk");
            DiscountCalculator milkCalculator = product -> product.getPrice() * 0.05;
            Discount combinedDiscount = new GeneralDiscount(milkCondition, milkCalculator, "5% of on milk", priceAbove400Discount);

            String combinedDescription = combinedDiscount.getDescription(milk);

            System.out.println("Product: " + milk.getName());
            System.out.println("Original Price: " + milk.getPrice());
            System.out.println("Quantity: " + milk.getQuantity());
            System.out.println("Description of Applied Discounts: " + combinedDescription);
            System.out.println("Total Discount Applied: " + combinedDiscountValue1);
            System.out.println("Price After Discount: " + (milk.getPrice() - combinedDiscountValue1));
    }
}