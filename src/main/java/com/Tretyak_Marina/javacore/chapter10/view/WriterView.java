package com.Tretyak_Marina.javacore.chapter10.view;

import com.Tretyak_Marina.javacore.chapter10.controller.PostController;
import com.Tretyak_Marina.javacore.chapter10.controller.WriterController;
import com.Tretyak_Marina.javacore.chapter10.model.*;
import com.Tretyak_Marina.javacore.chapter10.repository.hibernate.HibernatePostRepositoryImpl;
import com.Tretyak_Marina.javacore.chapter10.repository.hibernate.HibernateWriterRepositoryImpl;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class WriterView {
    private final WriterController controller = new WriterController(new HibernateWriterRepositoryImpl());
    public void createWriter() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter first name of the writer being created: ");
        String firstName = console.nextLine();
        System.out.print("Enter last name of the writer being created: ");
        String lastName = console.nextLine();
        System.out.println();
        controller.createWriter(firstName, lastName);
        System.out.println("\nThe writer has been successfully created!\n");
    }
    public void readWriter() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter ID of the writer you are looking for: ");
        long id;
        try {
            id = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        Writer writer = controller.getWriter(id);
        if (writer != null) {
            System.out.println("\nThe writer you are looking for: ");
            printWriter(writer);
            System.out.println();
        }
        else
            System.out.println("\nThe writer is not found\n");
    }
    public void readAllWriters() {
        List<Writer> writers = controller.getAllWriters();
        if (writers.size() > 0) {
            System.out.println("All writers:\n ");
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
        long id;
        try {
            id = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        if (controller.getWriter(id) == null) {
            System.out.println("\nThere are no writers with this ID!\n");
            return;
        }
        System.out.println("Select the field you want to change: ");
        System.out.println("1 - first name");
        System.out.println("2 - last name");
        System.out.print("Your chose is: ");
        int c;
        try {
            c = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        switch (c) {
            case 1 -> {
                System.out.print("Enter new first name: ");
                String newFirstName = console.nextLine();
                controller.updateWriter(id, newFirstName, "first");
                System.out.println("\nThe writer has been successfully updated!\n");
            }
            case 2 -> {
                System.out.print("Enter new last name: ");
                String newLastName = console.nextLine();
                controller.updateWriter(id, newLastName, "last");
                System.out.println("\nThe writer has been successfully updated!\n");
            }
            default -> System.out.println("\nYour enter is incorrect!\n");
        }
    }
    public void addPostToWriter() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter ID of the writer to which you want to add the post: ");
        long writerId;
        try {
            writerId = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        if (controller.getWriter(writerId) == null) {
            System.out.println("\nThere are no writers with this ID!\n");
            return;
        }
        System.out.println("Do you want to create new post (1) or choose the existing one (2)? Print '1' or '2'");
        System.out.print("Your answer: ");
        int answer;
        try {
            answer = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        Post post;
        PostController pc = new PostController(new HibernatePostRepositoryImpl());
        switch (answer) {
            case 1 -> {
                System.out.print("Enter the content of the post being created: ");
                String content = console.nextLine();
                post = pc.createPost(content);
            }
            case 2 -> {
                System.out.print("\nEnter ID of the post you want to add to the writer: ");
                long postId;
                try {
                    postId = console.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("\nYour enter is incorrect!\n");
                    return;
                }
                console.nextLine();
                post = pc.getPost(postId);
                if (post == null) {
                    System.out.println("\nThe post not found\n");
                    return;
                }
            }
            default -> {
                System.out.println("\nYour enter is incorrect!\n");
                return;
            }
        }
        controller.addPostToWriter(writerId, post);
        System.out.println("\nThe label has been successfully added to the post!\n");
    }
    public void deletePostFromWriter() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter ID of the writer from which you want to delete the post: ");
        long writerId;
        try {
            writerId = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        if (controller.getWriter(writerId) == null) {
            System.out.println("\nThere are no writers with this ID!\n");
            return;
        }
        List<Post> postsOfThisWriter = controller.getWriter(writerId).getPosts();
        printListPost(postsOfThisWriter);
        System.out.println();
        if (postsOfThisWriter.size() < 1)
            return;
        System.out.println("Post under which number in the list do you want to delete from the writer?");
        System.out.print("Enter number: ");
        int num;
        try {
            num = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        if ((num <= 0) || (num > postsOfThisWriter.size())) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        long postId = controller.getWriter(writerId).getPosts().get(num - 1).getId();
        controller.deletePostFromWriter(writerId, postId);
        System.out.println("\nThe post has been successfully deleted from the post!\n");
    }
    public void deleteAllPostsFromWriter() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter ID of the writer from which you want to delete all posts: ");
        long id;
        try {
            id = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        if (controller.getWriter(id) == null) {
            System.out.println("\nThere are no writers with this ID!\n");
            return;
        }
        controller.deleteAllPostFromWriter(id);
        System.out.println("\nAll posts have been successfully deleted from this writer!");
    }
    public void deleteWriter() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter ID of the writer you want to delete: ");
        long id;
        try {
            id = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        if (controller.getWriter(id) == null) {
            System.out.println("\nThere are no writers with this ID!\n");
            return;
        }
        controller.deleteWriter(id);
        System.out.println("\nThe writer has been successfully deleted!\n");
    }
    public void deleteAllWriters() {
        controller.deleteAllWriters();
        System.out.println("\nAll writers have been successfully deleted!\n");
    }
    public void printWriter(Writer w) {
        System.out.println("id: " + w.getId());
        System.out.println("first name: " + w.getFirstName());
        System.out.println("last name: " + w.getLastName());
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
    public void menu() {
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
                int answer;
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
                        if (y.equals("y"))
                            deleteAllWriters();
                        else
                            System.out.println("\nDeleting was canceled!\n");
                        System.out.println();
                    }
                    case 10 -> {
                        System.out.println();
                        return;
                    }
                    default -> System.out.println("\nYour enter is incorrect!\n");
                }
            }
        }
    }
}
