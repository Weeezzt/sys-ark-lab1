package org.example.discount;
import org.example.model.Product;

public interface Discount {
  double applyDiscount(Product product);

  String getDescription(Product product);

}
