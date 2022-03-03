package com.company;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    Product product1;
    Product product2;
    Product product3;
    Product product4;
    ArrayList<Purchase> purchases1;
    ArrayList<Purchase> purchases2;
    ArrayList<Purchase> purchases3;
    ArrayList <Long> numberOfEachProduct;
    ArrayList<String> productCodes;
    ByteArrayOutputStream output=new ByteArrayOutputStream();
    Customer customer1;
    Customer customer2;
    Customer customer3;

    @BeforeEach
    public void init(){
        product1=new Product("Nike21",14d,12d,21l,"12345");
        product2=new Product("Adidas",15d,13d,19l,"12341");
        product3=new Product("Puma",12d,10d,17l,"23412");
        product4=new Product("Holiday",11d,12d,18l,"21345");
        purchases1=new ArrayList<>();
        purchases2=new ArrayList<>();
        purchases3=new ArrayList<>();

        numberOfEachProduct=new ArrayList<>();
        productCodes=new ArrayList<>();
        productCodes.add(product1.getProductCode());productCodes.add(product2.getProductCode());
        numberOfEachProduct.add(3l);numberOfEachProduct.add(4l);
        Purchase purchase=new Purchase(productCodes,numberOfEachProduct);
        purchases1.add(purchase);
        numberOfEachProduct.clear();numberOfEachProduct.add(4l);numberOfEachProduct.add(5l);
        productCodes.clear();productCodes.add(product1.getProductCode());productCodes.add(product3.getProductCode());
        purchase=new Purchase(productCodes,numberOfEachProduct);
        purchases1.add(purchase);

        numberOfEachProduct.clear();numberOfEachProduct.add(2l);numberOfEachProduct.add(1l);
        productCodes.clear();productCodes.add(product2.getProductCode());productCodes.add(product4.getProductCode());
        purchase=new Purchase(productCodes,numberOfEachProduct);
        purchases2.add(purchase);
        numberOfEachProduct.clear();numberOfEachProduct.add(3l);numberOfEachProduct.add(5l);
        productCodes.clear();productCodes.add(product4.getProductCode());productCodes.add(product2.getProductCode());
        purchase=new Purchase(productCodes,numberOfEachProduct);
        purchases2.add(purchase);

        numberOfEachProduct.clear();numberOfEachProduct.add(4l);numberOfEachProduct.add(3l);
        productCodes.clear();productCodes.add(product1.getProductCode());productCodes.add(product4.getProductCode());
        purchase=new Purchase(productCodes,numberOfEachProduct);
        purchases3.add(purchase);numberOfEachProduct.clear();numberOfEachProduct.add(2l);numberOfEachProduct.add(2l);
        productCodes.clear();productCodes.add(product3.getProductCode());productCodes.add(product2.getProductCode());
        purchase=new Purchase(productCodes,numberOfEachProduct);
        purchases3.add(purchase);
        customer1=new Customer("Mahdyar","Mohammadi","Male","0381236765","09421213445",32l,"32121","ijejewhhe2",purchases1);
        customer2=new Customer("Atena","Shahriari","Female","0021235465","09121403751",47l,"67321","juliiiiiiip",purchases2);
        customer3=new Customer("Bardia","Moghaddam","Male","0381235465","09124456789",19l,"12345","123123131",purchases3);

        System.setOut(new PrintStream(output));
    }

    @AfterEach
    public void reset(){
        output.reset();
        Customer.getAllCustomers().clear();
    }

    @Test
    @DisplayName("Purchase History Checking")
    public void purchaseTest(){
        customer1.printingHistoryOfPurchase();
        String expectedOutPut="History of All Purchase: \r\n"+"Purchase Number 1: \r\n"+purchases1.get(0).toString()+"\r\n\r\n"+"Purchase Number 2: \r\n"+purchases1.get(1).toString()+"\r\n\r\n";
        Assertions.assertEquals(expectedOutPut,output.toString());

        output.reset();
        customer2.printingHistoryOfPurchase();
        expectedOutPut="History of All Purchase: \r\n"+"Purchase Number 1: \r\n"+purchases2.get(0).toString()+"\r\n\r\n"+"Purchase Number 2: \r\n"+purchases2.get(1).toString()+"\r\n\r\n";
        Assertions.assertEquals(expectedOutPut,output.toString());

        output.reset();
        customer3.printingHistoryOfPurchase();
        expectedOutPut="History of All Purchase: \r\n"+"Purchase Number 1: \r\n"+purchases3.get(0).toString()+"\r\n\r\n"+"Purchase Number 2: \r\n"+purchases3.get(1).toString()+"\r\n\r\n";
        Assertions.assertEquals(expectedOutPut,output.toString());

    }

    @Test
    @DisplayName("Sort and Printing Customer Testing")
    public void printCustomer(){
        output.reset();
        Customer.printCustomer();
        String expectedOutPut="All Customers: \r\n"+customer1.toString()+"\r\n\r\n"+customer2.toString()+"\r\n\r\n"+customer3.toString()+"\r\n\r\n";
        Assertions.assertEquals(expectedOutPut,output.toString());

        output.reset();
        Customer.sortCustomersByName();
        Customer.printCustomer();
        expectedOutPut="All Customers: \r\n"+customer2.toString()+"\r\n\r\n"+customer3.toString()+"\r\n\r\n"+customer1.toString()+"\r\n\r\n";
        Assertions.assertEquals(expectedOutPut,output.toString());

        output.reset();
        Customer.sortCustomersByStoreCode();
        Customer.printCustomer();
        expectedOutPut="All Customers: \r\n"+customer3.toString()+"\r\n\r\n"+customer1.toString()+"\r\n\r\n"+customer2.toString()+"\r\n\r\n";
        Assertions.assertEquals(expectedOutPut,output.toString());

        output.reset();
        Customer.sortCustomersByNationalID();
        Customer.printCustomer();
        expectedOutPut="All Customers: \r\n"+customer2.toString()+"\r\n\r\n"+customer3.toString()+"\r\n\r\n"+customer1.toString()+"\r\n\r\n";
        Assertions.assertEquals(expectedOutPut,output.toString());





    }

    @Test
    @DisplayName("Customer Search Checking")
    public void searchCheck(){
     output.reset();
     int index= Customer.searchInCustomersByFullName(customer1.getFirstName(),customer1.getLastName());
     Assertions.assertEquals(customer1,Customer.indexToInstance(index));

     index =Customer.searchInCustomersByFullName(customer2.getFirstName(),customer2.getLastName());
     Assertions.assertEquals(customer2,Customer.indexToInstance(index));

     index =Customer.searchInCustomersByFullName(customer3.getFirstName(),customer3.getLastName());
     Assertions.assertEquals(customer3,Customer.indexToInstance(index));

     index=Customer.searchInCustomersByFullName("Ali","Mohammadi");
     Assertions.assertTrue(index<=-1);

     index=Customer.searchInCustomersByNationalID(customer1.getNationalID());
     Assertions.assertEquals(customer1,Customer.indexToInstance(index));

     index=Customer.searchInCustomersByNationalID(customer2.getNationalID());
     Assertions.assertEquals(customer2,Customer.indexToInstance(index));

     index=Customer.searchInCustomersByNationalID(customer3.getNationalID());
     Assertions.assertEquals(customer3,Customer.indexToInstance(index));

     index=Customer.searchInCustomersByNationalID("0031233657");
     Assertions.assertTrue(index<=-1);

     index=Customer.searchInCustomersByStoreCode(customer1.getStoreCode());
     Assertions.assertEquals(customer1,Customer.indexToInstance(index));

     index=Customer.searchInCustomersByStoreCode(customer2.getStoreCode());
     Assertions.assertEquals(customer2,Customer.indexToInstance(index));

     index=Customer.searchInCustomersByStoreCode(customer3.getStoreCode());
     Assertions.assertEquals(customer3,Customer.indexToInstance(index));

     index=Customer.searchInCustomersByStoreCode("32451");
     Assertions.assertTrue(index<=-1);

    }




}