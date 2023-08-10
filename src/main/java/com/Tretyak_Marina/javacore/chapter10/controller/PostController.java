package com.Tretyak_Marina.javacore.chapter10.controller;

import com.Tretyak_Marina.javacore.chapter10.model.*;
import com.Tretyak_Marina.javacore.chapter10.repository.PostRepository;

import java.util.List;

public class PostController {
    private final PostRepository postRepository;
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    public Post createPost(String content) {
        if (content.isEmpty())
            return null;
        Post post = new Post(content);
        return postRepository.add(post);
    }
    public Post getPost(long postId) {
        if (postId < 1)
            return null;
        return postRepository.getById(postId);
    }
    public List<Post> getAllPosts() {
        return postRepository.getAll();
    }
    public Post updatePost(long postId, String newContent) {
        if (postId < 1 || newContent.isEmpty())
            return null;
        Post post = getPost(postId);
        try {
            post.setContent(newContent);
            post = postRepository.update(post);
        } catch (NullPointerException e) {
            System.out.println("There are no posts with this ID!\n");
        }
        return post;
    }
    public Post updatePost(long postId, PostStatus newStatus) {
        if (postId < 1)
            return null;
        Post post = getPost(postId);
        try {
            post.setStatus(newStatus);
            post = postRepository.update(post);
        } catch (NullPointerException e) {
            System.out.println("There are no posts with this ID!\n");
        }
        return post;
    }
    public Post addLabelToPost(long postId, Label label) {
        if (postId < 1)
            return null;
        Post post = getPost(postId);
        try {
            post.addLabel(label);
            post = postRepository.update(post);
        } catch (NullPointerException e) {
            System.out.println("There are no posts with this ID!\n");
        }
        return post;
    }
    public Post deleteLabelFromPost(long postId, long labelId) {
        if (postId < 1 || labelId < 1)
            return null;
        Post post = getPost(postId);
        try {
            post.deleteLabel(labelId);
            post = postRepository.update(post);
        } catch (NullPointerException e) {
            System.out.println("There are no posts with this ID!\n");
        }
        return post;
    }
    public Post deleteAllLabelFromPost (long postId) {
        if (postId < 1)
            return null;
        Post post = getPost(postId);
        try {
            post.getLabels().clear();
            post = postRepository.update(post);
        } catch (NullPointerException e) {
            System.out.println("There are no posts with this ID!\n");
        }
        return post;
    }
    public void deletePost(long postId) {
        if (postId < 1)
            return;
        Post post = getPost(postId);
        try {
            postRepository.deleteById(postId);
            post.setStatus(PostStatus.DELETED);
        } catch (NullPointerException e) {
            System.out.println("There are no posts with this ID!\n");
        }
    }
    public void deleteAllPosts() {
        postRepository.deleteAll();
    }
}
