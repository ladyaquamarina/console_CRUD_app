package com.Tretyak_Marina.javacore.chapter10.view;

import com.Tretyak_Marina.javacore.chapter10.controller.LabelController;
import com.Tretyak_Marina.javacore.chapter10.model.Label;
import com.Tretyak_Marina.javacore.chapter10.repository.jdbc.JdbcLabelRepositoryImpl;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class LabelView {
    private final LabelController controller = new LabelController(new JdbcLabelRepositoryImpl());
    public void createLabel() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter the name of the label being created: ");
        String name = console.nextLine();
        if (controller.createLabel(name) == null)
            System.out.println("Label create failed!\n");
        else
            System.out.println("New label has been successfully created!\n");
    }
    public void readLabel() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter the ID of the label you are looking for: ");
        int id;
        try {
            id = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        Label label = controller.getLabel(id);
        if (label != null) {
            System.out.println("\nThe label you are looking for:");
            printLabel(label);
            System.out.println();
        }
        else
            System.out.println("\nThe label is not found!\n");
    }
    public void readAllLabels() {
        List<Label> labels = controller.getAllLabels();
        if (labels.size() > 0) {
            System.out.println("All labels:\n");
            int i = 1;
            for (Label l : labels) {
                System.out.print(i++ + ") ");
                printLabel(l);
                System.out.println();
            }
        }
        else
            System.out.println("There are no saved labels!\n");
    }
    public void updateLabel() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter ID of the label you want to update: ");
        int id;
        try {
            id = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        if (controller.getLabel(id) == null) {
            System.out.println("\nThe label is not found!\n");
            return;
        }
        System.out.print("Enter new name: ");
        String newName = console.nextLine();
        if (controller.updateLabel(id, newName) == null)
            System.out.println("\nLabel update failed!\n");
        else
            System.out.println("\nThe label has been successfully updated!\n");
    }
    public void deleteLabel() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter ID of the label you want to delete: ");
        int id;
        try {
            id = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        if (controller.getLabel(id) == null) {
            System.out.println("\nThere are no labels with this ID!\n");
            return;
        }
        controller.deleteLabel(id);
        System.out.println("\nThe label has been successfully deleted!\n");
    }
    public void deleteAllLabels() {
        controller.deleteAllLabels();
        System.out.println("\nAll labels have been successfully deleted!\n");
    }
    private void printLabel(Label l) {
        System.out.println("id: " + l.getId());
        System.out.println("name: " + l.getName());
    }
    public void menu() {
        while (true) {
            point:
            {
                Scanner console = new Scanner(System.in);
                System.out.println("What are you want to do with labels?");
                System.out.println("1 - Create the label");
                System.out.println("2 - Show the label");
                System.out.println("3 - Show all exciting labels");
                System.out.println("4 - Update the label");
                System.out.println("5 - Delete the label");
                System.out.println("6 - Delete all labels");
                System.out.println("7 - Exit");
                System.out.print("Your number is: ");
                int answer;
                try {
                    answer = console.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("\nYour enter is incorrect!\n");
                    break point;
                }
                console.nextLine();
                switch (answer) {
                    case 1 -> createLabel();
                    case 2 -> readLabel();
                    case 3 -> readAllLabels();
                    case 4 -> updateLabel();
                    case 5 -> deleteLabel();
                    case 6 -> {
                        System.out.println("\nAll labels will be deleted! Are you sure?");
                        System.out.print("Enter 'y' to delete all labels:");
                        String y = console.nextLine();
                        if (y.equals("y"))
                            deleteAllLabels();
                        else
                            System.out.println("\nDeleting was canceled!\n");
                    }
                    case 7 -> {
                        System.out.println();
                        return;
                    }
                    default -> System.out.println("\nYour enter is incorrect!\n");
                }
            }
        }
    }
}
