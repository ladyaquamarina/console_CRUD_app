package com.Tretyak_Marina.javacore.chapter10.view;

public class Main {
    private final static MainView menu = new MainView();
    public static void main(String[] args) {
        System.out.println("Welcome to my CRUD app!");
        menu.menu();
        System.out.println("See you soon!");
    }
}
