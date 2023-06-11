package com.Tretyak_Marina.javacore.chapter10.view;


import com.Tretyak_Marina.javacore.chapter10.model.Label;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void ListOfOptions() {
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
                    case 1 -> new WriterView().listOfOptions();
                    case 2 -> new PostView().listOfOptions();
                    case 3 -> new LabelView().listOfOptions();
                    case 4 -> {
                        return;
                    }
                    default -> {
                        System.out.println("Your enter is incorrect!");
                        System.out.println();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to my CRUD app!");
        ListOfOptions();
        System.out.println("See you soon!");

//        Scanner console = new Scanner(System.in);
//        Gson gson = new Gson();
//
//        int li1 = console.nextInt();
//        console.nextLine();
//        String ln1 = console.nextLine();
//        int li2 = console.nextInt();
//        console.nextLine();
//        String ln2 = console.nextLine();
//        Label label1 = new Label(li1, ln1);
//        Label label2 = new Label(li2, ln2);
//        List<Label> labelList = new ArrayList<>();
//        labelList.add(label1);
//        labelList.add(label2);
//
//        java.io.Writer ioWriter = new FileWriter("labels.json");
//        gson.toJson(labelList, ioWriter);
//        ioWriter.flush();
//
//        JsonReader reader = new JsonReader(new FileReader("labels.json"));
//        Type listLabelType = new TypeToken<List<Label>>() {}.getType();
//        List<Label> labels = gson.fromJson(reader, listLabelType);
//        for (Label l : labels) {
//            System.out.println(l.getId());
//            System.out.println(l.getName());
//        }
//
//        PrintWriter printWriter = new PrintWriter("labels.json");
//        printWriter.write("");
//        printWriter.flush();
//        printWriter.close();
    }
}
