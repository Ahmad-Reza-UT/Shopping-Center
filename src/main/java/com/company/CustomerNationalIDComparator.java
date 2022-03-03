package com.company;

import java.util.Comparator;

public class CustomerNationalIDComparator implements Comparator<Customer> {
    @Override
    public int compare(Customer customer1, Customer customer2) {
        return customer1.getNationalID().compareTo(customer2.getNationalID());
    }
}
