package com.company;

import java.util.Comparator;

public class CustomerFullNameComparator implements Comparator<Customer> {
    @Override
    public int compare (Customer customer1, Customer customer2) {
        int compareResult = customer1.getFirstName().compareTo(customer2.getFirstName());
        if (compareResult == 0) {
            compareResult = customer1.getLastName().compareTo(customer2.getLastName());
        }
        return compareResult;
    }
}
