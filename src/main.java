

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


public class main {

    private static Scanner s = new Scanner(System.in);
    private static bankmanagement mybank =  new bankmanagement();
    public static void main(String args[]) //main class of bank
            throws IOException {

        boolean quit = false;


                System.out.println("Welcome to Krishna Bank");
                System.out.println("________________________");

                while(!quit) {
                    System.out.println("***************");
                    System.out.println("Press 0 to exit");
                    System.out.println("press 1 to login");
                    System.out.println("press 2 to signup");
                    System.out.println("*****************");
                    System.out.println("Enter the choice: ");
                    int choice = s.nextInt();
                    s.nextLine();
                        switch (choice){
                            case 0:
                                quit = true;
                                System.out.println("Quitting.......");
                                break;
                            case 1:
                                login();
                                break;
                            case 2:
                                signup();
                                break;
                        }

                    }

    }


    public static void login(){
        try {
            System.out.println("Enter your Email: ");
            String getName = s.nextLine();
            System.out.println("Enter your Password: ");
            String getPassword = s.nextLine();
            mybank.FindLogin(getName, getPassword);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Failed to login");
        }

    }

    public static void signup() {
        try {
            System.out.println("Register your Email: ");
            String getName = s.nextLine();
            System.out.println("Register your Password: ");
            String getPassword = s.nextLine();
            mybank.RegisterAcc(getName,getPassword);
        }

        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Failed to signup");

            }

        }

    }


