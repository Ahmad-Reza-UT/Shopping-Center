package com.company;

public class Main {

    public static void main(String []args) {
        /* reading form json file */
        ReadAndWrite.read();
        /* main program */
        Menu.menu();
        /* writing on json file */
        ReadAndWrite.write();
    }
}