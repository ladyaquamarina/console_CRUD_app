package com.Tretyak_Marina.javacore.chapter10.view;

import com.Tretyak_Marina.javacore.chapter10.controller.LabelController;
import com.Tretyak_Marina.javacore.chapter10.model.Label;
import com.Tretyak_Marina.javacore.chapter10.model.PostStatus;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class LabelView {
    LabelController controller = new LabelController();
    public void createLabel() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter the ID of the label being created: ");
        int id = 0;
        try {
            id = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        System.out.print("Enter the name of the label being created: ");
        String name = console.nextLine();
        if (controller.createLabel(id, name))
            System.out.println("New label has been successfully created!\n");
        else
            System.out.println("New label has not been created! Try another ID\n");
    }
    public void createLabel(int id) {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter the name of the label being created: ");
        String name = console.nextLine();
        if (controller.createLabel(id, name))
            System.out.println("New label has been successfully created!\n");
        else
            System.out.println("New label has not been created!\n");
    }
    public void readLabel() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter the ID of the label you are looking for: ");
        int id = 0;
        try {
            id = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        Label label = controller.readLabel(id);
        if (label != null) {
            System.out.println("\nThe label you are looking for:");
            printLabel(label);
            System.out.println();
        }
        else
            System.out.println("\nThe label is not found!\n");
    }
    public void readAllLabels() {
        List<Label> labels = controller.readAllLabels();
        if (labels.size() > 0) {
            System.out.println("All labels:");
            int i = 1;
            for (Label l : labels) {
                System.out.print(i++ + ") ");
                printLabel(l);
                System.out.println();
            }
        }
        else
            System.out.println("\nThere are no saved lavels!\n");
    }
    public void updateLabel() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter ID of the label you want to update: ");
        int id = 0;
        try {
            id = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        if (!controller.updateLabel(id, PostStatus.UNDER_REVIEW))
            return;
        System.out.println("\nSelect the field you want to change: ");
        System.out.println("1 - ID");
        System.out.println("2 - name");
        System.out.println("Your chose is: ");
        int c = 0;
        try {
            c = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            controller.updateLabel(id, PostStatus.ACTIVE);
            return;
        }
        console.nextLine();
        switch (c) {
            case 1 -> {
                System.out.print("\nEnter new ID: ");
                int newId = 0;
                try {
                    newId = console.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("\nYour enter is incorrect!\n");
                    controller.updateLabel(id, PostStatus.ACTIVE);
                    return;
                }
                console.nextLine();
                if (!controller.updateLabel(id, newId)) {
                    System.out.println("\nThe label with this ID already exist!\n");
                    controller.updateLabel(id, PostStatus.ACTIVE);
                } else {
                    System.out.println("\nThe label has been successfully updated!\n");
                    controller.updateLabel(newId, PostStatus.ACTIVE);
                }
            }
            case 2 -> {
                System.out.print("\nEnter new name: ");
                String newName = console.nextLine();
                controller.updateLabel(id, newName);
                System.out.println("\nThe label has been successfully updated!\n");
                controller.updateLabel(id, PostStatus.ACTIVE);
            }
            default -> {
                System.out.println("\nYour enter is incorrect!\n");
                controller.updateLabel(id, PostStatus.ACTIVE);
            }
        }
    }
    public void deleteLabel() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter ID of the label you want to delete: ");
        int id = 0;
        try {
            id = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        System.out.println();
        if (controller.deleteLabel(id))
            System.out.println("\nThe label has been successfully deleted!\n");
    }
    public void deleteAllLabels() {
        if (controller.deleteAllLabels())
            System.out.println("\nAll labels have been successfully deleted!\n");
    }
    private void printLabel(Label l) {
        System.out.println("id: " + l.getId());
        System.out.println("name: " + l.getName());
        System.out.println("Status: " + l.getStatus());
    }
    public void listOfOptions() {
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
                int answer = 0;
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
                        System.out.println();
                        if (y.equals("y"))
                            deleteAllLabels();
                        else
                            System.out.println("Deleting is cancelling!\n");
                    }
                    case 7 -> {
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
