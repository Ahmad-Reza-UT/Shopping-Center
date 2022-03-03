package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ReadAndWrite {

    /**
     * this class is for reading and writing on .json file.
     * for each manager, employee, customer and product, there are three methods: read(reading from .json), write(writing on .json) and parse(part of reading)*/

    /* read(); method calls all read* methods */
    public static void read() {
        ReadAndWrite.readAllProducts("allProducts.json", 2);
        ReadAndWrite.readAllProducts("availableProducts.json", 1);
        ReadAndWrite.readAllProducts("soldProducts.json", 3);
        ReadAndWrite.readCustomers();
        ReadAndWrite.readEmployees();
        ReadAndWrite.readManager();
    }

    /* write(); method call all write* methods */
    public static void write() {
        ReadAndWrite.writeProducts(Product.getAllProducts(), "allProducts.json");
        ReadAndWrite.writeProducts(Product.getAvailableProducts(), "availableProducts.json");
        ReadAndWrite.writeProducts(Product.getSoldProducts(), "soldProducts.json");
        ReadAndWrite.writeCustomers();
        ReadAndWrite.writeEmployees();
        ReadAndWrite.writeManager();
    }


    /* Read and Write for Manager */
    @SuppressWarnings("Unchecked")
    public static void parseManagerObject(JSONObject manager) {
        JSONObject managerObject = (JSONObject) manager.get("manager");

        String firstName = (String) managerObject.get("firstName");
        String lastName = (String) managerObject.get("lastName");
        String gender = (String) managerObject.get("gender");
        String nationalID = (String) managerObject.get("nationalID");
        String phoneNumber = (String) managerObject.get("phoneNumber");
        Long age = (Long) managerObject.get("age");
        String storeCode = (String) managerObject.get("storeCode");
        String password = (String) managerObject.get("password");

        new Manager(firstName, lastName, gender, nationalID, phoneNumber, age, storeCode, password);
    }

    @SuppressWarnings("Unchecked")
    public static void readManager() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("manager.json")) {
            Object object = jsonParser.parse(reader);

            JSONArray managerList = (JSONArray) object;
            System.out.println(managerList);

            managerList.forEach(emp -> ReadAndWrite.parseManagerObject((JSONObject) emp));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void writeManager() {
        JSONArray managerList = new JSONArray();
        for (int i = 0; i < Manager.getManagers().size(); i++) {
            Manager manager = Manager.getManagers().get(i);
            JSONObject managerDetails = new JSONObject();
            managerDetails.put("firstName", manager.getFirstName());
            managerDetails.put("lastName", manager.getLastName());
            managerDetails.put("gender", manager.getGender());
            managerDetails.put("nationalID", manager.getNationalID());
            managerDetails.put("phoneNumber", manager.getPhoneNumber());
            managerDetails.put("age", manager.getAge());
            managerDetails.put("storeCode", manager.getStoreCode());
            managerDetails.put("password", manager.getPassword());

            JSONObject managerObject = new JSONObject();
            managerObject.put("manager", managerDetails);
            managerList.add(managerObject);
        }
        try(FileWriter file = new FileWriter("manager.json")) {
            file.write(managerList.toJSONString());
            file.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /* Read and Write for Employees */
    @SuppressWarnings("Unchecked")
    public static void parseEmployeeObject(JSONObject employee) {
        JSONObject employeeObject = (JSONObject) employee.get("employee");

        String firstName = (String) employeeObject.get("firstName");
        String lastName = (String) employeeObject.get("lastName");
        String gender = (String) employeeObject.get("gender");
        String nationalID = (String) employeeObject.get("nationalID");
        String phoneNumber = (String) employeeObject.get("phoneNumber");
        Long age = (Long) employeeObject.get("age");
        String storeCode = (String) employeeObject.get("storeCode");
        String password = (String) employeeObject.get("password");
        String salary = (String) employeeObject.get("salary");

        new Employee(firstName, lastName, gender, nationalID, phoneNumber, age, storeCode, password, salary);
    }

    @SuppressWarnings("unchecked")
    public static void readEmployees() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("employees.json")) {
            Object object = jsonParser.parse(reader);

            JSONArray employeeList = (JSONArray) object;
            System.out.println(employeeList);

            employeeList.forEach( emp -> ReadAndWrite.parseEmployeeObject((JSONObject) emp));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void writeEmployees() {
        JSONArray employeeList = new JSONArray();
        for (int i = 0; i < Employee.getAllEmployees().size(); i++) {
            Employee employee = Employee.getAllEmployees().get(i);
            JSONObject employeeDetails = new JSONObject();
            employeeDetails.put("firstName", employee.getFirstName());
            employeeDetails.put("lastName", employee.getLastName());
            employeeDetails.put("gender", employee.getGender());
            employeeDetails.put("nationalID", employee.getNationalID());
            employeeDetails.put("phoneNumber", employee.getPhoneNumber());
            employeeDetails.put("age", employee.getAge());
            employeeDetails.put("storeCode", employee.getStoreCode());
            employeeDetails.put("password", employee.getPassword());
            employeeDetails.put("salary", employee.getSalary());

            JSONObject employeeObject = new JSONObject();
            employeeObject.put("employee", employeeDetails);
            employeeList.add(employeeObject);
        }
        try(FileWriter file = new FileWriter("employees.json")) {
            file.write(employeeList.toJSONString());
            file.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /* Read and Write for Customers */
    @SuppressWarnings("Unchecked")
    public static void parseCustomerObject(JSONObject customer) {
        JSONObject employeeObject = (JSONObject) customer.get("customer");

        String firstName = (String) employeeObject.get("firstName");
        String lastName = (String) employeeObject.get("lastName");
        String gender = (String) employeeObject.get("gender");
        String nationalID = (String) employeeObject.get("nationalID");
        String phoneNumber = (String) employeeObject.get("phoneNumber");
        Long age = (Long) employeeObject.get("age");
        String storeCode = (String) employeeObject.get("storeCode");
        String password = (String) employeeObject.get("password");

        ArrayList<Purchase> allPurchaseList = new ArrayList<>();
        ArrayList<String> productCodesList = new ArrayList<>();
        ArrayList<Long> numberOfEachProductList = new ArrayList<>();

        JSONArray allPurchase = (JSONArray) employeeObject.get("allPurchases");
        for (int i = 0; i < allPurchase.size(); i++) {
            JSONObject purchase = (JSONObject) allPurchase.get(i);
            JSONObject purchaseInfo = (JSONObject) purchase.get("purchase");
            String dateOfPurchase = (String) purchaseInfo.get("dateOfPurchase");
            Double priceOfPurchase = (Double) purchaseInfo.get("priceOfPurchase");
            JSONArray productCodes = (JSONArray) purchaseInfo.get("productCodes");
            JSONArray numberOfEachProduct = (JSONArray) purchaseInfo.get("numberOfEachProduct");
            for (int j = 0; j < productCodes.size(); j++) {
                productCodesList.add((String) productCodes.get(j));
                numberOfEachProductList.add((Long) numberOfEachProduct.get(j));
            }
            Purchase purchase1 = new Purchase(productCodesList, numberOfEachProductList, dateOfPurchase, priceOfPurchase);
            allPurchaseList.add(purchase1);
        }

        new Customer(firstName, lastName, gender, nationalID, phoneNumber, age, storeCode, password, allPurchaseList);
    }

    @SuppressWarnings("unchecked")
    public static void readCustomers() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("customers.json")) {
            Object object = jsonParser.parse(reader);

            JSONArray customerList = (JSONArray) object;
            System.out.println(customerList);

            customerList.forEach( emp -> ReadAndWrite.parseCustomerObject((JSONObject) emp));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void writeCustomers() {
        JSONArray customerList = new JSONArray();
        for (int i = 0; i < Customer.getAllCustomers().size(); i++) {
            Customer customer = Customer.getAllCustomers().get(i);
            JSONObject customerDetails = new JSONObject();
            customerDetails.put("firstName", customer.getFirstName());
            customerDetails.put("lastName", customer.getLastName());
            customerDetails.put("gender", customer.getGender());
            customerDetails.put("nationalID", customer.getNationalID());
            customerDetails.put("phoneNumber", customer.getPhoneNumber());
            customerDetails.put("age", customer.getAge());
            customerDetails.put("storeCode", customer.getStoreCode());
            customerDetails.put("password", customer.getPassword());
            JSONArray purchaseList = new JSONArray();
            for (int j = 0; j < customer.getAllPurchases().size(); j++) {
                Purchase purchase = customer.getAllPurchases().get(j);
                JSONObject purchaseDetails = new JSONObject();
                JSONArray purchaseProductCode = new JSONArray();
                JSONArray purchaseNumberOfEachProduct = new JSONArray();
                for (int k = 0; k < purchase.getProductCodes().size(); k++) {
                    purchaseProductCode.add(purchase.getProductCodes().get(k));
                    purchaseNumberOfEachProduct.add(purchase.getNumberOfEachProduct().get(k));
                }
                purchaseDetails.put("productCodes", purchaseProductCode);
                purchaseDetails.put("numberOfEachProduct", purchaseNumberOfEachProduct);
                purchaseDetails.put("dateOfPurchase", purchase.getFormattedDate());
                purchaseDetails.put("priceOfPurchase", purchase.getPriceOfPurchase());

                JSONObject purchaseObject = new JSONObject();
                purchaseObject.put("purchase", purchaseDetails);
                purchaseList.add(purchaseObject);
            }
            customerDetails.put("allPurchases", purchaseList);

            JSONObject customerObject = new JSONObject();
            customerObject.put("customer", customerDetails);
            customerList.add(customerObject);
        }
        try(FileWriter file = new FileWriter("customers.json")) {
            file.write(customerList.toJSONString());
            file.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /* Read and Write for Products */
    @SuppressWarnings("Unchecked")
    public static void parseProductObject(JSONObject products, int allAvailableOrSoldNumber) {
        JSONObject productObject = (JSONObject) products.get("product");

        String name = (String) productObject.get("name");
        Double buyingPrice = (Double) productObject.get("buyingPrice");
        Double sellingPrice = (Double) productObject.get("sellingPrice");
        Long numberOfAvailableProducts = (Long) productObject.get("numberOfAvailableProducts");
        String productCode = (String) productObject.get("productCode");

        new Product(name, buyingPrice, sellingPrice, numberOfAvailableProducts, productCode, allAvailableOrSoldNumber);
    }

    @SuppressWarnings("unchecked")
    public static void readAllProducts(String allAvailableOrSold, int allAvailableOrSoldNumber) {
        /*
         in case of availableProduct, allAvailableOrSoldNumber == 1;
         in case of allProduct, allAvailableOrSoldNumber == 2;
         in case of soldProduct, allAvailableOrSoldNumber == 3;
        */
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(allAvailableOrSold)) {
            Object object = jsonParser.parse(reader);

            JSONArray productsList = (JSONArray) object;
            System.out.println(productsList);

            productsList.forEach( emp -> ReadAndWrite.parseProductObject((JSONObject) emp, allAvailableOrSoldNumber));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void writeProducts(ArrayList<Product> products, String allAvailableOrSold) {
        JSONArray productsList = new JSONArray();
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            JSONObject productDetails = new JSONObject();
            productDetails.put("name", product.getName());
            productDetails.put("buyingPrice", product.getBuyingPrice());
            productDetails.put("sellingPrice", product.getSellingPrice());
            productDetails.put("numberOfAvailableProducts", product.getNumberOfAvailableProduct());
            productDetails.put("productCode", product.getProductCode());

            JSONObject peopleObject = new JSONObject();
            peopleObject.put("product", productDetails);
            productsList.add(peopleObject);
        }
        try(FileWriter file = new FileWriter(allAvailableOrSold)) {
            file.write(productsList.toJSONString());
            file.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}