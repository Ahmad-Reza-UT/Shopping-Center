package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class Customer extends Person {

    /* Fields */
    private String password;
    private String storeCode;
    private ArrayList<Purchase> allPurchases = new ArrayList<>();
    private static ArrayList<Customer> allCustomers = new ArrayList<>();

    /* Setter and Getters */
    public static ArrayList<Customer> getAllCustomers () {
        return allCustomers;
    }

    public String getStoreCode () {
        return storeCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword() {
        storeCode = Manager.getValidPassword();
    }

    public void setStoreCode() {
        storeCode = Manager.getValidStoreCode();
    }

    public ArrayList<Purchase> getAllPurchases() {
        return allPurchases;
    }

    /* Constructor */
    /* Default constructor */
    public Customer () {
        super();
        this.storeCode = Manager.getValidStoreCode();
        this.password = Manager.getValidPassword();
        allCustomers.add(this);
        Thread customer = new Thread(new SignedUpPersonRunnable());
        customer.start();
    }

    /* following constructor is for reading data from .Json file */
    public Customer(String firstName, String lastName, String gender, String nationalID, String phoneNumber, Long age, String storeCode, String password, ArrayList<Purchase> allPurchases) {
        super(firstName, lastName, gender, nationalID, phoneNumber, age);
        this.storeCode = storeCode;
        this.password = password;
        this.allPurchases = allPurchases;
        allCustomers.add(this);
        Thread customer = new Thread(new SignedUpPersonRunnable());
        customer.start();
    }

    /* following constructors are for Collections binary search */
    public Customer(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public Customer(String storeCode) {
        super(storeCode);
        this.storeCode = storeCode;
    }

    public Customer(String nationalID, int i) {
        super(nationalID, i);
    }

    /**
     * Methods contains:
     *     Printing list of all purchase information sorted by purchaseDate,
     *     Creating new shopping list with productCode,
     *     Printing all products and filter products by availability and ability to search,
    */
    /* <---------------------------- Other Method ------------------------------> */
    public void printingHistoryOfPurchase () {
        System.out.println("History of All Purchase: ");
        for (int i = 0; i < allPurchases.size(); i++) {
            System.out.println("Purchase Number " + (i + 1) + ": ");
            System.out.println(allPurchases.get(i).toString());
            System.out.println();
        }
    }

    public void newShoppingList () {
        System.out.print("How many product do you want to buy? ");
        String numberOfProductString = scanner.nextLine();
        Integer numberOfProduct = Menu.preventClientWrongChoice(Product.getAvailableProducts().size(), numberOfProductString);
        if (numberOfProduct == null) {
            return;
        }
        ArrayList<String> consideredProducts = new ArrayList<>();
        ArrayList<Long> consideredNumbers = new ArrayList<>();
        String consideredProductCode = null;
        Integer consideredNumber = 0;
        for (int i = 0; i < numberOfProduct; i++) {
            boolean isEnteredProductCodeValid = false;
            while (!isEnteredProductCodeValid) {
                System.out.print("Please enter product code for product " + (i + 1) + ": ");
                consideredProductCode = scanner.nextLine();
                int indexOfProductCodeInAvailableProducts = Product.searchInProductByProductCode(Product.getAvailableProducts(), consideredProductCode);
                if (indexOfProductCodeInAvailableProducts <= -1) {
                   System.out.println("Sorry! Your product didn't found!");
                   continue;
                }
                System.out.println(Product.getAvailableProducts().get(indexOfProductCodeInAvailableProducts).toStringForCustomer());
                System.out.print("Is this your considered product (if yes enter 1 else enter 2 to enter product code again): ");
                String customerValidationString = scanner.nextLine();
                Integer customerValidation = Menu.preventClientWrongChoice(2 ,customerValidationString);
                if (customerValidation == null) {
                    continue;
                }
                switch (customerValidation) {
                    case 1: {
                        Product consideredProduct = Product.productFinder(consideredProductCode);
                        boolean isConsideredNumberValid = false;
                        while (!isConsideredNumberValid) {
                            System.out.print("Please enter number of this product that you want to buy: ");
                            String consideredNumberString = scanner.nextLine();
                            consideredNumber = Menu.preventClientWrongChoice(consideredProduct.getNumberOfAvailableProduct(), consideredNumberString);
                            if (consideredNumber == null) {
                                continue;
                            }
                            isConsideredNumberValid = true;
                        }
                        break;
                    }
                    case 2: {
                        continue;
                    }
                }
                isEnteredProductCodeValid = true;
            }
            consideredProducts.add(consideredProductCode);
            consideredNumbers.add((long)consideredNumber);
        }
        Purchase purchase = new Purchase(consideredProducts, consideredNumbers, this);
    }

    /* printing */
    public static void printCustomer() {
        System.out.println("All Customers: ");
        for (int i = 0; i < allCustomers.size(); i++) {
            System.out.println(allCustomers.get(i).toString());
            System.out.println();
        }
    }

    /* sort by name */
    public static void sortCustomersByName() {
        Collections.sort(allCustomers, new CustomerFullNameComparator());
    }

    /* sort by productCode */
    public static void sortCustomersByStoreCode() {
        Collections.sort(allCustomers, new CustomerStoreCodeComparator());
    }

    /* sort by nationalID */
    public static void sortCustomersByNationalID() {
        Collections.sort(allCustomers, new CustomerNationalIDComparator());
    }

    /* Search by name */
    public static int searchInCustomersByFullName(String firstName, String lastName) {
        sortCustomersByName();
        Customer keyCustomer = new Customer(firstName, lastName);
        return Collections.binarySearch(allCustomers, keyCustomer, new CustomerFullNameComparator());
        /* if this method returned a number that is equal or less than -1, there is no product with that name */
    }

    /* Search by storeCode */
    public static int searchInCustomersByStoreCode(String storeCode) {
        sortCustomersByStoreCode();
        Customer keyCustomer = new Customer(storeCode);
        return Collections.binarySearch(allCustomers, keyCustomer, new CustomerStoreCodeComparator());
        /* if this method returned a number that is equal or less than -1, there is no product with that productCode */
    }

    /* Search by nationalID */
    public static int searchInCustomersByNationalID(String nationalID) {
        sortCustomersByNationalID();
        Customer keyCustomer = new Customer(nationalID, 0);
        return Collections.binarySearch(allCustomers, keyCustomer, new CustomerNationalIDComparator());
        /* if this method returned a number that is equal or less than -1, there is no product with that productCode */
    }

    public static Customer indexToInstance(int indexOfKey) {
        return allCustomers.get(indexOfKey);
    }

    /* ****************************************** Products Supervise ************************************************ */
    /* Printing products */
    public void printProducts(ArrayList<Product> products) { /* products can be Product.allProduct, Product.availableProduct or Product.soldProduct */
        Product.printProducts(products);
    }

    /* Printing products */
    public void printProductsForCustomer(ArrayList<Product> products) { /* products can be Product.allProduct, Product.availableProduct or Product.soldProduct */
        Product.printProductsForCustomers(products);
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

    public void resultOfProductSearchForCustomer(ArrayList<Product> products, int indexOfKey, String searchIn) {
        if (indexOfKey <= -1) {
            System.out.println("There is no " + searchIn + " with this name! ");
        }
        else {
            System.out.println(products.get(indexOfKey).toStringForCustomer());
        }
    }

    @Override
    public String toString () {
        return "Full Name: " + this.getFirstName() + " " + this.getLastName() + "     Gender: " + this.getGender() + "     National ID: " + this.getNationalID() + "     Phone Number: " + getPhoneNumber() + "     age: " + this.getAge() + "     password: " + password  + "     store code: " + storeCode;
    }
}
