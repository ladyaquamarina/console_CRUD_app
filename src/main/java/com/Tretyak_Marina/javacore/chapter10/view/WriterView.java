package com.Tretyak_Marina.javacore.chapter10.view;

import com.Tretyak_Marina.javacore.chapter10.controller.PostController;
import com.Tretyak_Marina.javacore.chapter10.controller.WriterController;
import com.Tretyak_Marina.javacore.chapter10.model.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class WriterView {
    WriterController controller = new WriterController();
    public void createWriter() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter ID of the writer being created: ");
        int id = 0;
        try {
            id = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        System.out.print("Enter first name of the writer being created: ");
        String firstName = console.nextLine();
        System.out.print("Enter last name of the writer being created: ");
        String lastName = console.nextLine();
        System.out.println();
        if (controller.createWriter(id, firstName, lastName))
            System.out.println("The writer has been successfully created!\n");
        else System.out.println("New writer has not been created. Try another ID\n");
    }
    public void readWriter() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter ID of the writer you are looking for: ");
        int id = 0;
        try {
            id = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        Writer writer = controller.readWriter(id);
        if (writer != null) {
            System.out.println("\nThe writer you are looking for: ");
            printWriter(writer);
            System.out.println();
        }
        else
            System.out.println("\nThe writer is not found\n");
    }
    public void readAllWriters() {
        List<Writer> writers = controller.readAllWriters();
        if (writers.size() > 0) {
            System.out.println("All writers: ");
            int i = 1;
            for (Writer w : writers) {
                System.out.print(i++ + ") ");
                printWriter(w);
                System.out.println();
            }
        }
        else
            System.out.println("\nThere sre no saved writers!\n");
    }
    public void updateWriter() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter ID of the writer you want to update: ");
        int id = 0;
        try {
            id = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        if (!controller.updateWriter(id, PostStatus.UNDER_REVIEW))
            return;
        System.out.println("Select the field you want to change: ");
        System.out.println("1 - ID");
        System.out.println("2 - first name");
        System.out.println("3 - last name");
        System.out.println("Your chose is: ");
        int c = 0;
        try {
            c = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            controller.updateWriter(id, PostStatus.ACTIVE);
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
                    controller.updateWriter(id, PostStatus.ACTIVE);
                    return;
                }
                console.nextLine();
                if (!controller.updateWriter(id, newId)) {
                    System.out.println("\nThe writer with this ID already exist!\n");
                    controller.updateWriter(id, PostStatus.ACTIVE);
                }
                else {
                    System.out.println("\nThe writer has been successfully updated!\n");
                    controller.updateWriter(newId, PostStatus.ACTIVE);
                }
            }
            case 2 -> {
                System.out.print("\nEnter new first name: ");
                String newFirstName = console.nextLine();
                controller.updateWriter(id, newFirstName, "first");
                controller.updateWriter(id, PostStatus.ACTIVE);
                System.out.println("\nThe writer has been successfully updated!\n");
            }
            case 3 -> {
                System.out.print("\nEnter new last name: ");
                String newLastName = console.nextLine();
                controller.updateWriter(id, newLastName, "last");
                controller.updateWriter(id, PostStatus.ACTIVE);
                System.out.println("\nThe writer has been successfully updated!\n");
            }
            default -> {
                System.out.println("\nYour enter is incorrect!\n");
                controller.updateWriter(id, PostStatus.ACTIVE);
            }
        }
    }
    public void addPostToWriter() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter ID of the writer to which you want to add the post: ");
        int writerId = 0;
        try {
            writerId = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        if (controller.updateWriter(writerId, PostStatus.UNDER_REVIEW))
            return;
        System.out.println("\nDo you want to create new post (1) or choose the existing one (2)? Print '1' or '2'");
        System.out.print("Your answer: ");
        int answer = 0;
        try {
            answer = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            controller.updateWriter(writerId, PostStatus.ACTIVE);
            return;
        }
        console.nextLine();
        Post post = null;
        PostController pc = new PostController();
        switch (answer) {
            case 1 -> {
                PostView pw = new PostView();
                System.out.print("\nEnter the ID of the post being created: ");
                int id = 0;
                try {
                    id = console.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("\nYour enter is incorrect!\n");
                    controller.updateWriter(writerId, PostStatus.ACTIVE);
                    return;
                }
                console.nextLine();
                pw.createPost(id);
                post = pc.readPost(id);
            }
            case 2 -> {
                System.out.print("\nEnter ID of the post you want to add to the writer: ");
                int postId = 0;
                try {
                    postId = console.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("\nYour enter is incorrect!\n");
                    controller.updateWriter(writerId, PostStatus.ACTIVE);
                    return;
                }
                console.nextLine();
                post = pc.readPost(postId);
            }
            default -> {
                controller.updateWriter(writerId, PostStatus.ACTIVE);
                System.out.println("\nYour enter is incorrect!\n");
                return;
            }
        }
        controller.addPostToWriter(writerId, post);
        controller.updateWriter(writerId, PostStatus.ACTIVE);
        System.out.println("\nThe label has been successfully added to the post!");
    }
    public void deletePostFromWriter() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter ID of the writer from which you want to delete the post: ");
        int writerId = 0;
        try {
            writerId = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        if (controller.updateWriter(writerId, PostStatus.UNDER_REVIEW))
            return;
        printListPost(controller.getWriter(writerId).getPosts());
        System.out.println();
        int s = controller.getWriter(writerId).getPosts().size();
        if (s < 1)
            return;
        System.out.println("Post under which number in the list do you want to delete from the writer?");
        System.out.print("Enter number: ");
        int num = 0;
        try {
            num = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        if ((num <= 0) || (num > s)) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        int postId = controller.getWriter(writerId).getPosts().get(num - 1).getId();
        if (controller.deletePostFromWriter(writerId, postId))
            System.out.println("\nThe post has been successfully deleted from the post!\n");
        else
            System.out.println("\nThe post has not been deleted from this writer\n");

        controller.updateWriter(writerId, PostStatus.ACTIVE);
    }
    public void deleteAllPostsFromWriter() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter ID of the writer from which you want to delete all posts: ");
        int id = 0;
        try {
            id = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        if (controller.deleteAllPostFromWriter(id))
            System.out.println("\nAll posts have been successfully deleted from this writer!");
        else
            System.out.println("\nPosts have not been deleted from this writer");
    }
    public void deleteWriter() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter ID of the writer you want to delete: ");
        int id = 0;
        try {
            id = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        controller.deleteWriter(id);
        System.out.println("\nThe writer has been successfully deleted!\n");
    }
    public void deleteAllWriters() {
        if (controller.deleteAllWriters())
            System.out.println("\nAll writers have been successfully deleted!\n");
    }
    public void printWriter(Writer w) {
        System.out.println("id: " + w.getId());
        System.out.println("first name: " + w.getFirstName());
        System.out.println("last name: " + w.getLastName());
        System.out.println("Status: " + w.getStatus());
        printListPost(w.getPosts());
    }
    public void printListPost(List<Post> posts) {
        if (posts.size() < 1) {
            System.out.println("This writer has no posts");
            return;
        }
        System.out.println("Post owned by this writer: ");
        int i = 1;
        for (Post p : posts)
            System.out.println(i++ + ".    [id: " + p.getId() + ", content: " + p.getContent() + "]");
    }
    public void listOfOptions() {
        while (true) {
            point:
            {
                Scanner console = new Scanner(System.in);
                System.out.println("What are you want to do with writers?");
                System.out.println("1 - Create the writer");
                System.out.println("2 - Show the writer");
                System.out.println("3 - Show all exciting writers");
                System.out.println("4 - Update the writer");
                System.out.println("5 - Add post to the writer");
                System.out.println("6 - Delete post from the writer");
                System.out.println("7 - Delete all post from the writer");
                System.out.println("8 - Delete the writer");
                System.out.println("9 - Delete all writers");
                System.out.println("10 - Exit");
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
                    case 1 -> createWriter();
                    case 2 -> readWriter();
                    case 3 -> readAllWriters();
                    case 4 -> updateWriter();
                    case 5 -> addPostToWriter();
                    case 6 -> deletePostFromWriter();
                    case 7 -> deleteAllPostsFromWriter();
                    case 8 -> deleteWriter();
                    case 9 -> {
                        System.out.println("\nAll writers will be deleted! Are you sure?");
                        System.out.print("Enter 'y' to delete all writers:");
                        String y = console.nextLine();
                        System.out.println();
                        if (y.equals("y"))
                            deleteAllWriters();
                        else
                            System.out.println("Deleting is cancelling!\n");
                        System.out.println();
                    }
                    case 10 -> {
                        return;
                    }
                    default -> System.out.println("\nYour enter is incorrect!\n");
                }
            }
        }
    }
}
