package com.company;

import java.util.Comparator;

public class EmployeeStoreCodeComparator implements Comparator<Employee> {
    @Override
    public int compare (Employee employee1, Employee employee2) {
        return employee1.getStoreCode().compareTo(employee2.getStoreCode());
    }
}
