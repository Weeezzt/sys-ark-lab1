package org.example;

import org.example.discount.Discount;
import org.example.discount.FridayDiscount;
import org.example.discount.MilkDiscount;
import org.example.discount.QuantityDiscount;
import org.example.model.Product;


public class Main {
    public static void main(String[] args) {
            // Create some products
            Product milk = new Product("Milk", 500, 10);  // Price: 30, Quantity: 3

            // Create discounts
            Discount milkieDiscount = new MilkDiscount(null); // No next discount
            Discount quantityDiscount = new QuantityDiscount(milkieDiscount); // Next discount is milkieDiscount
            Discount fridayDiscount = new FridayDiscount(quantityDiscount);

            String milkDescription = quantityDiscount.getDescription(milk);


            // Apply discounts to products
            double milkDiscount = fridayDiscount.applyDiscount(milk);

            // Print results
            System.out.println("Product: " + milk.getName());
            System.out.println("Original Price: " + milk.getPrice());
            System.out.println("Quantity: " + milk.getQuantity());
            System.out.println("Description: " + milkDescription);
            System.out.println("Discount Applied: " + milkDiscount);
            System.out.println("Price After Discount: " + (milk.getPrice() - milkDiscount));
            System.out.println();
    }
}