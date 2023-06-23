package com.Tretyak_Marina.javacore.chapter10.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainView {
    public void menu() {
        System.out.println("Welcome to my CRUD app!");
        while (true) {
            point:
            {
                Scanner console = new Scanner(System.in);
                System.out.println("What are you want to do? Select an action and enter the corresponding number: ");
                System.out.println("1 - View and edit writers");
                System.out.println("2 - View and edit posts");
                System.out.println("3 - View and edit labels");
                System.out.println("4 - Exit");
                System.out.print("Your number is: ");
                int answer = 0;
                try {
                    answer = console.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("\nYour enter is incorrect!\n");
                    break point;
                }
                console.nextLine();
                System.out.println();
                switch (answer) {
                    case 1 -> new WriterView().menu();
                    case 2 -> new PostView().menu();
                    case 3 -> new LabelView().menu();
                    case 4 -> {
                        System.out.println();
                        System.out.println("See you soon!");
                        return;
                    }
                    default -> {
                        System.out.println("\nYour enter is incorrect!\n");
                    }
                }
            }
        }
    }
}
