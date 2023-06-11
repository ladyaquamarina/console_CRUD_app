package com.Tretyak_Marina.javacore.chapter10.view;

import com.Tretyak_Marina.javacore.chapter10.controller.LabelController;
import com.Tretyak_Marina.javacore.chapter10.controller.PostController;
import com.Tretyak_Marina.javacore.chapter10.model.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PostView {
    PostController controller = new PostController();
    public void createPost() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter the ID of the post being created: ");
        int id = 0;
        try {
            id = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        System.out.print("Enter the content of the post being created: ");
        String content = console.nextLine();
        if (controller.createPost(id, content))
            System.out.println("New post has been successfully created!\n");
        else
            System.out.println("New post has not been created. Try another ID\n");
    }
    public void createPost(int id) {
        Scanner console = new Scanner(System.in);
        System.out.print("\nEnter the content of the post being created: ");
        String content = console.nextLine();
        if (controller.createPost(id, content))
            System.out.println("New post has been successfully created!\n");
        else
            System.out.println("New post has not been created. Try another ID\n");
    }
    public void readPost() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter the ID of the post you are looking for: ");
        int id = 0;
        try {
            id = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        Post post = controller.readPost(id);
        if (post != null) {
            System.out.println("\nThe post you are looking for:");
            printPost(post);
            System.out.println();
        }
        else
            System.out.println("\nThe post is not found\n");
    }
    public void readAllPosts() {
        List<Post> posts = controller.readAllPosts();
        if (posts.size() > 0) {
            System.out.println("All posts:");
            int i = 1;
            for (Post p : posts) {
                System.out.print(i++ + ") ");
                printPost(p);
            }
        }
        else
            System.out.println("\nThere are no saved posts!\n");
    }
    public void updatePost() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter ID of the post you want to update: ");
        int id = 0;
        try {
            id = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        if (!controller.updatePost(id, PostStatus.UNDER_REVIEW))
            return;
        System.out.println("\nSelect the field you want to change: ");
        System.out.println("1 - ID");
        System.out.println("2 - content");
        System.out.println("Your chose is: ");
        int c = 0;
        try {
            c = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            controller.updatePost(id, PostStatus.ACTIVE);
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
                    controller.updatePost(id, PostStatus.ACTIVE);
                    return;
                }
                console.nextLine();
                if (!controller.updatePost(id, newId)) {
                    System.out.println("\nThe post with this ID already exist\n");
                    controller.updatePost(id, PostStatus.ACTIVE);
                } else {
                    System.out.println("\nThe post has been successfully updated!\n");
                    controller.updatePost(newId, PostStatus.ACTIVE);
                }
            }
            case 2 -> {
                System.out.print("\nEnter new name: ");
                String newContent = console.nextLine();
                controller.updatePost(id, newContent);
                controller.updatePost(id, PostStatus.ACTIVE);
                System.out.println("\nThe post has been successfully updated!\n");
            }
            default -> {
                System.out.println("\nYour enter is incorrect!\n");
                controller.updatePost(id, PostStatus.ACTIVE);
                System.out.println("\nThe post has been successfully updated!\n");
            }
        }
    }
    public void addLabelToPost(){
        Scanner console = new Scanner(System.in);
        System.out.print("Enter ID of the post to which you want to add the label: ");
        int postId = 0;
        try {
            postId = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        if (!controller.updatePost(postId, PostStatus.UNDER_REVIEW))
            return;
        System.out.println("\nDo you want to create new label (1) or choose the existing one (2)? Print '1' or '2'");
        System.out.print("Your answer: ");
        int answer = 0;
        try {
            answer = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            controller.updatePost(postId, PostStatus.ACTIVE);
            return;
        }
        console.nextLine();
        Label label = null;
        LabelController lc = new LabelController();
        switch (answer) {
            case 1 -> {
                LabelView lw = new LabelView();
                System.out.print("\nEnter the ID of the label being created: ");
                int id = 0;
                try {
                    id = console.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("\nYour enter is incorrect!\n");
                    controller.updatePost(postId, PostStatus.ACTIVE);
                    return;
                }
                console.nextLine();
                lw.createLabel(id);
                label = lc.readLabel(id);
            }
            case 2 -> {
                System.out.print("\nEnter ID of the label you want to add to the post: ");
                int labelId = 0;
                try {
                    labelId = console.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("\nYour enter is incorrect!\n");
                    controller.updatePost(postId, PostStatus.ACTIVE);
                    return;
                }
                console.nextLine();
                label = lc.readLabel(labelId);
            }
            default -> {
                controller.updatePost(postId, PostStatus.ACTIVE);
                System.out.println("\nYour enter is incorrect!\n");
                return;
            }
        }
        controller.addLabelToPost(postId, label);
        controller.updatePost(postId, PostStatus.ACTIVE);
        System.out.println("The label has been successfully added to the post!");
    }
    public void deleteLabelFromPost() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter ID of the post from which you want to delete the label: ");
        int postId = 0;
        try {
            postId = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        if (!controller.updatePost(postId, PostStatus.UNDER_REVIEW))
            return;
        printListLabels(controller.getPost(postId).getLabels());
        System.out.println();
        int s = controller.getPost(postId).getLabels().size();
        if (s < 1)
            return;
        System.out.println("Label under which number in the list do you want to delete from the post?");
        System.out.print("Enter number: ");
        int num = 0;
        try {
            num = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            controller.updatePost(postId, PostStatus.ACTIVE);
            return;
        }
        console.nextLine();
        if ((num <= 0) || (num > s)) {
            System.out.println("\nYour enter is incorrect!\n");
            controller.updatePost(postId, PostStatus.ACTIVE);
            return;
        }
        int labelId = controller.getPost(postId).getLabels().get(num - 1).getId();
        if (controller.deleteLabelFromPost(postId, labelId))
            System.out.println("\nThe label has been successfully deleted from the post!\n");
        else
            System.out.println("\nThe label has not been deleted from this post\n");
        controller.updatePost(postId, PostStatus.ACTIVE);
    }
    public void deleteAllLabelsFromPost() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter ID of the post from which you want to delete all labels: ");
        int id = 0;
        try {
            id = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        if (!controller.updatePost(id, PostStatus.UNDER_REVIEW))
            return;
        console.nextLine();
        if (controller.deleteAllLabelFromPost(id))
            System.out.println("\nAll labels have been successfully deleted from this post!");
        else
            System.out.println("\nLabels have not been deleted from this post");
        controller.updatePost(id, PostStatus.ACTIVE);
    }
    public void deletePost() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter ID of the post you want to delete: ");
        int id = 0;
        try {
            id = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nYour enter is incorrect!\n");
            return;
        }
        console.nextLine();
        System.out.println();
        if (controller.deletePost(id))
            System.out.println("The post has been successfully deleted!\n");
    }
    public void deleteAllPosts() {
        if (controller.deleteAllPosts())
            System.out.println("\nAll posts have been successfully deleted!\n");
    }
    public void printPost(Post p) {
        System.out.println("id: " + p.getId());
        System.out.println("content: " + p.getContent());
        System.out.println("created: " + p.getCreated());
        System.out.println("updated: " + p.getUpdated());
        System.out.println("Status: " + p.getStatus());
        printListLabels(p.getLabels());
    }
    public void printListLabels(List<Label> labels) {
        if (labels.size() < 1) {
            System.out.println("This post has no labels");
            return;
        }
        System.out.println("Labels owned by this post: ");
        int i = 1;
        for (Label l : labels)
            System.out.println(i++ + ".    [id: " + l.getId() + ", name: " + l.getName() + "]");
    }
    public void listOfOptions() {
        while (true) {
            point:
            {
                Scanner console = new Scanner(System.in);
                System.out.println("What are you want to do with posts?");
                System.out.println("1 - Create the post");
                System.out.println("2 - Show the post");
                System.out.println("3 - Show all exciting posts");
                System.out.println("4 - Update the post");
                System.out.println("5 - Add label to the post");
                System.out.println("6 - Delete label from the post");
                System.out.println("7 - Delete all labels from the post");
                System.out.println("8 - Delete the post");
                System.out.println("9 - Delete all posts");
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
                    case 1 -> createPost();
                    case 2 -> readPost();
                    case 3 -> readAllPosts();
                    case 4 -> updatePost();
                    case 5 -> addLabelToPost();
                    case 6 -> deleteLabelFromPost();
                    case 7 -> deleteAllLabelsFromPost();
                    case 8 -> deletePost();
                    case 9 -> {
                        System.out.println("\nAll posts will be deleted! Are you sure?");
                        System.out.print("Enter 'y' to delete all posts:");
                        String y = console.nextLine();
                        System.out.println();
                        if (y.equals("y"))
                            deleteAllPosts();
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
