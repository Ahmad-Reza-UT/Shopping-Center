package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class Employee extends Person{

    /* Fields */
    private String password;
    private String storeCode;
    private String salary;
    private static ArrayList<Employee> allEmployees = new ArrayList<>();

    /* Setter and Getters */
    public String getPassword() {
        return password;
    }

    public String getStoreCode () {
        return storeCode;
    }

    public String getSalary() {
        return salary;
    }

    public void setPassword() {
        password = Manager.getValidPassword();
    }

    public void setSalary() {
        salary = getValidSalary();
    }

    public void setStoreCode() {
        storeCode = Manager.getValidStoreCode();
    }

    public static ArrayList<Employee> getAllEmployees() {
        return allEmployees;
    }

    /* Constructor */
    /* Default constructor */
    public Employee() {
        super();
        this.storeCode = Manager.getValidStoreCode();
        this.password = Manager.getValidPassword();
        this.salary = getValidSalary();
        allEmployees.add(this);
        Thread employee = new Thread(new SignedUpPersonRunnable());
        employee.start();
    }

    /* following constructor is for reading data from .Json file */
    public Employee(String firstName, String lastName, String gender, String nationalID, String phoneNumber, Long age, String storeCode, String password, String salary) {
        super(firstName, lastName, gender, nationalID, phoneNumber, age);
        this.storeCode = storeCode;
        this.password = password;
        this.salary = salary;
        allEmployees.add(this);
        Thread employee = new Thread(new SignedUpPersonRunnable());
        employee.start();
    }

    /* following constructors are for Collections binary search */
    public Employee (String firstName, String lastName) {
        super(firstName, lastName);
    }

    public Employee (String storeCode) {
        super(storeCode);
        this.storeCode = storeCode;
    }

    public Employee (String nationalID, int i) {
        super(nationalID, i);
    }

    /**
     *     Methods contains:
     *     Search (by full name, storeCode), sort (by full name, storeCode) and Print all information of all customers,
     *     Search (by name, productCode) sort (by sellingPrice, productNumber) and Print all information in all products,
     *     adding a product with field information (by constructor),
     *     removing a product with product code,
     *     printing status of a customer (all fields information and purchase history),
    */

    /* <-------------------- Getting and Validation Methods --------------------> */
    /* getValidSalary */
    public String getValidSalary() {
        System.out.print("-----> Enter Salary: ");
        String salary = scanner.nextLine();
        while (doublePriceValidation(salary) != null) {
            System.out.print(doublePriceValidation(salary) + "\n-----> Please Enter valid Salary: ");
            salary = scanner.nextLine();
        }
        return salary;
    }

    public String doublePriceValidation(String test) {
        for (int i = 0; i < test.length(); i++) {
            /* Using ASCII Charset to Limiting */
            if (!((test.charAt(i) >= 48 && test.charAt(i) <= 57) || test.charAt(i) == 32 || test.charAt(i) == 46))
                return "***** You Entered Character(s) that aren't Number or point. *****";
        }
        return null;
    }

    /* printing */
    public static void printEmployees() {
        System.out.println("All Employee: ");
        for (int i = 0; i < allEmployees.size(); i++) {
            System.out.println(allEmployees.get(i));
            System.out.println();
        }
    }

    /* sort by name */
    public static void sortEmployeesByName() {
        Collections.sort(allEmployees, new EmployeeFullNameComparator());
    }

    /* sort by storeCode */
    public static void sortEmployeesByStoreCode() {
        Collections.sort(allEmployees, new EmployeeStoreCodeComparator());
    }

    /* sort by NationalID */
    public static void sortEmployeesByNationalID() {
        Collections.sort(allEmployees, new EmployeeNationalIDComparator());
    }

    /* Search by name */
    public static int searchInEmployeesByFullName(String firstName, String lastName) {
        sortEmployeesByName();
        Employee keyEmployee = new Employee(firstName, lastName);
        return Collections.binarySearch(allEmployees, keyEmployee, new EmployeeFullNameComparator());
        /* if this method returned a number that is equal or less than -1, there is no Employee with that name */
    }

    /* Search by storeCode */
    public static int searchInEmployeesByStoreCode(String storeCode) {
        sortEmployeesByStoreCode();
        Employee keyEmployee = new Employee(storeCode);
        return Collections.binarySearch(allEmployees, keyEmployee, new EmployeeStoreCodeComparator());
        /* if this method returned a number that is equal or less than -1, there is no Employee with that productCode */
    }

    /* Search by nationalID */
    public static int searchInEmployeesByNationalID(String nationalID) {
        sortEmployeesByNationalID();
        Employee keyEmployee = new Employee(nationalID, 1);
        return Collections.binarySearch(allEmployees, keyEmployee, new EmployeeNationalIDComparator());
        /* if this method returned a number that is equal or less than -1, there is no Employee with that name */
    }

    public static Employee indexToInstance(int indexOfKey) {
        return allEmployees.get(indexOfKey);
    }


    /* ***************************************** Customers Supervise ************************************************ */
    /* Printing customer */
    public void printCustomers() {
        Customer.printCustomer();
    }

    /* Sort customers by name */
    public void sortCustomersByName() {
        Customer.sortCustomersByName();
    }

    /* Sort customers by productCode */
    public void sortCustomersByStoreCode() {
        Customer.sortCustomersByStoreCode();
    }

    /* Sort customers by productCode */
    public void sortCustomersByNationalID() {
        Customer.sortCustomersByNationalID();
    }

    /* Search in customers with name */
    public int searchInCustomersWithName (String firstName, String lastName) {
        return Customer.searchInCustomersByFullName(firstName, lastName);
    }

    /* Search in customers with productCode */
    public int searchInCustomersWithStoreCode (String storeCode) {
        return Customer.searchInCustomersByStoreCode(storeCode);
    }

    /* Printing result of search */
    public void resultOfCustomersSearch(int indexOfKey) {
        if (indexOfKey <= -1) {
            System.out.println("There is no customer with this name! ");
        }
        else {
            System.out.println(Customer.getAllCustomers().get(indexOfKey).toString());
        }
    }

    /* Printing Status of a customer */
    public void customerStatus (String storeCode) {
        int indexOfKey = searchInCustomersWithStoreCode(storeCode);
        if (indexOfKey <= -1) {
            System.out.println("***** There is no customer with this information! *****");
        }
        else {
            System.out.println(Customer.getAllCustomers().get(indexOfKey).toString());
            Customer.getAllCustomers().get(indexOfKey).printingHistoryOfPurchase();
        }
    }

    /* ****************************************** Products Supervise ************************************************ */
    /* Printing products */
    public void printProducts(ArrayList<Product> products) { /* products can be Product.allProduct, Product.availableProduct or Product.soldProduct */
        Product.printProducts(products);
    }

    /* Sort products by name */
    public void sortProductsByName(ArrayList<Product> products) { /* products can be Product.allProduct, Product.availableProduct or Product.soldProduct */
        Product.sortProductsByName(products);
    }

    /* Sort products by productCode */
    public void sortProductsByProductCode(ArrayList<Product> products) { /* products can be Product.allProduct, Product.availableProduct or Product.soldProduct */
        Product.sortProductsByProductCode(products);
    }

    /* Search in products with name */
    public int searchInProductsWithName (ArrayList<Product> products, String name) { /* products can be Product.allProduct, Product.availableProduct or Product.soldProduct */
        return Product.searchInProductsByName(products, name);
    }

    /* Search in products with productCode */
    public int searchInProductsWithProductCode (ArrayList<Product> products, String productCode) { /* products can be Product.allProduct, Product.availableProduct or Product.soldProduct */
        return Product.searchInProductByProductCode(products, productCode);
    }

    /* Printing result of search */
    public void resultOfProductSearch(ArrayList<Product> products, int indexOfKey, String searchIn) {
        if (indexOfKey <= -1) {
            System.out.println("There is no " + searchIn + " with this name! ");
        }
        else {
            System.out.println(products.get(indexOfKey).toString());
        }
    }


    /* Adding Product */
    public void addProduct () {
        new Product();
    }

    /* Removing Product */
    public void removeProduct () {
        Manager.removeProduct();
    }

    @Override
    public String toString() {
        return "-----> Full Name: " + this.getFirstName() + " " + this.getLastName() + "     Gender: " + this.getGender() + "     National ID: " + this.getNationalID() + "     Phone Number: " + this.getPhoneNumber() + "     Age: " + this.getAge() + "     Store Code: " + this.storeCode + "     Password: " + password + "     Salary: " + salary;
    }
}
