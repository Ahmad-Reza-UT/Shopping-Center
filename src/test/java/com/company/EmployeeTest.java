package com.company;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    Employee employee1;
    Employee employee2;
    Employee employee3;
    ByteArrayOutputStream output=new ByteArrayOutputStream();

    @BeforeEach
    public void init(){

        employee1=new Employee("Mahdyar","Mohammadi","Male","0381236765","09421213445",32l,"32121","ijejewhhe2","27");
        employee2=new Employee("Atena","Shahriari","Female","0021235465","09121403751",47l,"67321","juliiiiiiip","43");
        employee3=new Employee("Bardia","Moghaddam","Male","0381235465","09124456789",19l,"12345","123123131","13");
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    public void reset(){
        output.reset();
        Employee.getAllEmployees().clear();

    }

    @Test
    @DisplayName("sort and Print Testing")
    public void printEmployee(){
        output.reset();
        Employee.printEmployees();
        String expectedOutPut="All Employee: \r\n"+employee1.toString()+"\r\n\r\n"+employee2.toString()+"\r\n\r\n"+employee3.toString()+"\r\n\r\n";
        Assertions.assertEquals(expectedOutPut,output.toString());

        output.reset();
        Employee.sortEmployeesByName();
        Employee.printEmployees();
         expectedOutPut="All Employee: \r\n"+employee2.toString()+"\r\n\r\n"+employee3.toString()+"\r\n\r\n"+employee1.toString()+"\r\n\r\n";
        Assertions.assertEquals(expectedOutPut,output.toString());

        output.reset();
        Employee.sortEmployeesByStoreCode();
        Employee.printEmployees();
        expectedOutPut="All Employee: \r\n"+employee3.toString()+"\r\n\r\n"+employee1.toString()+"\r\n\r\n"+employee2.toString()+"\r\n\r\n";
        Assertions.assertEquals(expectedOutPut,output.toString());


        output.reset();
        Employee.sortEmployeesByNationalID();
        Employee.printEmployees();
        expectedOutPut="All Employee: \r\n"+employee2.toString()+"\r\n\r\n"+employee3.toString()+"\r\n\r\n"+employee1.toString()+"\r\n\r\n";
        Assertions.assertEquals(expectedOutPut,output.toString());
    }

    @Test
    @DisplayName("Employee Search Testing")
    public void searchTest(){
        output.reset();
        int index=Employee.searchInEmployeesByFullName(employee1.getFirstName(),employee1.getLastName());
        Assertions.assertEquals(employee1,Employee.indexToInstance(index));

        index=Employee.searchInEmployeesByFullName(employee2.getFirstName(),employee2.getLastName());
        Assertions.assertEquals(employee2,Employee.indexToInstance(index));

        index=Employee.searchInEmployeesByFullName(employee3.getFirstName(),employee3.getLastName());
        Assertions.assertEquals(employee3,Employee.indexToInstance(index));

        index=Employee.searchInEmployeesByFullName("Reza","Shahsavand");
        Assertions.assertTrue(index<=-1);

        index=Employee.searchInEmployeesByNationalID(employee1.getNationalID());
        Assertions.assertEquals(employee1,Employee.indexToInstance(index));

        index=Employee.searchInEmployeesByNationalID(employee2.getNationalID());
        Assertions.assertEquals(employee2,Employee.indexToInstance(index));

        index=Employee.searchInEmployeesByNationalID(employee3.getNationalID());
        Assertions.assertEquals(employee3,Employee.indexToInstance(index));

        index=Employee.searchInEmployeesByNationalID("0031233657");
        Assertions.assertTrue(index<=-1);

        index=Employee.searchInEmployeesByStoreCode(employee1.getStoreCode());
        Assertions.assertEquals(employee1,Employee.indexToInstance(index));

        index=Employee.searchInEmployeesByStoreCode(employee2.getStoreCode());
        Assertions.assertEquals(employee2,Employee.indexToInstance(index));

        index=Employee.searchInEmployeesByStoreCode(employee3.getStoreCode());
        Assertions.assertEquals(employee3,Employee.indexToInstance(index));

        index=Employee.searchInEmployeesByStoreCode("54322");
        Assertions.assertTrue(index<=-1);





        }






}