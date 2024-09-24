package org.example.discount;

import jdk.jfr.Description;
import org.example.model.Product;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountTest {

    @Test
    @Description("Test that no discount works as it should")
    public void testNoDiscount() {
        Product product = new Product("Milk", 500, 10);
        Discount noDiscount = new NoDiscount();

        assertEquals(0, noDiscount.applyDiscount(product));
        assertEquals("", noDiscount.getDescription(product));
    }

    @Test
    @Description("Test that friday discount works as it should")
    public void testFridayDiscount() {
        Product product = new Product("Milk", 500, 10);

        Discount fridayDiscount = new FridayDiscount(new NoDiscount());

        int expectedAmount = 0;
        String expectedDesc = "";
        if(LocalDate.now().getDayOfWeek() == DayOfWeek.FRIDAY) {
            expectedAmount = 50;
            expectedDesc = "10% Friday Discount";
        }

        assertEquals(expectedAmount, fridayDiscount.applyDiscount(product));
        assertEquals(expectedDesc, fridayDiscount.getDescription(product));
    }

    @Test
    @Description("Test that milk discount only works for milk")
    public void testMilkDiscountOnlyForMilk() {
        Product product = new Product("Bread", 500, 10);

        Discount fridayDiscount = new FridayDiscount(new NoDiscount());
        Discount milkDiscount = new MilkDiscount(fridayDiscount);

        assertEquals(0, milkDiscount.applyDiscount(product));
        assertEquals("", milkDiscount.getDescription(product));
    }

    @Test
    @Description("Test that milk discount works as it should")
    public void testMilkDiscount() {
        Product product = new Product("Milk", 500, 10);

        Discount fridayDiscount = new FridayDiscount(new NoDiscount());
        Discount milkDiscount = new MilkDiscount(fridayDiscount);

        assertEquals(25, milkDiscount.applyDiscount(product));
        assertEquals("5% Milk Discount", milkDiscount.getDescription(product));
    }

    @Test
    @Description("Test that general disocunt works with one discount")
    public void testGeneralDiscount() {
        Product product = new Product("Milk", 500, 10);

        DiscountCondition condition = p -> p.getName().equals("Milk");
        DiscountCalculator calculator = p -> p.getPrice() * 0.05;
        Discount discount = new GeneralDiscount(condition, calculator, "5% off on milk", new NoDiscount());

        assertEquals(25, discount.applyDiscount(product));
        assertEquals("5% off on milk", discount.getDescription(product));
    }

    @Test
    @Description("Test that quantity discount works with multiple discounts")
    public void testQuantityDiscount() {
        Product product = new Product("Milk", 500, 10);

        Discount fridayDiscount = new FridayDiscount(new NoDiscount());
        Discount milkDiscount = new MilkDiscount(fridayDiscount);
        Discount quantityDiscount = new QuantityDiscount(milkDiscount);

        double expectedDiscount = 125;

        assertEquals(expectedDiscount, quantityDiscount.applyDiscount(product));
        String expectedDescription = "Quantity Discount 10kr/stk + 5% Milk Discount";
        assertEquals(expectedDescription, quantityDiscount.getDescription(product));
    }



    @Test
    @Description("Test that combining discounts works as expected")
    public void testCombinedDiscounts() {
        Product product = new Product("Milk", 500, 10);

        DiscountCondition priceAbove400Condition = p -> p.getPrice() > 400;
        DiscountCalculator priceAbove400Calculator = p -> p.getQuantity() * 5;
        Discount priceAboveDiscount = new GeneralDiscount(priceAbove400Condition, priceAbove400Calculator, "5kr off per item", new NoDiscount());


        DiscountCondition milkCondition = p -> p.getName().equals("Milk");
        DiscountCalculator milkCalculator = p -> p.getPrice() * 0.05;
        Discount combinedDiscount = new GeneralDiscount(milkCondition, milkCalculator, "5% off on milk", priceAboveDiscount);

        double expectedDiscount = 75;

        assertEquals(expectedDiscount, combinedDiscount.applyDiscount(product));
        String expectedDescription = "5% off on milk + 5kr off per item";
        assertEquals(expectedDescription, combinedDiscount.getDescription(product));
    }
}
