package com.company;

import java.util.Comparator;

public class EmployeeFullNameComparator implements Comparator<Employee> {
    @Override
    public int compare (Employee employee1, Employee employee2) {
        int compareResult = employee1.getFirstName().compareTo(employee2.getFirstName());
        if (compareResult == 0) {
            compareResult = employee1.getLastName().compareTo(employee2.getLastName());
        }
        return compareResult;
    }
}
