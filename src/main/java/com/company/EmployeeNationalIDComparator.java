package com.company;

import java.util.Comparator;

public class EmployeeNationalIDComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee employee1, Employee employee2) {
        return employee1.getNationalID().compareTo(employee2.getNationalID());
    }
}
