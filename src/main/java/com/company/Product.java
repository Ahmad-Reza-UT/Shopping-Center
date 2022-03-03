package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Product {

    Scanner scanner = new Scanner(System.in);

    /* Fields */
    private static ArrayList<Product> allProducts =new ArrayList<>();
    private static ArrayList<Product> soldProducts =new ArrayList<>();
    private static ArrayList<Product> availableProducts =new ArrayList<>();
    private String name;
    private double sellingPrice;
    private double buyingPrice;
    private double profitOfOneProduct = 0; /* for one product | when a product instance created, this valuable would ba assigned */
    private double profitOfAllThisProduct = 0; /* for sold product ==> profitOfOneProduct * numberOfSoldProduct | when a purchase confirmed, this valuable would be updated */
    private static double allProfit = 0; /* summation of all ProfitOfAllThisProduct | when a purchase confirmed, this valuable would be updated */
    private int numberOfAvailableProduct; /* how many are there */
    private int numberOfSoldProduct = 0; /* how many are sold */
    private String productCode;

    /* setter and getters */
    public static ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public static ArrayList<Product> getAvailableProducts() {
       return availableProducts;
    }

    public static ArrayList<Product> getSoldProducts() {
        return soldProducts;
    }

    public int getNumberOfAvailableProduct() {
        return numberOfAvailableProduct;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public String getProductCode() {
        return productCode;
    }

    public double getProfitOfOneProduct() {
        return profitOfOneProduct;
    }

    public double getProfitOfAllThisProduct() {
        return profitOfAllThisProduct;
    }

    public int getNumberOfSoldProduct() {
        return numberOfSoldProduct;
    }

    public String getName() {
        return name;
    }

    public void setName() {
        name = getValidName();
    }

    public void setSellingPrice() {
        sellingPrice = getValidPrice("selling");
    }

    public void setBuyingPrice() {
        buyingPrice = getValidPrice("selling");
    }

    public void setNumberOfAvailableProduct(int numberOfAvailableProduct) {
        this.numberOfAvailableProduct = numberOfAvailableProduct;
    }

    public void setValidNumberOfAvailableProduct() {
        numberOfAvailableProduct = getValidNumberOfAvailableProducts();
    }

    public void setProductCode() {
        productCode = getValidProductCode();
    }

    public static double getAllProfit() {
        return allProfit;
    }

    public static void setAllProfit(double allProfit) {
        Product.allProfit = allProfit;
    }

    /* constructor */
    /* Default constructor */
    public Product() {
        this.name = getValidName();
        this.buyingPrice = getValidPrice("buying");
        this.sellingPrice = getValidPrice("selling");
        this.numberOfAvailableProduct = getValidNumberOfAvailableProducts();
        this.productCode = getValidProductCode();
        allProducts.add(this);
        availableProducts.add(this);
    }

    /* following constructor is for adding sold product  and also using for reading from Json file */
    public Product(String name, double buyingPrice, double sellingPrice, Long numberOfAvailableProduct, String productCode, int i) {
        /* if i == 1, add to available product; else if i == 2, add to all product; else if i == 3, add to sold product; */
        this.name = name;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.numberOfAvailableProduct = numberOfAvailableProduct.intValue();
        this.profitOfOneProduct = sellingPrice - buyingPrice;
        this.productCode = productCode;
        if (i == 1) {
            availableProducts.add(this);
        }
        else if (i == 2) {
            allProducts.add(this);
        }
        else if (i == 3) {
            soldProducts.add(this);
        }
    }

    /* following constructor is for adding sold product  and also using for reading from Json file */
    public Product(String name, double buyingPrice, double sellingPrice, Long numberOfAvailableProduct, String productCode) {
        /* if i == 1, add to available product; else if i == 2, add to all product; else if i == 3, add to sold product; */
        this.name = name;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.numberOfAvailableProduct = numberOfAvailableProduct.intValue();
        this.profitOfOneProduct = sellingPrice - buyingPrice;
        this.productCode = productCode;
        allProducts.add(this);
        availableProducts.add(this);
    }

    /* following constructors are for Collections binary search */
    public Product(String name) {
        this.name = name;
    }

    public Product(String productCode, int i) {
        this.productCode = productCode;
    }

    /** Methods contains:
    * calculating profit of selling one product ==> profitOfOneProduct,
    * calculation profit of all sold product from this name ==> profitOfOneProduct * numberOfSoldProduct,
    * calculation profit of all sold products in this store ==> all(ProfitOfAllThisProduct),
    * doing some change in fields: numberOfAvailableProduct, numberOfSoldProduct, profitOfAllThisProduct, allProfit ==> done by soldProduct,
    * sort (by name and productCode),
    * search (by name and productCode),
    * printing,
    * getValidName,
    * getValidSellingPrice and getValidBuyingPrice,
    * getValidNumberOfAvailableProduct,
    * getValidProductCode,
    * */

    /* <-------------------- Getting and Validation Methods --------------------> */
    /* getValidName */
    public String getValidName() {
        System.out.print("-----> Enter Name of Product (Using just letters and numbers): ");
        String name = scanner.nextLine();
        while (!stringValidationBool(name) || isEqualProductNameExist(name)) {
            while (!stringValidationBool(name)) {
                System.out.print("***** You Entered Character(s) that aren't Letter or Numbers. ***** \n-----> Please Enter Product Name Again: ");
                name = scanner.nextLine();
            }
            while (isEqualProductNameExist(name)) {
                System.out.print("***** There is a product with this name in this store *****\n-----> Please Enter Product Name Again: ");
                name = scanner.nextLine();
            }
        }
        return name;
    }


    public Double getValidPrice(String buyingOrSelling) {
        double price = 0;
        boolean isPriceValid = false;
        while (!isPriceValid) {
            System.out.print("-----> Enter " + buyingOrSelling + " Price: ");
            String priceString = scanner.nextLine();
            try {
                price = Double.parseDouble(priceString);
                if (price < 0) {
                    System.out.println("***** Entered price is invalid *****");
                    continue;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("***** Entered price is invalid *****");
                continue;
            }
            isPriceValid = true;
        }
        return price;
    }

    public Integer getValidNumberOfAvailableProducts() {
        boolean isNumberValid = false;
        int numberOfAvailableProduct = 0;
        while (!isNumberValid) {
            System.out.print("-----> Enter Number of Available Product: ");
            String numberOfAvailableProductString = scanner.nextLine();
            try {
                numberOfAvailableProduct = Integer.parseInt(numberOfAvailableProductString);
                if (numberOfAvailableProduct <= 0) {
                    System.out.println("***** Entered Number is invalid *****");
                    continue;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("***** Entered Number is invalid *****");
                continue;
            }
            isNumberValid = true;
        }
        return numberOfAvailableProduct;
    }

    /* getValidProductCode */
    public static String getValidProductCode() {
        System.out.print("-----> Enter product code Code (5 Numbers): ");
        Scanner scanner = new Scanner(System.in);
        String productCode = scanner.nextLine();
        int idealLength = 5;
        while (characterValidation(productCode) != null || lengthValidation(productCode, idealLength) != null || isEqualProductCodeExist(productCode)) {
            while (characterValidation(productCode) != null) {
                System.out.print(characterValidation(productCode) + "\n-----> Enter product code Again (" + idealLength + " Numbers): ");
                productCode = scanner.nextLine();
            }
            while (lengthValidation(productCode, idealLength) != null) {
                System.out.print(lengthValidation(productCode, idealLength) + "\n-----> Enter product code Again (" + idealLength + " Numbers): ");
                productCode = scanner.nextLine();
            }
            while (isEqualProductCodeExist(productCode)) {
                System.out.print("***** There is a product in this store with this product code *****\n-----> Please enter valid product code: ");
                productCode = scanner.nextLine();
            }
        }
        return productCode;
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

    public static boolean isEqualProductCodeExist (String storeCode) {
        if (allProducts != null) {
            for (int i = 0; i < Product.getAllProducts().size(); i++) {
                if (storeCode.equals(Product.getAllProducts().get(i).getProductCode())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isEqualProductNameExist(String name) {
        if (allProducts != null) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).name.equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean stringValidationBool(String name) {
        for (int i = 0; i < name.length(); i++)
            if ((!((name.charAt(i) >= 65 && name.charAt(i) <= 90) ||
                    (name.charAt(i) >= 97 && name.charAt(i) <= 122|| ((name.charAt(i) >= 48) && (name.charAt(i) <= 57))))||
                    name.charAt(i) ==32 )) /* Using ASCII Charset to Limiting */
                return false;
        return true;
    }

    /* printing */
    public static void printProducts(ArrayList<Product> products) { /* products can be Product.allProduct, Product.availableProduct or Product.soldProduct */
        System.out.println("All Products: ");
        for (int i = 0; i < products.size(); i++) {
            System.out.println(products.get(i).toString());
            System.out.println();
        }
    }

    public static void printProductsForCustomers(ArrayList<Product> products) { /* products can be Product.allProduct, Product.availableProduct or Product.soldProduct */
        System.out.println("Available Products: ");
        for (int i = 0; i < products.size(); i++) {
            if (Product.getAllProducts().get(i).numberOfAvailableProduct == 0) {
                continue;
            }
            System.out.println(products.get(i).toStringForCustomer());
            System.out.println();
        }
    }

    /* sort by name */
    public static void sortProductsByName(ArrayList<Product> products) { /* products can be Product.allProduct, Product.availableProduct or Product.soldProduct */
        Collections.sort(products, new ProductNameComparator());
    }

    /* sort by productCode */
    public static void sortProductsByProductCode(ArrayList<Product> products) { /* products can be Product.allProduct, Product.availableProduct or Product.soldProduct */
        Collections.sort(products, new ProductCodeComparator());
    }

    /* Search by name */
    public static int searchInProductsByName (ArrayList<Product> products, String name) { /* products can be Product.allProduct, Product.availableProduct or Product.soldProduct */
        sortProductsByName(products);
        Product keyProduct = new Product(name);
        return Collections.binarySearch(products, keyProduct, new ProductNameComparator());
        /* if this method returned a number that is equal or less than -1, there is no product with that name */
    }

    /* Search by productCode */
    public static int searchInProductByProductCode(ArrayList<Product> products, String productCode) { /* products can be Product.allProduct, Product.availableProduct or Product.soldProduct */
        sortProductsByProductCode(products);
        Product keyProduct = new Product(productCode, 1);
        return Collections.binarySearch(products, keyProduct, new ProductCodeComparator());
        /* if this method returned a number that is equal or less than -1, there is no product with that productCode */
    }

    /* soldProduct */
    public synchronized void soldProduct(long number) { /* when a purchase confirmed, this method would be called */
        numberOfAvailableProduct -= number;
        numberOfSoldProduct += number;
        profitOfAllThisProduct += number * profitOfOneProduct;
        allProfit += number * profitOfOneProduct;
        soldProducts.add(new Product(this.name, this.buyingPrice, this.sellingPrice, (long) numberOfAvailableProduct, this.productCode, 3));
        if (this.numberOfAvailableProduct == 0) {
            availableProducts.remove(this);
        }
    }

    /* Product Finder */
    public static Product productFinder(String productCode) {
        /* this method is for returning instance of Product that we have it's productCode
        * Here we considered that customer just can chose product that is in availableProducts ArrayList for purchase
        * this method is used in sellProses of Purchase class */
        int indexOfKey = searchInProductByProductCode(allProducts, productCode);
        return availableProducts.get(indexOfKey);
    }

    /* productCode to name */
    public static String productCodeToName(ArrayList<Product> products, String productCode) {
        int indexOfKey = searchInProductByProductCode(products, productCode);
        return products.get(indexOfKey).getName();
    }

    @Override
    public String toString() {/* this toString method is for supervise */
        return "name: " + name +
                "     selling price: " + sellingPrice +
                "     buying price: " + buyingPrice +
                "     number of available product: " + numberOfAvailableProduct +
                "     number of sold product: " + numberOfSoldProduct +
                "     product code: " + productCode +
                "     profit of one product: " + profitOfOneProduct +
                "     profit of all sell of this product: " + profitOfAllThisProduct;
    }

    public String toStringForCustomer() {/* this toString method is for customer */
        return "name: " + name +
                "     price: " + sellingPrice +
                "     product code: " + productCode;
    }
}