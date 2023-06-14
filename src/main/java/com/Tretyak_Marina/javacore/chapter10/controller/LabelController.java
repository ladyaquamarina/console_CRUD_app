package com.Tretyak_Marina.javacore.chapter10.controller;

import com.Tretyak_Marina.javacore.chapter10.model.Label;
import com.Tretyak_Marina.javacore.chapter10.model.Post;
import com.Tretyak_Marina.javacore.chapter10.model.PostStatus;
import com.Tretyak_Marina.javacore.chapter10.model.Writer;
import com.Tretyak_Marina.javacore.chapter10.repository.gson.GsonLabelRepositoryImpl;
import com.Tretyak_Marina.javacore.chapter10.repository.LabelRepository;

import java.util.List;

public class LabelController {
    private final LabelRepository labelRepository = new GsonLabelRepositoryImpl();
    private Label addLabel(Label label) {
        return labelRepository.add(label);
    }
    public Label createLabel(String name) {
        Label label = new Label(name);
        return addLabel(label);
    }
    public Label getLabel(int labelId) {
        return labelRepository.getById(labelId);
    }
    public List<Label> getAllLabels() { // for use in methods that modify objects
        return labelRepository.getAll();
    }
    public Label updateLabel(int labelId, String newName) {
        Label label = getLabel(labelId);;
        try {
            label.setName(newName);
            labelRepository.update(label);
        } catch (NullPointerException e) {
            System.out.println("\nThere are no labels with this ID!\n");
            return null;
        }
        return label;
    }
    public Label updateLabel(int labelId, PostStatus newStatus) {
        Label label = getLabel(labelId);;
        try {
            label.setStatus(newStatus);
            labelRepository.update(label);
        } catch (NullPointerException e) {
            System.out.println("\nThere are no labels with this ID!\n");
            return null;
        }
        return label;
    }
    public void deleteLabel(int labelId) {   // доделать
        labelRepository.deleteById(labelId);
        try {
            WriterController writerController = new WriterController();
            List<Writer> writers = writerController.getAllWriters();
            for (Writer writer : writers) {
                for (Post post : writer.getPosts())
                    post.getLabels().stream().peek(l -> {
                        if (l.getId() == labelId) {
                            l.setStatus(PostStatus.DELETED);
                        }
                    }).toList();
                writerController.updateWriter(writer);
            }
        } catch (NullPointerException e) {
            // ignore
            // no saved writer
        }
        try {
            PostController postController = new PostController();
            List<Post> posts = postController.getAllPosts();
            for (Post post : posts) {
                post.getLabels().stream().peek(l -> {
                    if (l.getId() == labelId) {
                        l.setStatus(PostStatus.DELETED);
                    }
                }).toList();
                postController.updatePost(post);
            }
        } catch (NullPointerException e) {
            // ignore
            // no saved posts
        }
    }
    public void deleteAllLabels() {  // доделать
        labelRepository.deleteAll();
        try {
            WriterController writerController = new WriterController();
            List<Writer> writers = writerController.getAllWriters();
            for (Writer writer : writers) {
                for (Post post : writer.getPosts())
                    post.getLabels().stream().peek(l -> l.setStatus(PostStatus.DELETED));
                writerController.updateWriter(writer);
            }
        } catch (NullPointerException e) {
            // ignore
            // no saved writers
        }
        try {
            PostController postController = new PostController();
            List<Post> posts = postController.getAllPosts();
            for (Post post : posts) {
                post.getLabels().stream().peek(l -> l.setStatus(PostStatus.DELETED));
                postController.updatePost(post);
            }
        } catch (NullPointerException e) {
            // ignore
            // no saved posts
        }
    }
}
