package com.Tretyak_Marina.javacore.chapter10.controller;

import com.Tretyak_Marina.javacore.chapter10.model.*;
import com.Tretyak_Marina.javacore.chapter10.repository.gson.GsonPostRepositoryImpl;
import com.Tretyak_Marina.javacore.chapter10.repository.PostRepository;

import java.util.List;

public class PostController {
    private final PostRepository postRepository = new GsonPostRepositoryImpl();
    private Post addPost(Post post) {
        return postRepository.add(post);
    }
    public Post createPost(String content) {
        Post post = new Post(content);
        return addPost(post);
    }
    public Post getPost(int postId) {  // for use in methods that modify objects
        return postRepository.getById(postId);
    }
    public List<Post> getAllPosts() {  // for use in methods that modify objects
        return postRepository.getAll();
    }
    public void updatePost(Post post) {
        postRepository.update(post);
    }
    public void updatePost(int postId, String newContent) {
        Post post = getPost(postId);
        try {
            post.setContent(newContent);
            postRepository.update(post);
        } catch (NullPointerException e) {
            System.out.println("There are no posts with this ID!\n");
        }
    }
    public Post updatePost(int postId, PostStatus newStatus) {
        Post post = getPost(postId);
        try {
            post.setStatus(newStatus);
            postRepository.update(post);
        } catch (NullPointerException e) {
            System.out.println("There are no posts with this ID!\n");
            return null;
        }
        return post;
    }
    public void addLabelToPost(int postId, Label label) {
        Post post = getPost(postId);
        try {
            post.addLabel(label);
            postRepository.update(post);
        } catch (NullPointerException e) {
            System.out.println("There are no posts with this ID!\n");
            return;
        }
        try {
            WriterController writerController = new WriterController();
            List<Writer> writers = writerController.getAllWriters();
            for (Writer writer : writers) {
                writer.getPosts().stream().peek(p -> {
                    if (p.getId() == postId) {
                        p.addLabel(label);
                    }
                }).toList();
                writerController.updateWriter(writer);
            }
        } catch (NullPointerException e) {
            // ignore
            // no writers for this post
        }
    }
    public void deleteLabelFromPost(int postId, int labelId) {
        Post post = getPost(postId);
        try {
            post.deleteLabel(labelId);
            postRepository.update(post);
        } catch (NullPointerException e) {
            System.out.println("There are no posts with this ID!\n");
        }
        try {
            WriterController writerController = new WriterController();
            List<Writer> writers = writerController.getAllWriters();
            for (Writer writer : writers) {
                writer.getPosts().stream().peek(p -> {
                    if (p.getId() == postId) {
                        p.deleteLabel(labelId);
                    }
                }).toList();
                writerController.updateWriter(writer);
            }
        } catch (NullPointerException e) {
            // ignore
            // no writers for this post
        }
    }
    public void deleteAllLabelFromPost (int postId) {
        Post post = getPost(postId);
        try {
            post.getLabels().clear();
            postRepository.update(post);
        } catch (NullPointerException e) {
            System.out.println("There are no posts with this ID!\n");
        }
    }
    public void deletePost(int postId) {
        postRepository.deleteById(postId);
        try {
            WriterController writerController = new WriterController();
            List<Writer> writers = writerController.getAllWriters();
            for (Writer writer : writers) {
                writer.getPosts().stream().peek(p -> {
                    if (p.getId() == postId) {
                        p.setStatus(PostStatus.DELETED);
                    }
                }).toList();
                writerController.updateWriter(writer);
            }
        } catch (NullPointerException e) {
            // ignore
            // no active writers
        }
    }
    public void deleteAllPosts() {
        postRepository.deleteAll();
        try {
            WriterController writerController = new WriterController();
            List<Writer> writers = writerController.getAllWriters();
            for (Writer writer : writers) {
                writer.getPosts().stream().peek(p -> p.setStatus(PostStatus.DELETED));
                writerController.updateWriter(writer);
            }
        } catch (NullPointerException e) {
            // ignore
            // no saved writers
        }
    }
}
