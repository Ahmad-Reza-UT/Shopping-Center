package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Manager extends Person {

    /* Fields */
    private String storeCode;
    private String password;
    private static ArrayList<Manager> managers = new ArrayList<>();
    private static Manager oneManager;

    /* Constructor */
    /* Default constructor */
    public Manager() {
        super();
        this.storeCode = getValidStoreCode();
        this.password = getValidPassword();
        oneManager = this;
        managers.add(this);
        Thread manager = new Thread(new SignedUpPersonRunnable());
        manager.start();
    }

    /* following constructor is for reading data from .Json file */
    public Manager(String firstName, String lastName, String gender, String nationalID, String phoneNumber, Long age, String storeCode, String password) {
        super(firstName, lastName, gender, nationalID, phoneNumber, age);
        this.storeCode = storeCode;
        this.password = password;
        oneManager = this;
        managers.add(this);
        Thread manager = new Thread(new SignedUpPersonRunnable());
        manager.start();
    }

    /* Getters */
    public String getStoreCode() {
        return storeCode;
    }

    public String getPassword() {
        return password;
    }

    public static Manager getOneManager() {
        return oneManager;
    }

    public static ArrayList<Manager> getManagers() {
        return managers;
    }


    /** Methods contain:
    * add employee,
    * remove employee,
    * change employee information,
    * add customer,
    * remove customer,
    * change customer information,
    * add product,
    * remove product,
    * change product information,
    * sort employees by fullName and storeCode,
    * sort customers by fullName and storeCode,
    * sort product by name and productCode (all, available, sold),
    * search in employees by fullName and storeCode,
    * search in customers by fullName and storeCode,
    * search in product by name and productCode (all, available, sold),
    * printing profit of store based on buying price and selling price,
    * */

    /* <-------------------- Getting and Validation Methods --------------------> */
    /* getValidStoreCode */
    public static String getValidStoreCode() {
        System.out.print("-----> Enter store Code (5 Numbers): ");
        Scanner scanner = new Scanner(System.in);
        String storeCode = scanner.nextLine();
        int idealLength = 5;
        while (characterValidation(storeCode) != null || lengthValidation(storeCode, idealLength) != null || isEqualStoreCodeExist(storeCode)) {
            while (characterValidation(storeCode) != null) {
                System.out.print(characterValidation(storeCode) + "\n-----> Enter store Code Again (" + idealLength + " Numbers): ");
                storeCode = scanner.nextLine();
            }
            while (lengthValidation(storeCode, idealLength) != null) {
                System.out.print(lengthValidation(storeCode, idealLength) + "\n-----> Enter  store Code Again (" + idealLength + " Numbers): ");
                storeCode = scanner.nextLine();
            }
            while (isEqualStoreCodeExist(storeCode)) {
                System.out.print("***** There is a person in this store with this store code *****\n-----> Please enter valid store code: ");
                storeCode = scanner.nextLine();
            }
        }
        return storeCode;
    }

    public static String getValidPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("-----> Enter password (Using just letters and numbers | Password should contain at least 8 characters): ");
        String password = scanner.nextLine();
        while (!passwordValidation(password)) {
            System.out.print("***** Your Entered password is invalid *****\n-----> Please Enter password Again: ");
            password = scanner.nextLine();
        }
        return password;
    }

    public static boolean passwordValidation(String password) {
        if (password.length() < 8) {
            return false;
        }
        for (int i = 0; i < password.length(); i++)
            if ((!((password.charAt(i) >= 65 && password.charAt(i) <= 90) ||
                    (password.charAt(i) >= 97 && password.charAt(i) <= 122||
                            ((password.charAt(i) >= 48) && (password.charAt(i) <= 57))))||
                    password.charAt(i) ==32 )) { /* Using ASCII Charset to Limiting */
                return false;
            }
        return true;
    }

    public static String characterValidation(String test) {
        for (int i = 0; i < test.length(); i++) {
            /* Using ASCII Charset to Limiting */
            if (!((test.charAt(i) >= 48 && test.charAt(i) <= 57) || test.charAt(i) == 32))
                return "***** You Entered Character(s) that aren't Number. *****";
        }
        return null;
    }

    public static String lengthValidation(String test , int idealLength){
        if (idealLength > test.length())
            return "***** Your Entered Information is Smaller then Standard Case. *****";
        else if (idealLength < test.length())
            return "***** Your Entered Information is Longer than Standard Case. *****";
        else
            return null;
    }

    public static boolean isEqualStoreCodeExist (String storeCode) {
        for (int i = 0; i < Employee.getAllEmployees().size(); i++) {
            if (storeCode.equals(Employee.getAllEmployees().get(i).getStoreCode())) {
                return true;
            }
        }
        for (int i = 0; i < Customer.getAllCustomers().size(); i++) {
            if (storeCode.equals(Customer.getAllCustomers().get(i).getStoreCode())) {
                return true;
            }
        }
        if (oneManager != null) {
            if (storeCode.equals(Manager.getOneManager().getStoreCode())) {
                return true;
            }
        }
        return false;
    }

    /* ****************************************** Employees Supervise ************************************************ */
    /* printing employees */
    public void printEmployees() {
        Employee.printEmployees();
    }

    /* add Employee */
    public Employee addEmployee() {
        return new Employee();
    }

    /* remove Employee */
    public void removeEmployee() {
        synchronized(Employee.getAllEmployees()) {
            boolean isRemoveValid = false;
            while (!isRemoveValid) {
                System.out.print("-----> Please enter store Code of Employee that you want to remove: ");
                String storeCode = scanner.nextLine();
                ArrayList<Employee> allEmployees = Employee.getAllEmployees();
                int indexOfKey = Employee.searchInEmployeesByStoreCode(storeCode);
                if (indexOfKey <= -1) {
                    System.out.println("***** Your considered employee didn't found *****");
                    continue;
                }
                System.out.println(allEmployees.get(indexOfKey).toString());
                System.out.print("-----> Is this your considered employee to remove? if yes enter 1, else enter 2 to exit: ");
                String  employeeValidationString = scanner.nextLine();
                Integer employeeValidation = Menu.preventClientWrongChoice(2, employeeValidationString);
                if (employeeValidation == null) {
                    continue;
                }
                switch (employeeValidation) {
                    case 1: {
                        allEmployees.remove(indexOfKey);
                        System.out.println("Employee " + storeCode + " removed successfully.");
                        break;
                    }
                    case 2: {
                        return;
                    }
                }
                isRemoveValid = true;
            }
        }
    }

    /* change Information of Employee */
    public void findEmployeeForChange() {

        boolean exitBool = false;
        while (!exitBool) {
            System.out.print("-----> With which item do you want to find an employee that you want to change his/her information? (Enter a number)\n-----> 1.Full Name          2.NationalID          3.Exit          Your Answer: ");
            String nameOrNationalIDString = scanner.nextLine();
            Integer nameOrNationalID =  Menu.preventClientWrongChoice(3, nameOrNationalIDString);
            if (nameOrNationalID == null) {
                continue;
            }
            switch (nameOrNationalID) {
                case 1: {
                    System.out.print("-----> Enter number of employee (Ex: Jone Jo): ");
                    String fullName = scanner.nextLine();
                    boolean isFullNameValid = false;
                    String firstName = null;
                    String lastName = null;
                    while (!isFullNameValid) {
                        int indexOfSpace = fullName.indexOf(' ');
                        if (indexOfSpace <= -1) {
                            System.out.print("***** Full name is invalid *****\n-----> Please enter first name and last name with an space between them: ");
                            fullName = scanner.nextLine();
                        }
                        else {
                            isFullNameValid = true;
                            firstName = fullName.substring(0 ,indexOfSpace);
                            lastName = fullName.substring(indexOfSpace + 1);
                        }
                    }
                    int indexOfKey = Employee.searchInEmployeesByFullName(firstName, lastName);
                    if (indexOfKey <= -1) {
                        System.out.println("***** Your considered employee didn't found. *****");
                        continue;
                    }
                    changeInformationOfEmployee(indexOfKey);
                    break;
                }

                case 2: {
                    System.out.print("Enter NationalID (Ex: 1234567890): ");
                    String nationalID = scanner.nextLine();
                    int indexOfKey = Employee.searchInEmployeesByNationalID(nationalID);
                    if (indexOfKey <= -1) {
                        System.out.println("***** Your considered employee didn't found. *****");
                        continue;
                    }
                    changeInformationOfEmployee(indexOfKey);
                    break;
                }

                case 3: {
                    exitBool = true;
                    break;
                }
            }
        }
    }

    public void changeInformationOfEmployee(int indexOfEmployee) {
        synchronized(Employee.getAllEmployees()) {
            boolean exitBool = false;
            while (!exitBool) {
                System.out.print("Which item do You Want To Change? (Enter a Number) " +
                        "\n1) First Name" +
                        "\n2) Last Name" +
                        "\n3) Age" +
                        "\n4) Gender" +
                        "\n5) NationalID" +
                        "\n6) Phone Number" +
                        "\n7) Store Code" +
                        "\n8) Salary" +
                        "\n9) Password" +
                        "\n10) Exit" +
                        "\n# Your Option : ");
                String optionAnswerString = scanner.nextLine();
                Integer optionAnswer = Menu.preventClientWrongChoice(10, optionAnswerString);
                if (optionAnswer == null) {
                    continue;
                }
                Employee employee = Employee.getAllEmployees().get(indexOfEmployee);
                switch (optionAnswer) {
                    case 1: {
                        employee.setFirstName();
                        break;
                    }
                    case 2: {
                        employee.setLastName();
                        break;
                    }
                    case 3: {
                        employee.setAge();
                        break;
                    }
                    case 4: {
                        employee.setGender();
                        break;
                    }
                    case 5: {
                        employee.setNationalID();
                        break;
                    }
                    case 6: {
                        employee.setPhoneNumber();
                        break;
                    }
                    case 7: {
                        employee.setStoreCode();
                        break;
                    }
                    case 8: {
                        employee.setSalary();
                        break;
                    }
                    case 9: {
                        employee.setPassword();
                        break;
                    }
                    case 10: {
                        exitBool = true;
                        break;
                    }
                }
            }
        }
    }

    /* Sort in employees by name */
    public void sortEmployeesByName() {
        Employee.sortEmployeesByName();
    }

    /* Sort in employees by storeCode */
    public void sortEmployeesBySortCode() {
        Employee.sortEmployeesByStoreCode();
    }

    /* Sort in employees by nationalID */
    public void sortEmployeesByNationalID() {
        Employee.sortEmployeesByNationalID();
    }

    /* search In Employees By Name */
    public int searchInEmployeesByName(String firstName, String lastName) {
        return Employee.searchInEmployeesByFullName(firstName, lastName);
    }

    /* search in Employees by Store Code */
    public int searchInEmployeesByStoreCode(String storeCode) {
        return Employee.searchInEmployeesByStoreCode(storeCode);
    }

    /* search in Employees by National ID */
    public int searchInEmployeesByNationalID(String nationalID) {
        return Employee.searchInEmployeesByNationalID(nationalID);
    }

    /* Printing result of search */
    public void resultOfEmployeeSearch(int indexOfKey) {
        if (indexOfKey <= -1) {
            System.out.println("***** There is no employee with this name! *****");
        }
        else {
            System.out.println(Employee.getAllEmployees().get(indexOfKey).toString());
        }
    }

    /* ***************************************** Customers Supervise ************************************************ */
    /* printing employees */
    public void printCustomer() {
        Customer.printCustomer();
    }

    /* add Customer */
    public Customer addCustomer() {
        return new Customer();
    }

    /* remove Customer */
    public void removeCustomer() {
        synchronized(Customer.getAllCustomers()) {
            boolean isRemoveValid = false;
            while (!isRemoveValid) {
                System.out.print("-----> Please enter store Code of Customer that you want to remove: ");
                String storeCode = scanner.nextLine();
                ArrayList<Customer> allCustomers = Customer.getAllCustomers();
                int indexOfKey = Customer.searchInCustomersByStoreCode(storeCode);
                if (indexOfKey <= -1) {
                    System.out.println("***** Your considered customer didn't found *****");
                    continue;
                }
                System.out.println(allCustomers.get(indexOfKey).toString());
                System.out.print("-----> Is this your considered customer to remove? if yes, enter 1. if you want to exit from removing item, enter 2, else enter 3 to enter store Code again: ");
                String employeeValidationString = scanner.nextLine();
                Integer employeeValidation = Menu.preventClientWrongChoice(3, employeeValidationString);
                if (employeeValidation == null) {
                    continue;
                }
                switch (employeeValidation) {
                    case 1: {
                        allCustomers.remove(indexOfKey);
                        System.out.println("***** Customer " + storeCode + " removed successfully *****");
                        break;
                    }
                    case 2: {
                        continue;
                    }
                    case 3: {
                        return;
                    }
                }
                isRemoveValid = true;

            }
        }
    }

    /* change Information of Customer */
    public void findCustomerForChange() {
        boolean exitBool = false;
        while (!exitBool) {
            System.out.print("-----> With which item do you want to find an customer that you want to change his/her information? (Enter a number)\n-----> 1.Full Name          2.NationalID          3.Exit          Your Answer: ");
            String nameOrNationalIDString = scanner.nextLine();
            Integer nameOrNationalID = Menu.preventClientWrongChoice(3, nameOrNationalIDString);
            if (nameOrNationalID == null) {
                continue;
            }
            switch (nameOrNationalID) {
                case 1: {
                    System.out.print("-----> Enter number of customer (Ex: Jone Jo): ");
                    String fullName = scanner.nextLine();
                    boolean isFullNameValid = false;
                    String firstName = null;
                    String lastName = null;
                    while (!isFullNameValid) {
                        int indexOfSpace = fullName.indexOf(' ');
                        if (indexOfSpace <= -1) {
                            System.out.print("***** Full name is invalid *****\n-----> Please enter first name and last name with an space between them: ");
                            fullName = scanner.nextLine();
                        }
                        else {
                            isFullNameValid = true;
                            firstName = fullName.substring(0 ,indexOfSpace);
                            lastName = fullName.substring(indexOfSpace + 1);
                        }
                    }
                    int indexOfKey = Customer.searchInCustomersByFullName(firstName, lastName);
                    if (indexOfKey <= -1) {
                        System.out.println("***** Your considered customer didn't found. *****");
                        continue;
                    }
                    changeInformationOfCustomers(indexOfKey);
                    break;
                }

                case 2: {
                    System.out.print("Enter NationalID (Ex: 1234567890): ");
                    String nationalID = scanner.nextLine();
                    int indexOfKey = Customer.searchInCustomersByNationalID(nationalID);
                    if (indexOfKey <= -1) {
                        System.out.println("***** Your considered customer didn't found. *****");
                        continue;
                    }
                    changeInformationOfCustomers(indexOfKey);
                    break;
                }

                case 3: {
                    exitBool = true;
                    break;
                }
            }
        }
    }

    public void changeInformationOfCustomers(int indexOfEmployee) {
        synchronized(Customer.getAllCustomers()) {
            boolean exitBool = false;
            while (!exitBool) {
                System.out.print("Which item do You Want To Change ? Please Enter a Number. " +
                        "\n1) First Name" +
                        "\n2) Last Name" +
                        "\n3) Age" +
                        "\n4) Gender" +
                        "\n5) NationalID" +
                        "\n6) Phone Number" +
                        "\n7) Store Code" +
                        "\n8) Password" +
                        "\n9) Exit" +
                        "\n# Your Option : ");
                String optionAnswerString = scanner.nextLine();
                Integer optionAnswer = Menu.preventClientWrongChoice(9, optionAnswerString);
                if (optionAnswer == null) {
                    continue;
                }
                Customer customer = Customer.getAllCustomers().get(indexOfEmployee);
                switch (optionAnswer) {
                    case 1: {
                        customer.setFirstName();
                        break;
                    }
                    case 2: {
                        customer.setLastName();
                        break;
                    }
                    case 3: {
                        customer.setAge();
                        break;
                    }
                    case 4: {
                        customer.setGender();
                        break;
                    }
                    case 5: {
                        customer.setNationalID();
                        break;
                    }
                    case 6: {
                        customer.setPhoneNumber();
                        break;
                    }
                    case 7: {
                        customer.setStoreCode();
                        break;
                    }
                    case 8: {
                        customer.setPassword();
                        break;
                    }
                    case 9: {
                        exitBool = true;
                        break;
                    }
                }
            }
        }
    }

    /* sort customers by name */
    public void sortCustomersByName() {
        Customer.sortCustomersByName();
    }

    /* sort customers by storeCode */
    public void sortCustomersByStoreCode() {
        Customer.sortCustomersByStoreCode();
    }

    /* sort customers by storeCode */
    public void sortCustomersByNationalID() {
        Customer.sortCustomersByNationalID();
    }

    /* search in customers by name */
    public int searchInCustomersByName(String firstName, String lastName) {
        return Customer.searchInCustomersByFullName(firstName, lastName);
    }

    /* search in customers by storeCode */
    public int searchInCustomersByStoreCode(String storeCode) {
        return Customer.searchInCustomersByStoreCode(storeCode);
    }

    /* search in customers by NationalID */
    public int searchInCustomersByNationalID(String nationalID) {
        return Customer.searchInCustomersByNationalID(nationalID);
    }

    /* Printing result of search */
    public void resultOfCustomerSearch(int indexOfKey) {
        if (indexOfKey <= -1) {
            System.out.println("***** There is no customer with this name! *****");
        }
        else {
            System.out.println(Customer.getAllCustomers().get(indexOfKey).toString());
        }
    }

    /* Printing Status of a customer */
    public void customerStatus (String storeCode) {
        int indexOfKey = searchInCustomersByStoreCode(storeCode);
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

    /* add product */
    public void addProduct() {
        new Product();
    }

    /* Removing Product */
    public static void removeProduct () {
        synchronized(Product.getAllProducts()) {
            Scanner scanner = new Scanner(System.in);
            boolean isRemoveValid = false;
            while (!isRemoveValid) {
                System.out.print("-----> Please enter product code of product: ");
                String productCode = scanner.nextLine();
                ArrayList<Product> availableProducts = Product.getAvailableProducts();
                int indexOfKey = Product.searchInProductByProductCode(availableProducts, productCode);
                if (indexOfKey <= -1) {
                    System.out.println("***** Your considered product didn't found *****");
                    continue;
                }
                System.out.println(availableProducts.get(indexOfKey).toString());
                System.out.print("-----> Is this your considered product to remove? if yes enter 1 if you want to exit from removing item, enter 2 else enter 3 to enter product code again: ");
                String employeeValidationString = scanner.nextLine();
                Integer employeeValidation = Menu.preventClientWrongChoice(3, employeeValidationString);
                if (employeeValidation == null) {
                    continue;
                }
                switch (employeeValidation) {
                    case 1: {
                        boolean isNumberOfRemoveValid = false;
                        Integer numberOfRemove = 0;
                        int numberOfAvailableProduct = availableProducts.get(indexOfKey).getNumberOfAvailableProduct();
                        while (!isNumberOfRemoveValid) {
                            System.out.println("There are " + numberOfAvailableProduct + " of this product.");
                            System.out.print("How many of this product do you want to remove? ");
                            String numberOfRemoveString = scanner.nextLine();
                            numberOfRemove = Menu.preventClientWrongChoice(numberOfAvailableProduct, numberOfRemoveString);
                            if (numberOfRemove == null) {
                                return;
                            }
                            isNumberOfRemoveValid = true;
                        }
                        availableProducts.get(indexOfKey).setNumberOfAvailableProduct(availableProducts.get(indexOfKey).getNumberOfAvailableProduct() - numberOfRemove); /* Remove Done */
                        System.out.println(  numberOfRemove + " of product " + productCode + " removed successfully.");
                        isRemoveValid = true;
                        break;
                    }
                    case 2: {
                        return;
                    }
                    case 3: {
                        continue;
                    }
                }
            }
        }
    }

    /* change Information of Product */
    public void findProductForChange() {
        boolean exitBool = false;
        while (!exitBool) {
            System.out.print("-----> With which item do you want to find a product that you want to change its information? (Enter a number)\n-----> 1.Name          2.Product Code          3.Exit          Your Answer: ");
            String nameOrProductCodeString = scanner.nextLine();
            Integer nameOrProductCode = Menu.preventClientWrongChoice(3, nameOrProductCodeString);
            if (nameOrProductCode == null) {
                continue;
            }
            switch (nameOrProductCode) {
                case 1: {
                    System.out.print("-----> Enter number of product (Ex: shoe125): ");
                    String name = scanner.nextLine();
                    int indexOfKey = Product.searchInProductsByName(Product.getAllProducts() ,name);
                    if (indexOfKey <= -1) {
                        System.out.print("***** Your considered product didn't found. *****");
                        continue;
                    }
                    changeInformationOfProducts(indexOfKey);
                    break;
                }

                case 2: {
                    System.out.print("Enter Product Code (Ex: 1234567890): ");
                    String productCode = scanner.nextLine();
                    int indexOfKey = Product.searchInProductByProductCode(Product.getAllProducts(), productCode);
                    if (indexOfKey <= -1) {
                        System.out.print("***** Your considered product didn't found. *****");
                        continue;
                    }
                    changeInformationOfProducts(indexOfKey);
                    break;
                }
                case 3: {
                    exitBool = true;
                    break;
                }
            }
        }
    }

    public void changeInformationOfProducts(int indexOfProduct) {
        synchronized(Product.getAllProducts()) {
            boolean exitBool = false;
            while (!exitBool) {
                System.out.print("Which item do You want To Change ? Please Enter a Number. " +
                        "\n1) Name" +
                        "\n2) Selling Price" +
                        "\n3) Buying Price" +
                        "\n4) Number of Available Product" +
                        "\n5) Product Code" +
                        "\n6) Exit" +
                        "\n# Your Option : ");
                String optionAnswerString = scanner.nextLine();
                Integer optionAnswer = Menu.preventClientWrongChoice(6, optionAnswerString);
                if (optionAnswer == null) {
                    continue;
                }
                Product product = Product.getAllProducts().get(indexOfProduct);
                switch (optionAnswer) {
                    case 1: {
                        product.setName();
                        break;
                    }
                    case 2: {
                        product.setSellingPrice();
                        break;
                    }
                    case 3: {
                        product.setBuyingPrice();
                        break;
                    }
                    case 4: {
                        product.setValidNumberOfAvailableProduct();
                        break;
                    }
                    case 5: {
                        product.setProductCode();
                        break;
                    }
                    case 6: {
                        exitBool = true;
                        break;
                    }
                }
            }
        }
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

    public static void getTotalProfit () {
        System.out.print("-----> Total Profit: " + Product.getAllProfit());
    }

    @Override
    public String toString() {
        return "-----> Full Name: " + this.getFirstName() + " " + this.getLastName() + "     Gender: " + this.getGender() + "     National ID: " + this.getNationalID() + "     Phone Number: " + this.getPhoneNumber() + "     Age: " + this.getAge() + "     Store Code: " + this.storeCode + "     Password: " + password;
    }
}
