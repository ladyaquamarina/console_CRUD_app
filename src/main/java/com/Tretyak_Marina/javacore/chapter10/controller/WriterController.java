package com.Tretyak_Marina.javacore.chapter10.controller;

import com.Tretyak_Marina.javacore.chapter10.model.*;
import com.Tretyak_Marina.javacore.chapter10.repository.WriterRepository;

import java.util.List;

public class WriterController {
    private final WriterRepository writerRepository;
    public WriterController(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public Writer createWriter(String firstName, String lastName) {
        if (firstName.isEmpty() || lastName.isEmpty())
            return null;
        Writer writer = new Writer(firstName, lastName);
        return writerRepository.add(writer);
    }

    public Writer getWriter(int id) {
        if (id < 1)
            return null;
        return writerRepository.getById(id);
    }

    public List<Writer> getAllWriters() {
        return writerRepository.getAll();
    }

    public Writer updateWriter(int writerId, String newName, String firstOrLastName) {
        if (writerId < 1 || newName.isEmpty() || firstOrLastName.isEmpty())
            return null;
        Writer writer = getWriter(writerId);
        try {
            if (firstOrLastName.equals("first"))
                writer.setFirstName(newName);
            else
                writer.setLastName(newName);
            writer = writerRepository.update(writer);
        } catch (NullPointerException e) {
            System.out.println("There are no writers with this ID!\n");
            return null;
        }
        return writer;
    }

    public Writer addPostToWriter(int writerId, Post Post) {
        if (writerId < 1)
            return null;
        Writer writer = getWriter(writerId);
        try {
            writer.addPost(Post);
            writer = writerRepository.update(writer);
        } catch (NullPointerException e) {
            System.out.println("There are no writers with this ID!\n");
            return null;
        }
        return writer;
    }

    public Writer deletePostFromWriter(int writerId, int postId) {
        if (writerId < 1 || postId < 1)
            return null;
        Writer writer = getWriter(writerId);
        try {
            writer.deletePost(postId);
            writer = writerRepository.update(writer);
        } catch (NullPointerException e) {
            System.out.println("There are no writers with this ID!\n");
            return null;
        }
        return writer;
    }

    public Writer deleteAllPostFromWriter(int writerId) {
        if (writerId < 1)
            return null;
        Writer writer = getWriter(writerId);
        try {
            writer.getPosts().clear();
            writer = writerRepository.update(writer);
        } catch (NullPointerException e) {
            System.out.println("There are no writers with this ID!\n");
            return null;
        }
        return writer;
    }

    public void deleteWriter(int writerId) {
        writerRepository.deleteById(writerId);
    }

    public void deleteAllWriters() {
        writerRepository.deleteAll();
    }
}
