package com.company;

import org.junit.jupiter.api.*;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    Product product1;
    Product product2;
    Product product3;
    ArrayList<Product> products;
    ByteArrayOutputStream output=new ByteArrayOutputStream();

    @BeforeEach
    public void init(){
        Product.getAvailableProducts().clear();
        Product.getAllProducts().clear();
        Product.getSoldProducts().clear();
        Product.setAllProfit(0d);
        product1=new Product("Nike21",14d,12d,21l,"12345");
        product2=new Product("Adidas",15d,13d,12l,"12341");
        product3=new Product("Puma",12d,10d,3l,"23412");
        products=new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    public void reset(){
      output.reset();

    }

    @Test
    @DisplayName("Sell Checking")
    public void sellCheck(){
        int AvailableProduct1=product1.getNumberOfAvailableProduct();
        int allProducts=Product.getAllProducts().size();
        int numberOfAvailableProducts=Product.getAvailableProducts().size();
        product1.soldProduct(AvailableProduct1);//selling all available products
        product2.soldProduct(3);
        double profit=product2.getProfitOfAllThisProduct()+product1.getProfitOfAllThisProduct();

        Assertions.assertEquals(0,product1.getNumberOfAvailableProduct());

        Assertions.assertTrue(Product.searchInProductsByName(Product.getSoldProducts(),product1.getName())>-1);
        Assertions.assertTrue(Product.searchInProductsByName(Product.getSoldProducts(),product2.getName())>-1);

        Assertions.assertEquals(AvailableProduct1,product1.getNumberOfSoldProduct());
        Assertions.assertEquals(numberOfAvailableProducts-1,Product.getAvailableProducts().size());
        Assertions.assertEquals(allProducts,Product.getAllProducts().size());
        Assertions.assertTrue(Product.searchInProductsByName(Product.getAvailableProducts(),product1.getName())<=-1);
        Assertions.assertTrue(Product.searchInProductsByName(Product.getSoldProducts(),product1.getName())>-1);
        Assertions.assertEquals(product1.getNumberOfSoldProduct()*product1.getProfitOfOneProduct(),product1.getProfitOfAllThisProduct());
        Assertions.assertEquals(Product.getAllProfit(),profit);
    }
    @Test
    @DisplayName("Print Checking")
    public void printCheck(){
        ByteArrayOutputStream output=new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Product.printProducts(products);
        String expectedOutput="All Products: \r\n"+product1.toString()+"\r\n\r\n"+product2.toString()+"\r\n\r\n"+product3.toString()+"\r\n\r\n";
        Assertions.assertEquals(expectedOutput,output.toString());

        output.reset();
        int availableProduct1=product1.getNumberOfAvailableProduct();
        product1.soldProduct(product1.getNumberOfAvailableProduct());
        Product.printProductsForCustomers(products);
        Assertions.assertEquals(0,product1.getNumberOfAvailableProduct());
        expectedOutput="Available Products: \r\n"+product2.toStringForCustomer()+"\r\n\r\n"+product3.toStringForCustomer()+"\r\n\r\n";
        Assertions.assertEquals(expectedOutput,output.toString());


    }
    @Test
    @DisplayName("sort Checking")
    public void sortCheck(){

        Product.sortProductsByProductCode(products);
        Product.printProductsForCustomers(products);
        String expectedOutput="Available Products: \r\n"+product2.toStringForCustomer()+"\r\n\r\n"+product1.toStringForCustomer()+"\r\n\r\n"+product3.toStringForCustomer()+"\r\n\r\n";
        Assertions.assertEquals(expectedOutput,output.toString());

        output.reset();
        Product.sortProductsByName(products);
        expectedOutput="All Products: \r\n"+product2.toString()+"\r\n\r\n"+product1.toString()+"\r\n\r\n"+product3.toString()+"\r\n\r\n";
        Product.printProducts(products);
        Assertions.assertEquals(expectedOutput,output.toString());
    }

    @Test
    @DisplayName("Search Checking")
    public void searchCheck(){

        Assertions.assertTrue(Product.searchInProductByProductCode(Product.getAvailableProducts(),product1.getProductCode())>-1);
        Assertions.assertTrue(Product.searchInProductByProductCode(Product.getAvailableProducts(),product2.getProductCode())>-1);
        Assertions.assertTrue(Product.getAvailableProducts().contains(product1));
        Assertions.assertTrue(Product.searchInProductsByName(Product.getAvailableProducts(),product1.getName())>-1);
        Assertions.assertTrue(Product.searchInProductsByName(Product.getAvailableProducts(),product2.getName())>-1);
        Assertions.assertTrue(Product.searchInProductsByName(Product.getAvailableProducts(),product3.getName())>-1);
        Assertions.assertTrue(Product.searchInProductsByName(Product.getAvailableProducts(),product2.getName())>-1);

        Assertions.assertSame(product1,Product.productFinder(product1.getProductCode()));
        Assertions.assertSame(product2,Product.productFinder(product2.getProductCode()));

    }



}