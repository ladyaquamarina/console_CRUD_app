package com.Tretyak_Marina.javacore.chapter10.controller;

import com.Tretyak_Marina.javacore.chapter10.model.Label;
import com.Tretyak_Marina.javacore.chapter10.model.Post;
import com.Tretyak_Marina.javacore.chapter10.model.PostStatus;
import com.Tretyak_Marina.javacore.chapter10.model.Writer;
import com.Tretyak_Marina.javacore.chapter10.repository.GsonLabelRepositoryImpl;
import com.Tretyak_Marina.javacore.chapter10.repository.LabelRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LabelController {
    LabelRepository labelRepository = new GsonLabelRepositoryImpl();
    public boolean createLabel(int id, String name) {
        Set<Integer> set = ids();
        if (!set.add(id))
            return false;
        Label label = new Label(id, name);
        return addToJsonLabel(label);
    }
    public Label readLabel(int id) { // just for read and print in console
        Label result = getLabel(id);
        if (!addToJsonLabel(result))
            result = null;
        return result;
    }
    public Label getLabel(int labelId) { // for use in methods that modify objects
        return labelRepository.getById(labelId);
    }
    public List<Label> readAllLabels() { // just for read and print in console
        List<Label> active_posts = getAllLabels();
        try {
            for (Label l : active_posts)
                addToJsonLabel(l);
        } catch (NullPointerException e) {
            System.out.println("Exception: " + e);
            return null;
        }
        return active_posts;
    }
    public List<Label> getAllLabels() { // for use in methods that modify objects
        return labelRepository.readJson();
    }
    public boolean updateLabel(int labelId, int newId) {
        Set<Integer> set = ids();
        Label label = getLabel(labelId);
        try {
            if (!set.add(newId)) {
                addToJsonLabel(label);
                return false;
            }
            label.setId(newId);
        } catch (NullPointerException e) {
            System.out.println("\nThere are no active labels with this ID!\n");
            return false;
        }
        return addToJsonLabel(label);
    }
    public boolean updateLabel(int labelId, String newName) {
        Label label = getLabel(labelId);
        try {
            label.setName(newName);
        } catch (NullPointerException e) {
            System.out.println("\nThere are no active labels with this ID!\n");
            return false;
        }
        return addToJsonLabel(label);
    }
    public boolean updateLabel(int labelId, PostStatus newStatus) {
        Label label = getLabel(labelId);
        try {
            label.setStatus(newStatus);
        } catch (NullPointerException e) {
            System.out.println("\nThere are no active labels with this ID!\n");
            return false;
        }
        return addToJsonLabel(label);
    }
    public boolean deleteLabel(int labelId) {
        try {
            Label label = getLabel(labelId);
            label.setStatus(PostStatus.DELETED);
            addToJsonLabel(label);
        } catch (NullPointerException e) {
            System.out.println("\nThere are no saved labels with this ID!\n");
            return false;
        }
        try {
            WriterController writerController = new WriterController();
            List<Writer> writers = writerController.getAllWriters();
            for (Writer writer : writers) {
                for (Post p : writer.getPosts())
                    for (Label l : p.getLabels())
                        if (l.getId() == labelId)
                            l.setStatus(PostStatus.DELETED);
                writerController.addToJsonWriter(writer);
            }
        } catch (NullPointerException e) {
            // ignore
            // no saved writer
        }
        try {
            PostController postController = new PostController();
            List<Post> posts = postController.getAllPosts();
            for (Post post : posts)
                for (Label l : post.getLabels()) {
                    if (l.getId() == labelId)
                        l.setStatus(PostStatus.DELETED);
                    postController.addToJsonPost(post);
                }
        } catch (NullPointerException e) {
            // ignore
            // no saved posts
        }
        return true;
    }
    public boolean deleteAllLabels() {
        try {
            for (Label l : labelRepository.readJson()) {
                l.setStatus(PostStatus.DELETED);
                addToJsonLabel(l);
            }
        } catch (NullPointerException e) {
            System.out.println("There are no saved labels!\n");
            return false;
        }
        try {
            WriterController writerController = new WriterController();
            List<Writer> writers = writerController.getAllWriters();
            for (Writer writer : writers) {
                for (Post p : writer.getPosts())
                    for (Label l : p.getLabels())
                        l.setStatus(PostStatus.DELETED);
                writerController.addToJsonWriter(writer);
            }
        } catch (NullPointerException e) {
            // ignore
            // no saved writers
        }
        try {
            PostController postController = new PostController();
            List<Post> posts = postController.getAllPosts();
            for (Post post : posts) {
                for (Label l : post.getLabels())
                    l.setStatus(PostStatus.DELETED);
                postController.addToJsonPost(post);
            }
        } catch (NullPointerException e) {
            // ignore
            // no saved posts
        }
        return true;
    }
    public boolean addToJsonLabel(Label label) {
        if (label == null)
            return false;
        return labelRepository.addToJson(label);
    }
    public Set<Integer> ids() {
        List<Label> labels = labelRepository.readJson();
        Set<Integer> result = new HashSet<>();
        try {
            for (Label l : labels) {
                result.add(l.getId());
                addToJsonLabel(l);
            }
        } catch (NullPointerException e) {
            System.out.println("\nThere are no active labels with this ID!\n");
            return null;
        }
        return result;
    }
}
