package com.Tretyak_Marina.javacore.chapter10.controller;

import com.Tretyak_Marina.javacore.chapter10.model.*;
import com.Tretyak_Marina.javacore.chapter10.repository.GsonPostRepositoryImpl;
import com.Tretyak_Marina.javacore.chapter10.repository.PostRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PostController {
    PostRepository postRepository = new GsonPostRepositoryImpl();
    public boolean createPost(int id, String content) {
        Set<Integer> set = ids();
        if (!set.add(id))
            return false;
        Post post = new Post(id, content);
        return addToJsonPost(post);
    }
    public Post readPost(int id) { // just for read and print in console
        Post result = getPost(id);
        if (!addToJsonPost(result))
            result = null;
        return result;
    }
    public Post getPost(int postId) {  // for use in methods that modify objects
        return postRepository.getById(postId);
    }
    public List<Post> readAllPosts() { // just for read and print in console
        List<Post> active_posts = getAllPosts();
        try {
            for (Post p : active_posts)
                addToJsonPost(p);
        } catch (NullPointerException e) {
            System.out.println("There is no saved post!\n");
            return null;
        }
        return active_posts;
    }
    public List<Post> getAllPosts() {  // for use in methods that modify objects
        return postRepository.readJson();
    }
    public boolean updatePost(int postId, int newId) {
        Set<Integer> set = ids();
        Post post = getPost(postId);
        try {
            if (!set.add(postId)) {
                addToJsonPost(post);
                return false;
            }
            post.setId(newId);
        } catch (NullPointerException e) {
            System.out.println("There are no active posts with this ID!\n");
            return false;
        }
        return addToJsonPost(post);
    }
    public boolean updatePost(int postId, String newContent) {
        Post post = getPost(postId);
        try {
            post.setContent(newContent);
        } catch (NullPointerException e) {
            System.out.println("There are no active posts with this ID!\n");
            return false;
        }
        return addToJsonPost(post);
    }
    public boolean updatePost(int postId,PostStatus newStatus) {
        Post post = getPost(postId);
        try {
            post.setStatus(newStatus);
        } catch (NullPointerException e) {
            System.out.println("There are no active posts with this ID!\n");
            return false;
        }
        return addToJsonPost(post);
    }
    public boolean addLabelToPost(int postId, Label label) {
        Post post = getPost(postId);
        try {
            post.addLabel(label);
        } catch (NullPointerException e) {
            System.out.println("There are no active posts with this ID!\n");
            return false;
        }
        return addToJsonPost(post);
    }
    public boolean deleteLabelFromPost(int postId, int labelId) {
        Post post = getPost(postId);
        try {
            post.deleteLabel(labelId);
        } catch (NullPointerException e) {
            System.out.println("There are no active posts with this ID!\n");
            return false;
        }
        return addToJsonPost(post);
    }
    public boolean deleteAllLabelFromPost (int postId) {
        Post post = getPost(postId);
        try {
            post.getLabels().clear();
        } catch (NullPointerException e) {
            System.out.println("There are no active posts with this ID!\n");
            return false;
        }
        return addToJsonPost(post);
    }
    public boolean deletePost(int postId) {
        try {
            Post post = getPost(postId);
            post.setStatus(PostStatus.DELETED);
            addToJsonPost(post);
        } catch (NullPointerException e) {
            System.out.println("There are no saved posts with this ID!\n");
            return false;
        }
        try {
            WriterController writerController = new WriterController();
            List<Writer> writers = writerController.getAllWriters();
            for (Writer writer : writers)
                for (Post p : writer.getPosts()) {
                    if (p.getId() == postId)
                        p.setStatus(PostStatus.DELETED);
                    writerController.addToJsonWriter(writer);
                }
        } catch (NullPointerException e) {
            // ignore
            // no active writers
        }
        return true;
    }
    public boolean deleteAllPosts() {
        try {
            for (Post p : postRepository.readJson()) {
                p.setStatus(PostStatus.DELETED);
                addToJsonPost(p);
            }
        } catch (NullPointerException e) {
            System.out.println("There are no saved posts!\n");
            return false;
        }
        try {
            WriterController writerController = new WriterController();
            List<Writer> writers = writerController.getAllWriters();
            for (Writer writer : writers) {
                for (Post post : writer.getPosts())
                    post.setStatus(PostStatus.DELETED);
                writerController.addToJsonWriter(writer);
            }
        } catch (NullPointerException e) {
            // ignore
            // no saved writers
        }
        return true;
    }
    public boolean addToJsonPost(Post post) {
        if (post == null)
            return false;
        return postRepository.addToJson(post);
    }
    public Set<Integer> ids() {
        List<Post> posts = postRepository.readJson();
        Set<Integer> result = new HashSet<>();
        try {
            for (Post p : posts) {
                result.add(p.getId());
                addToJsonPost(p);
            }
        } catch (NullPointerException e) {
            System.out.println("\nThere are no active labels with this ID!\n");
            return null;
        }
        return result;
    }
}
