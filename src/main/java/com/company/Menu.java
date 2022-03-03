package com.company;

import java.util.Scanner;

public class Menu {

    /**
     * menu() is a method that program start with that (after reading Json files).
     * in this method client chose to log in or sign up.
     * in case of sign up, information of customer, employee or manager (if there is no manager) will take.
     * in case of login store code and password will take to validation.
     * all client input will be validated
     * in all menu Exit option is considered.
     * */

    public static void menu() {
        Scanner scanner = new Scanner(System.in);

        boolean exitBool = false;
        while (!exitBool) {
            System.out.print("-----> Chose one of these option (enter a number): 1.Sign up  2.Log in  3.Exit  Your Option: ");
            String signInOrLogInString = scanner.nextLine();
            Integer signInOrLogIn = Menu.preventClientWrongChoice(3, signInOrLogInString);
            if (signInOrLogIn == null) {
                continue;
            }
            switch (signInOrLogIn) {
                case 1: {
                    signUpCase();
                    break;
                }
                case 2: {
                    logInCase();
                    break;
                }
                case 3: {
                    exitBool = true;
                    break;
                }
            }
        }
    }

    public static void signUpCase() {

        Scanner scanner = new Scanner(System.in);

        boolean exitMenuBool = false;
        while (!exitMenuBool) {
            System.out.print("-----> Sign up as(enter a number): 1.Manager  2.Employee  3.Customer  4.Exit  Your Option: ");
            String roleString = scanner.nextLine();
            Integer role = Menu.preventClientWrongChoice(4, roleString);
            if (role == null) {
                continue;
            }
            switch (role) {
                case 1: {
                    if (Manager.getManagers().size() == 1) {
                        System.out.println("***** This store already has a manager *****");
                        break;
                    }
                    Manager manager = new Manager();
                    System.out.println("***** You signed up as manager. *****");
                    Menu.signedUpManagerSupervise(manager);
                    break;
                }
                case 2: {
                    Employee employee = new Employee();
                    System.out.println("***** You signed up as employee. *****");
                    Menu.signedUpEmployeeSupervise(employee);
                    break;
                }
                case 3: {
                    Customer customer = new Customer();
                    System.out.println("***** You signed up as customer. *****");
                    Menu.signedUpCustomerSupervise(customer);
                    break;
                }
                case 4: {
                    exitMenuBool = true;
                    break;
                }
            }
        }
    }

     public static void logInCase() {

        Scanner scanner = new Scanner(System.in);

         boolean exitMenuBool = false;
         while (!exitMenuBool) {
             System.out.print("-----> Log in as(enter a number):  1.Manager  2.Employee  3.Customer  4.Exit: ");
             String roleString = scanner.nextLine();
             Integer role = Menu.preventClientWrongChoice(4, roleString);
             if (role == null) {
                 continue;
             }
             switch (role) {
                 case 1: {
                     Manager manager;
                     String storeCode = Menu.getValidStoreCodeForLogIn();
                     System.out.print("-----> Please enter your password: ");
                     String password = scanner.nextLine();
                     try {
                         String originalStoreCode = Manager.getOneManager().getStoreCode();
                         String originalPassword = Manager.getOneManager().getPassword();
                         if (originalStoreCode.equals(storeCode) && (originalPassword.equals(password))) {
                             manager = Manager.getOneManager();
                             System.out.println("***** You log in successfully as manager *****");
                             Menu.signedUpManagerSupervise(manager);
                         }
                         else {
                             System.out.println("***** Your store code or password is wrong *****");
                             break;
                         }
                     }
                     catch (NullPointerException e) {
                         System.out.println("***** Your store code or password is wrong *****");
                         break;
                     }
                     break;
                 }
                 case 2: {
                     Employee employee;
                     String storeCode = Menu.getValidStoreCodeForLogIn();
                     System.out.print("-----> Please enter your password: ");
                     String password = scanner.nextLine();

                     int indexOfKey = Employee.searchInEmployeesByStoreCode(storeCode);
                     if (indexOfKey <= -1) {
                         System.out.println("***** Your store code or password is wrong *****");
                         break;
                     }

                     else {
                         if (Employee.getAllEmployees().get(indexOfKey).getPassword().equals(password)) {
                             employee = Employee.getAllEmployees().get(indexOfKey);
                             System.out.println("***** You log in successfully as employee *****");
                             Menu.signedUpEmployeeSupervise(employee);
                         }
                         else {
                             System.out.println("***** Your store code or password is wrong *****");
                             break;
                         }
                     }
                     break;
                 }
                 case 3: {
                     Customer customer;
                     String storeCode = Menu.getValidStoreCodeForLogIn();
                     System.out.print("-----> Please enter your password: ");
                     String password = scanner.nextLine();

                     int indexOfKey = Customer.searchInCustomersByStoreCode(storeCode);
                     if (indexOfKey <= -1) {
                         System.out.println("***** Your store code or password is wrong *****");
                         break;
                     }
                     else {
                         if (Customer.getAllCustomers().get(indexOfKey).getPassword().equals(password)) {
                             customer = Customer.getAllCustomers().get(indexOfKey);
                             System.out.println("***** You log in successfully as customer *****");
                             Menu.signedUpCustomerSupervise(customer);
                         }
                         else {
                             System.out.println("***** Your store code or password is wrong *****");
                             break;
                         }
                     }
                     break;
                 }
                 case 4: {
                     exitMenuBool = true;
                     break;
                 }
             }
         }
     }

    /**
     * these following three method are for using log in or signed up manager, employee or manager.
     * these methods contain supervises that each client (manager, employee and customer) should have.
     * */

    public static void signedUpManagerSupervise(Manager manager) {

        Scanner scanner = new Scanner(System.in);

        boolean exitBool = false;
        while (!exitBool) {
            System.out.print("-----> Which one do you want to do?  1.Employee Supervise  2.Customer Supervise  3.Product Supervise  4.Exit: ");
            String managerSuperviseString = scanner.nextLine();
            Integer managerSupervise = preventClientWrongChoice(4, managerSuperviseString);
            if (managerSupervise == null) {
                continue;
            }
            switch (managerSupervise) {
                case 1: {
                    boolean exitMenuBool = false;
                    while (!exitMenuBool) {
                        System.out.print("-----> Which supervise do you want to do? " +
                                "\n1. Add Employee" +
                                "\n2. Change information of employee" +
                                "\n3. Sort employees by full name and print" +
                                "\n4. Sort employees by store code and print" +
                                "\n5. Sort employees by national ID and print" +
                                "\n6. Search in employees by full name" +
                                "\n7. Search in employees by store code" +
                                "\n8. Search in employees by national ID" +
                                "\n9. Remove Employee" +
                                "\n10. Exit" +
                                "\n-----> Your option: ");
                        String superviseOnString = scanner.nextLine();
                        Integer superviseOn = preventClientWrongChoice(10, superviseOnString);
                        if (superviseOn == null) {
                            continue;
                        }
                        switch (superviseOn) {
                            case 1: {
                                manager.addEmployee();
                                break;
                            }
                            case 2: {
                                manager.findEmployeeForChange();
                                break;
                            }
                            case 3: {
                                manager.sortEmployeesByName();
                                manager.printEmployees();
                                break;
                            }
                            case 4: {
                                manager.sortEmployeesBySortCode();
                                manager.printEmployees();
                                break;
                            }
                            case 5: {
                                manager.sortEmployeesByNationalID();
                                manager.printEmployees();
                                break;
                            }
                            case 6: {
                                System.out.print("Please enter first name: ");
                                String firstName = scanner.nextLine();
                                System.out.print("Please enter last name: ");
                                String lastName = scanner.nextLine();
                                int indexOfKey = manager.searchInEmployeesByName(firstName, lastName);
                                manager.resultOfEmployeeSearch(indexOfKey);
                                break;
                            }
                            case 7: {
                                System.out.print("Please enter store code: ");
                                String storeCode = scanner.nextLine();
                                int indexOfKey = manager.searchInEmployeesByStoreCode(storeCode);
                                manager.resultOfEmployeeSearch(indexOfKey);
                                break;
                            }
                            case 8: {
                                System.out.print("Please enter national ID: ");
                                String nationalID = scanner.nextLine();
                                int indexOfKey = manager.searchInEmployeesByNationalID(nationalID);
                                manager.resultOfEmployeeSearch(indexOfKey);
                                break;
                            }
                            case 9: {
                                manager.removeEmployee();
                                break;
                            }
                            case 10: {
                                exitMenuBool = true;
                                break;
                            }
                        }
                    }
                    break;
                }
                case 2: {
                    boolean exitMenuBool = false;
                    while (!exitMenuBool) {
                        System.out.print("-----> Which supervise do you want to do? " +
                                "\n1. Add customer" +
                                "\n2. Change information of customer" +
                                "\n3. Sort customer by full name and print" +
                                "\n4. Sort customer by store code and print" +
                                "\n5. Sort customer by national ID and print " +
                                "\n6. Search in customer by full name" +
                                "\n7. Search in customer by store code" +
                                "\n8. Search in customer by national ID" +
                                "\n9. Print customer status" +
                                "\n10. Remove Employee" +
                                "\n11. Exit" +
                                "\n-----> Your option: ");
                        String superviseOnString = scanner.nextLine();
                        Integer superviseOn = preventClientWrongChoice(11, superviseOnString);
                        if (superviseOn == null) {
                            continue;
                        }
                        switch (superviseOn) {
                            case 1: {
                                manager.addCustomer();
                                break;
                            }
                            case 2: {
                                manager.findCustomerForChange();
                                break;
                            }
                            case 3: {
                                manager.sortCustomersByName();
                                manager.printCustomer();
                                break;
                            }
                            case 4: {
                                manager.sortCustomersByStoreCode();
                                manager.printCustomer();
                                break;
                            }
                            case 5: {
                                manager.sortCustomersByNationalID();
                                manager.printCustomer();
                                break;
                            }
                            case 6: {
                                System.out.print("Please enter first name: ");
                                String firstName = scanner.nextLine();
                                System.out.print("Please enter last name: ");
                                String lastName = scanner.nextLine();
                                int indexOfKey = manager.searchInCustomersByName(firstName, lastName);
                                manager.resultOfCustomerSearch(indexOfKey);
                                break;
                            }
                            case 7: {
                                System.out.print("Please enter store code: ");
                                String storeCode = scanner.nextLine();
                                int indexOfKey = manager.searchInCustomersByStoreCode(storeCode);
                                manager.resultOfCustomerSearch(indexOfKey);
                                break;
                            }
                            case 8: {
                                System.out.print("Please enter national ID: ");
                                String nationalID = scanner.nextLine();
                                int indexOfKey = manager.searchInCustomersByNationalID(nationalID);
                                manager.resultOfEmployeeSearch(indexOfKey);
                                break;
                            }
                            case 9: {
                                System.out.print("-----> Please enter customer store code: ");
                                String storeCode = scanner.nextLine();
                                manager.customerStatus(storeCode);
                                break;
                            }
                            case 10: {
                                manager.removeCustomer();
                                break;
                            }
                            case 11: {
                                exitMenuBool = true;
                                break;
                            }
                        }
                    }
                    break;
                }
                case 3: {
                    boolean exitMenuBool = false;
                    while (!exitMenuBool) {
                        System.out.print("-----> Which supervise do you want to do? " +
                                "\n1. Add product" +
                                "\n2. Change information of product" +
                                "\n3. Sort product by name and print" +
                                "\n4. Sort product by product code and print" +
                                "\n5. Search in product by name" +
                                "\n6. Search in product by product code" +
                                "\n7. Remove product" +
                                "\n8. Exit" +
                                "\n-----> Your option: ");
                        String superviseOnString = scanner.nextLine();
                        Integer superviseOn = preventClientWrongChoice(8, superviseOnString);
                        if (superviseOn == null) {
                            continue;
                        }
                        switch (superviseOn) {
                            case 1: {
                                manager.addProduct();
                                break;
                            }
                            case 2: {
                                manager.findProductForChange();
                                break;
                            }
                            case 3: {
                                boolean exitSubMenuBool = false;
                                while (!exitSubMenuBool) {
                                    System.out.print("-----> In which products do you want to sort and print?  1.All Products  2.Available Product  3.Sold Product  4.Exit: ");
                                    String allSoldOrAvailableString = scanner.nextLine();
                                    Integer allSoldOrAvailable = preventClientWrongChoice(4, allSoldOrAvailableString);
                                    if (allSoldOrAvailable == null) {
                                        continue;
                                    }
                                    switch (allSoldOrAvailable) {
                                        case 1: {
                                            System.out.println("-----> Sorted All Product: ");
                                            manager.sortProductsByName(Product.getAllProducts());
                                            manager.printProducts(Product.getAllProducts());
                                            break;
                                        }
                                        case 2: {
                                            System.out.println("-----> Sorted Available Product: ");
                                            manager.sortProductsByName(Product.getAvailableProducts());
                                            manager.printProducts(Product.getAvailableProducts());
                                            break;
                                        }
                                        case 3: {
                                            System.out.println("-----> Sorted Sold Product: ");
                                            manager.sortProductsByName(Product.getSoldProducts());
                                            manager.printProducts(Product.getSoldProducts());
                                            break;
                                        }
                                        case 4: {
                                            exitSubMenuBool = true;
                                            break;
                                        }
                                    }
                                }
                                break;
                            }
                            case 4: {
                                boolean exitSubMenuBool = false;
                                while (!exitSubMenuBool) {
                                    System.out.print("-----> In which products do you want to sort and print?  1.All Products  2.Available Product  3.Sold Product  4.Exit: ");
                                    String allSoldOrAvailableString = scanner.nextLine();
                                    Integer allSoldOrAvailable = preventClientWrongChoice(4, allSoldOrAvailableString);
                                    if (allSoldOrAvailable == null) {
                                        continue;
                                    }
                                    switch (allSoldOrAvailable) {
                                        case 1: {
                                            System.out.println("-----> Sorted All Product: ");
                                            manager.sortProductsByProductCode(Product.getAllProducts());
                                            manager.printProducts(Product.getAllProducts());
                                            break;
                                        }
                                        case 2: {
                                            System.out.println("-----> Sorted Available Product: ");
                                            manager.sortProductsByProductCode(Product.getAvailableProducts());
                                            manager.printProducts(Product.getAvailableProducts());
                                            break;
                                        }
                                        case 3: {
                                            System.out.println("-----> Sorted Sold Product: ");
                                            manager.sortProductsByProductCode(Product.getSoldProducts());
                                            manager.printProducts(Product.getSoldProducts());
                                            break;
                                        }
                                        case 4: {
                                            exitSubMenuBool = true;
                                            break;
                                        }
                                    }
                                }
                                break;
                            }
                            case 5: {
                                System.out.print("Please enter name: ");
                                String name = scanner.nextLine();
                                int indexOfKey = manager.searchInProductsWithName(Product.getAllProducts(), name);
                                manager.resultOfProductSearch(Product.getAllProducts(), indexOfKey, "product");
                                break;
                            }
                            case 6: {
                                System.out.print("Please enter product code: ");
                                String productCode = scanner.nextLine();
                                int indexOfKey = manager.searchInProductsWithProductCode(Product.getAllProducts(), productCode);
                                manager.resultOfProductSearch(Product.getAllProducts(), indexOfKey, "product");
                                break;
                            }
                            case 7: {
                                Manager.removeProduct();
                                break;
                            }
                            case 8: {
                                exitMenuBool = true;
                                break;
                            }
                        }
                    }
                    break;
                }
                case 4: {
                    exitBool = true;
                    break;
                }
            }
        }
    }

    public static void signedUpEmployeeSupervise(Employee employee) {

        Scanner scanner = new Scanner(System.in);

        boolean exitBool = false;
        while (!exitBool) {
            System.out.print("-----> Which one do you want to do?  1.Customer Supervise  2.Product Supervise  3.Exit: ");
            String employeeSuperviseString = scanner.nextLine();
            Integer employeeSupervise = preventClientWrongChoice(3, employeeSuperviseString);
            if (employeeSupervise == null) {
                continue;
            }
            switch (employeeSupervise) {
                case 1: {
                    boolean exitMenuBool = false;
                    while (!exitMenuBool) {
                        System.out.print("-----> Which supervise do you want to do? " +
                                "\n1. Sort customer by full name and print" +
                                "\n2. Sort customer by store code and print" +
                                "\n3. Sort customer by national ID and print" +
                                "\n4. Search in customer by full name" +
                                "\n5. Search in customer by store code" +
                                "\n6. Search in customer by national ID" +
                                "\n7. See customer status" +
                                "\n8. Exit" +
                                "\n-----> Your option: ");
                        String superviseOnString = scanner.nextLine();
                        Integer superviseOn = preventClientWrongChoice(8, superviseOnString);
                        if (superviseOn == null) {
                            continue;
                        }
                        switch (superviseOn) {
                            case 1: {
                                employee.sortCustomersByName();
                                employee.printCustomers();
                                break;
                            }
                            case 2: {
                                employee.sortCustomersByStoreCode();
                                employee.printCustomers();
                                break;
                            }
                            case 3: {
                                employee.sortCustomersByNationalID();
                                employee.printCustomers();
                                break;
                            }
                            case 4: {
                                System.out.print("-----> Please enter first name: ");
                                String firstName = scanner.nextLine();
                                System.out.print("Please enter last name: ");
                                String lastName = scanner.nextLine();
                                int indexOfKey = Customer.searchInCustomersByFullName(firstName, lastName);
                                employee.resultOfCustomersSearch(indexOfKey);
                                break;
                            }
                            case 5: {
                                System.out.print("-----> Please enter store code: ");
                                String storeCode = scanner.nextLine();
                                int indexOfKey = Customer.searchInCustomersByStoreCode(storeCode);
                                employee.resultOfCustomersSearch(indexOfKey);
                                break;
                            }
                            case 6: {
                                System.out.print("-----> Please enter national ID: ");
                                String nationalID = scanner.nextLine();
                                int indexOfKey = Customer.searchInCustomersByNationalID(nationalID);
                                employee.resultOfCustomersSearch(indexOfKey);
                                break;
                            }
                            case 7: {
                                System.out.print("-----> Please enter customer store code: ");
                                String storeCode = scanner.nextLine();
                                employee.customerStatus(storeCode);
                                break;
                            }
                            case 8: {
                                exitMenuBool = true;
                                break;
                            }
                        }
                    }
                    break;
                }
                case 2: {
                    boolean exitMenuBool = false;
                    while (!exitMenuBool) {
                        System.out.print("-----> Which supervise do you want to do? " +
                                "\n1. Add product" +
                                "\n2. Sort product by name and print" +
                                "\n3. Sort product by product code and print" +
                                "\n4. Search in product by name" +
                                "\n5. Search in product by product code" +
                                "\n6. Remove Product" +
                                "\n7. Exit" +
                                "\n-----> Your option: ");
                        String superviseOnString = scanner.nextLine();
                        Integer superviseOn = preventClientWrongChoice(7, superviseOnString);
                        if (superviseOn == null) {
                            continue;
                        }
                        switch (superviseOn) {
                            case 1: {
                                employee.addProduct();
                                break;
                            }
                            case 2: {
                                boolean exitSubMenuBool = false;
                                while (!exitSubMenuBool) {
                                    System.out.print("-----> In which products do you want to sort and print?  1.All Products  2.Available Product  3.Sold Product  4.Exit: ");
                                    String allSoldOrAvailableString = scanner.nextLine();
                                    Integer allSoldOrAvailable = preventClientWrongChoice(4, allSoldOrAvailableString);
                                    if (allSoldOrAvailable == null) {
                                        continue;
                                    }
                                    switch (allSoldOrAvailable) {
                                        case 1: {
                                            System.out.println("-----> Sorted All Product: ");
                                            employee.sortProductsByName(Product.getAllProducts());
                                            employee.printProducts(Product.getAllProducts());
                                            break;
                                        }
                                        case 2: {
                                            System.out.println("-----> Sorted Available Product: ");
                                            employee.sortProductsByName(Product.getAvailableProducts());
                                            employee.printProducts(Product.getAvailableProducts());
                                            break;
                                        }
                                        case 3: {
                                            System.out.println("-----> Sorted Sold Product: ");
                                            employee.sortProductsByName(Product.getSoldProducts());
                                            employee.printProducts(Product.getSoldProducts());
                                            break;
                                        }
                                        case 4: {
                                            exitSubMenuBool = true;
                                            break;
                                        }
                                    }
                                }
                                break;
                            }
                            case 3: {
                                boolean exitSubMenuBool = false;
                                while (!exitSubMenuBool) {
                                    System.out.print("-----> In which products do you want to sort and print?  1.All Products  2.Available Product  3.Sold Product  4.Exit: ");
                                    String allSoldOrAvailableString = scanner.nextLine();
                                    Integer allSoldOrAvailable = preventClientWrongChoice(4, allSoldOrAvailableString);
                                    if (allSoldOrAvailable == null) {
                                        continue;
                                    }
                                    switch (allSoldOrAvailable) {
                                        case 1: {
                                            System.out.println("-----> Sorted All Product: ");
                                            employee.sortProductsByProductCode(Product.getAllProducts());
                                            employee.printProducts(Product.getAllProducts());
                                            break;
                                        }
                                        case 2: {
                                            System.out.println("-----> Sorted Available Product: ");
                                            employee.sortProductsByProductCode(Product.getAvailableProducts());
                                            employee.printProducts(Product.getAvailableProducts());
                                            break;
                                        }
                                        case 3: {
                                            System.out.println("-----> Sorted Sold Product: ");
                                            employee.sortProductsByProductCode(Product.getSoldProducts());
                                            employee.printProducts(Product.getSoldProducts());
                                            break;
                                        }
                                        case 4: {
                                            exitSubMenuBool = true;
                                            break;
                                        }
                                    }
                                }

                                break;
                            }
                            case 4: {
                                System.out.print("Please enter name: ");
                                String name = scanner.nextLine();
                                int indexOfKey = employee.searchInProductsWithName(Product.getAllProducts(), name);
                                employee.resultOfProductSearch(Product.getAllProducts(), indexOfKey, "product");
                                break;
                            }
                            case 5: {
                                System.out.print("Please enter product code: ");
                                String productCode = scanner.nextLine();
                                int indexOfKey = employee.searchInProductsWithProductCode(Product.getAllProducts(), productCode);
                                employee.resultOfProductSearch(Product.getAllProducts(), indexOfKey, "product");
                                break;
                            }
                            case 6: {
                                employee.removeProduct();
                                break;
                            }
                            case 7: {
                                exitMenuBool = true;
                                break;
                            }
                        }
                    }
                    break;
                }
                case 3: {
                    exitBool = true;
                    break;
                }
            }
        }
    }

    public static void signedUpCustomerSupervise(Customer customer) {

        Scanner scanner = new Scanner(System.in);

        boolean exitBool = false;
        while (!exitBool) {
            System.out.print("-----> Which one do you want to do?  1.Product Supervise  2.Exit: ");
            String customerSuperviseString = scanner.nextLine();
            Integer customerSupervise = preventClientWrongChoice(2, customerSuperviseString);
            if (customerSupervise == null) {
                continue;
            }
            switch (customerSupervise) {
                case 1: {
                    boolean exitMenuBool = false;
                    while (!exitMenuBool) {
                        System.out.print("-----> Which one do you want ot do?" +
                                "\n1. Sort product by name and print" +
                                "\n2. Sort product by product code and print" +
                                "\n3. Search in product by name" +
                                "\n4. Search in product by product code" +
                                "\n5. New Shopping List" +
                                "\n6. See History of Purchases" +
                                "\n7. Exit" +
                                "\n-----> Your option: ");
                        String superviseOnString = scanner.nextLine();
                        Integer superviseOn = preventClientWrongChoice(7, superviseOnString);
                        if (superviseOn == null) {
                            continue;
                        }
                        switch (superviseOn) {
                            case 1: {
                                System.out.println("-----> Sorted Available Product: ");
                                customer.sortProductsByName(Product.getAvailableProducts());
                                customer.printProductsForCustomer(Product.getAvailableProducts());
                                break;
                            }
                            case 2: {
                                System.out.println("-----> Sorted Available Product: ");
                                customer.sortProductsByProductCode(Product.getAvailableProducts());
                                customer.printProductsForCustomer(Product.getAvailableProducts());
                                break;
                            }
                            case 3: {
                                System.out.print("Please enter name: ");
                                String name = scanner.nextLine();
                                int indexOfKey = customer.searchInProductsWithName(Product.getAllProducts(), name);
                                customer.resultOfProductSearchForCustomer(Product.getAllProducts(), indexOfKey, "product");
                                break;
                            }
                            case 4: {
                                System.out.print("Please enter product code: ");
                                String productCode = scanner.nextLine();
                                int indexOfKey = customer.searchInProductsWithProductCode(Product.getAllProducts(), productCode);
                                customer.resultOfProductSearchForCustomer(Product.getAllProducts(), indexOfKey, "product");
                                break;
                            }
                            case 5: {
                                customer.newShoppingList();
                                break;
                            }
                            case 6: {
                                customer.printingHistoryOfPurchase();
                                break;
                            }
                            case 7: {
                                exitMenuBool = true;
                                break;
                            }
                        }
                    }

                    break;
                }
                case 2: {
                    exitBool = true;
                    break;
                }
            }
        }
    }

    /**
     * in all menu option, we have to get a number from client for client option.
     * with following method we considered client wrong answer and handled them.
     * */

    public static Integer preventClientWrongChoice(int indexOfLastOption, String indexOfClientOptionString) {
        int indexOfClientOption;
        try {
            indexOfClientOption = Integer.parseInt(indexOfClientOptionString);
        }
        catch (NumberFormatException e) {
            System.out.println("***** Your option is invalid *****");
            return null;
        }
        if (indexOfClientOption >= indexOfLastOption + 1 || indexOfClientOption <= 0) {
            System.out.println("***** Your option is invalid *****");
            return null;
        }
        return indexOfClientOption;
    }

    public static String getValidStoreCodeForLogIn(){
        Scanner scanner = new Scanner(System.in);
        String storeCodeString = null;
        boolean isStoreCodeValid = false;
        while (!isStoreCodeValid) {
            System.out.print("-----> Please enter your store code: ");
            storeCodeString = scanner.nextLine();
            try {
                Integer.parseInt(storeCodeString);
            }
            catch (NumberFormatException e) {
                System.out.println("***** You Entered character(s) that aren't number *****");
                continue;
            }
            isStoreCodeValid = true;
        }
        return storeCodeString;
    }
}