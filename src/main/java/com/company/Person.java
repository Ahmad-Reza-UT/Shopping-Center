package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Person {

    Scanner scanner = new Scanner(System.in);

    /* Fields */
    private String firstName;
    private String lastName;
    private String gender;
    private String nationalID;
    private String phoneNumber;
    private Long age;

    /* Setter and Getters */
    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getGender(){
        return gender;
    }

    public String getNationalID(){
        return nationalID;
    }

    public Long getAge(){
        return age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setFirstName() {
        firstName = getValidFirstName();
    }

    public void setLastName() {
        lastName = getValidLastName();
    }

    public void setGender() {
        gender = getValidGender();
    }

    public void setNationalID() {
        nationalID = getValidNationalID();
    }

    public void setPhoneNumber() {
        phoneNumber = getValidPhoneNumber();
    }

    public void setAge() {
        age = getValidAge();
    }

    /* Constructor */
    /* Default constructor */
    public Person(){
        firstName = getValidFirstName();
        lastName = getValidLastName();
        gender = getValidGender();
        nationalID = getValidNationalID();
        phoneNumber = getValidPhoneNumber();
        age = getValidAge();
    }

    /* following constructor is for reading data from .Json file */
    public Person(String firstName, String lastName, String gender, String nationalID, String phoneNumber, Long age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.nationalID = nationalID;
        this.phoneNumber = phoneNumber;
        this.age = age;
    }

    /* following constructors are for Collections binary search */
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(String storeCode) {
    }

    public Person(String nationalID, int i) {
        this.nationalID = nationalID;
    }

    /**
     * Methods contain:
     * getValid methods that give information from client and validate them, these methods contains:
     * getValidFirstName,
     * getValidLastName,
     * getValidGender,
     * getValidNationalID,
     * getValidPhoneNumber and
     * getValidAge.
     * numberValidation(String test): check each character to be a number.
     * numberValidation(String test , int idealLength): compare length of number with ideal length.
     * stringValidationBool(String test): check each character to be letters.
     * isEqualNationalIDExist(String nationalID): check all nationalIDs that stored to be unique.
     * */

    /* <-------------------- Getting and Validation Methods --------------------> */
    public String getValidFirstName() {
        System.out.print("-----> Enter First Name (in English): ");
        String firstName = scanner.nextLine();
        while (!stringValidationBool(firstName)) {
            System.out.print("***** You Entered Character(s) that aren't Letter. ***** \n-----> Please Enter Your First Name Again : ");
            firstName = scanner.nextLine();
        }
        return firstName;
    }

    public String getValidLastName(){
        System.out.print("-----> Enter Last Name (in English): ");
        String lastName = scanner.nextLine();
        while (!stringValidationBool(lastName)) {
            System.out.print("***** You Entered Character(s) that aren't Letter. ***** \n-----> Please Enter Your Last Name Again : ");
            lastName = scanner.nextLine();
        }
        return lastName;
    }

    public String getValidGender(){
        System.out.print("-----> Enter Gender (Male or Female): ");
        String gender = scanner.nextLine();
        while (!(gender.equals("Male") || gender.equals("male".toLowerCase()) || gender.equals("male".toUpperCase()) ||
                gender.equals("Female") || gender.equals("female".toLowerCase()) || gender.equals("female".toUpperCase()))) {
            System.out.print("***** Your Entered Gender isn't Valid. *****\n-----> Please Enter Gender Again : ");
            gender = scanner.nextLine();
        }
        return gender;
    }

    public String getValidNationalID(){
        String nationalID = null;
        try {
            System.out.print("-----> Enter National Code (Ex : 1234567890): ");
            nationalID = scanner.nextLine();
            while (numberValidation(nationalID) != null || numberValidation(nationalID, 10) != null || isEqualNationalIDExist(nationalID)) {
                while (numberValidation(nationalID) != null) {
                    System.out.print(numberValidation(nationalID) + "\n-----> Please Enter Your National Code Again : ");
                    nationalID = scanner.nextLine();
                }
                while (numberValidation(nationalID, 10) != null) {
                    System.out.print(numberValidation(nationalID, 10) + "\n-----> Please Enter Your National Code Again : ");
                    nationalID = scanner.nextLine();
                }
                while (isEqualNationalIDExist(nationalID)) {
                    System.out.print("***** There is a person with this national ID. *****\n-----> Please enter your valid national ID again: ");
                    nationalID = scanner.nextLine();
                }
            }
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return nationalID; // Assignment After Validation
    }

    public String getValidPhoneNumber(){
        System.out.print("-----> Enter Phone Number (Ex : 09123456789): ");
        String phoneNumber = scanner.nextLine();

        while (numberValidation(phoneNumber) != null || numberValidation(phoneNumber, 11) != null || phoneNumber.charAt(0) != 48 || phoneNumber.charAt(1) != 57) {
            while (numberValidation(phoneNumber) != null) {
                System.out.print(numberValidation(phoneNumber) + "\n-----> Please Enter Your Phone Number Again : ");
                phoneNumber = scanner.nextLine();
            }
            while (numberValidation(phoneNumber, 11) != null) {
                System.out.print(numberValidation(phoneNumber, 11) + "\n-----> Please Enter Your Phone Number Again : ");
                phoneNumber = scanner.nextLine();
            }
            while (phoneNumber.charAt(0) != 48) {
                System.out.print("***** The First Number of Phone Number is 0 *****\n-----> Please Enter Your Valid Information Again : ");
                phoneNumber = scanner.nextLine();
            }
            while (phoneNumber.charAt(1) != 57) {
                System.out.print("***** The Second Number of Phone Number is 9 *****\n-----> Please Enter Your Valid Information Again : ");
                phoneNumber = scanner.nextLine();
            }
        }
        return phoneNumber; // Assignment After Validation
    }

    public long getValidAge(){
        boolean isEnteredAgeValid = false;
        int age = 0;
        while (!isEnteredAgeValid) {
            System.out.print("-----> Enter Age (Ex : 35 | You should be older than 15): ");
            String ageString = scanner.nextLine();

            try {
                age = Integer.parseInt(ageString);
            }
            catch (NumberFormatException e) {
                System.out.println("***** Your entered age is invalid *****");
                continue;
            }
            if (age > 140 || age < 15){
                System.out.println("***** Your entered age is invalid *****");
                continue;
            }
            isEnteredAgeValid = true;
        }
        return age;
    }

    /* <-------------------- Method for Number Validation --------------------> */
    public String numberValidation(String test , int idealLength) {
        if (idealLength > test.length())
            return "***** Your Entered Information is Smaller then Standard Case. *****";
        else if (idealLength < test.length())
            return "***** Your Entered Information is Longer than Standard Case. *****";
        else
            return null;
    }

    /* <-------------------- Method for String Validation --------------------> */
    public String numberValidation(String test) {
        for (int i = 0; i < test.length(); i++) {
            /* Using ASCII Charset to Limiting */
            if (!(test.charAt(i) >= 48 && test.charAt(i) <= 57))
                return "***** You Entered Character(s) that aren't Number. *****";
        }
        return null;
    }

    /* <-------------------- Method for Validation of First Name and Last Name String --------------------> */
    public boolean stringValidationBool(String test) {
        for (int i = 0; i < test.length(); i++)
            if ((!((test.charAt(i) >= 65 && test.charAt(i) <= 90) ||
                    (test.charAt(i) >= 97 && test.charAt(i) <= 122) || test.charAt(i) == 32)))/* Using ASCII Charset to Limiting */
                return false;
        return true;
    }

    public boolean isEqualNationalIDExist(String nationalID) {
        ArrayList<Employee> employees = Employee.getAllEmployees();
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getNationalID().equals(nationalID)) {
                return true;
            }
        }
        ArrayList<Customer> customers = Customer.getAllCustomers();
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getNationalID().equals(nationalID)) {
                return true;
            }
        }
        if (Manager.getOneManager() != null) {
            if (Manager.getOneManager().getNationalID().equals(nationalID)) {
                return true;
            }
        }
        return false;
    }
}