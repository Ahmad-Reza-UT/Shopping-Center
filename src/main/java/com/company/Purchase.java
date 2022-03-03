package com.company;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Purchase {

    /* Fields */
    private ArrayList<String> productCodes; /* validated in Customer */
    private ArrayList<Long> numberOfEachProduct; /* validated in Customer */
    private LocalDateTime dateOfPurchase;
    private DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private String formattedDate;
    private Double priceOfPurchase;

    /* Constructor */
    public Purchase(ArrayList<String> productCodes, ArrayList<Long> numberOfEachProduct, Customer customer) {/* using parallel arrayList for productCodes and number of each product */
        this.productCodes = productCodes;
        this.numberOfEachProduct = numberOfEachProduct;
        this.dateOfPurchase = LocalDateTime.now();
        this.formattedDate = dateOfPurchase.format(myFormatObj);
        if (!isThereAvailableProduct(productCodes, numberOfEachProduct)) {
            System.out.println("***** Availability of your considered product(s) finished *****");
        }
        else {
            setPriceOfPurchases();
            sellProses();
            customer.getAllPurchases().add(this);
        }
    }

    public Purchase(ArrayList<String> productCodes, ArrayList<Long> numberOfEachProduct) {/* using parallel arrayList for productCodes and number of each product */
        this.productCodes = productCodes;
        this.numberOfEachProduct = numberOfEachProduct;
        this.dateOfPurchase = LocalDateTime.now();
        this.formattedDate = dateOfPurchase.format(myFormatObj);
        setPriceOfPurchases();
        sellProses();
    }

    /* following constructor is for reading data from json file */
    public Purchase(ArrayList<String> productCodes, ArrayList<Long> numberOfEachProduct, String formattedDate, Double priceOfPurchase) {
        this.productCodes = productCodes;
        this.numberOfEachProduct = numberOfEachProduct;
        this.formattedDate = formattedDate;
        this.priceOfPurchase = priceOfPurchase;
    }

    /* getters */
    public ArrayList<String> getProductCodes() {
        return productCodes;
    }

    public ArrayList<Long> getNumberOfEachProduct() {
        return numberOfEachProduct;
    }

    public Double getPriceOfPurchase() {
        return priceOfPurchase;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    /** Methods contain:
    * in this class we assume information of productCodes and numberOfProduct lists are valid and don't want validation,
    * validation is done in main or customer,
    * toString,
    * sellProses that do the proses of selling, for each product this method calls soldProduct(); for changing information of that product,
    * setPriceOfPurchase.
    *  */

    public boolean isThereAvailableProduct(ArrayList<String> productCodes, ArrayList<Long> numberOfEachProduct) {
        for (int i = 0; i < productCodes.size(); i++) {
            String currentProductCode = productCodes.get(i);
            Long currentNumberOfEachProduct = numberOfEachProduct.get(i);
            int indexOfProduct = Product.searchInProductByProductCode(Product.getAvailableProducts(), currentProductCode);
            if (indexOfProduct <= -1) {
                return false;
            }
            Product currentProduct = Product.getAvailableProducts().get(indexOfProduct);
            if (currentNumberOfEachProduct > currentProduct.getNumberOfAvailableProduct()) {
                return false;
            }
        }
        return true;
    }

    public synchronized void sellProses() {
        for (int i = 0; i < productCodes.size(); i++) {
            String productCode = productCodes.get(i);/* current product */
            long number = numberOfEachProduct.get(i); /* current number */
            Product sellingProduct = Product.productFinder(productCode); /* finding instance of current product */
            sellingProduct.soldProduct(number);
        }
    }

    public void setPriceOfPurchase() {
        double totalPrice = 0;
        for (int i = 0; i < productCodes.size(); i++) {
            long numberOfEachProduct = getNumberOfEachProduct().get(i);
            int indexOfKeyProduct = Product.searchInProductByProductCode(Product.getAvailableProducts(), productCodes.get(i));
            Product keyProduct = Product.getAllProducts().get(indexOfKeyProduct);
            totalPrice += (keyProduct.getSellingPrice() * numberOfEachProduct);
        }
        this.priceOfPurchase = totalPrice;
    }

    public void setPriceOfPurchases(){
        double totalprice=0;
        for (int i=0;i<productCodes.size();i++){
            int indexOfKeyProduct = Product.searchInProductByProductCode(Product.getAvailableProducts(), productCodes.get(i));
            totalprice+= Product.getAvailableProducts().get(indexOfKeyProduct).getSellingPrice()*numberOfEachProduct.get(i);
        }
        this.priceOfPurchase=totalprice;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < productCodes.size(); i++) {
            String productCode = productCodes.get(i);
            long number = numberOfEachProduct.get(i);
            int indexOfKey = Product.searchInProductByProductCode(Product.getAllProducts() ,productCode);
            double priceOfOneProduct = Product.getAllProducts().get(indexOfKey).getSellingPrice();
            double totalPrice = priceOfOneProduct * number;
            String nameOfProduct = Product.productCodeToName(Product.getAllProducts(), productCode);
            stringBuilder.append("Name of Product: " + nameOfProduct +
                    "     Number: " + number +
                    "     Price of one product: " + priceOfOneProduct +
                    "     Price for this product: " + totalPrice +
                    "     Product code: " + productCode +
                    "     Date of Purchase: " + formattedDate + "\n");
        }
        stringBuilder.append("=====> Total Price: " + priceOfPurchase);
        return stringBuilder.toString();
    }
}
