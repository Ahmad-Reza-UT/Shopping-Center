package com.company;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseTest {
    Product product1;
    Product product2;

    @BeforeEach
    public void init(){
        product1=new Product("Nike21",14d,12d,21l,"12345");
        product2=new Product("Adidas",15d,13d,12l,"12341");
    }

    @Test
    @DisplayName("Purchase Testing")
    public void  purchaseTest(){
        ArrayList<Long> numberOfProducts=new ArrayList<>();
        ArrayList<String> productsCodes =new ArrayList<>();
        numberOfProducts.add(3l);
        numberOfProducts.add(12l);
        productsCodes.add(product1.getProductCode());
        productsCodes.add(product2.getProductCode());
        Purchase purchase=new Purchase(productsCodes,numberOfProducts);

        Assertions.assertEquals(12,product2.getNumberOfSoldProduct());


        Assertions.assertEquals(3*product1.getSellingPrice()+12*product2.getSellingPrice(),purchase.getPriceOfPurchase());





    }


}