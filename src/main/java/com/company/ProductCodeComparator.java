package com.company;

import java.util.Comparator;

public class ProductCodeComparator implements Comparator<Product> {
    @Override
    public int compare (Product product1, Product product2) {
        return product1.getProductCode().compareTo(product2.getProductCode());
    }
}
