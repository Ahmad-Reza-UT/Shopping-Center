package com.company;

import java.util.Comparator;

public class CustomerStoreCodeComparator implements Comparator<Customer> {
    @Override
    public int compare (Customer customer1, Customer customer2) {
        return customer1.getStoreCode().compareTo(customer2.getStoreCode());
    }
}
