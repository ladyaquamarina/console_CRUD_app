package com.Tretyak_Marina.javacore.chapter10.controller;

import com.Tretyak_Marina.javacore.chapter10.model.*;
import com.Tretyak_Marina.javacore.chapter10.repository.gson.GsonWriterRepositoryImpl;
import com.Tretyak_Marina.javacore.chapter10.repository.WriterRepository;

import java.util.List;

public class WriterController {
    private final WriterRepository writerRepository = new GsonWriterRepositoryImpl();
    private Writer addWriter(Writer writer) {
        if (writer == null)
            return null;
        return writerRepository.add(writer);
    }
    public Writer createWriter(String firstName, String lastName) {
        Writer writer = new Writer(firstName, lastName);
        return addWriter(writer);
    }
    public Writer getWriter(int id) { // for use in methods that modify objects
        return writerRepository.getById(id);
    }
    public List<Writer> getAllWriters() {  // for use in methods that modify objects
        return writerRepository.getAll();
    }
    public Writer updateWriter(Writer writer) {
        return writerRepository.update(writer);
    }
    public Writer updateWriter(int writerId, int newId) {
        Writer writer = getWriter(writerId);
        try {
            writer.setId(newId);
            writerRepository.update(writer);
        } catch (NullPointerException e) {
            System.out.println("There are no writers with this ID!\n");
            return null;
        }
        return writer;
    }
    public Writer updateWriter(int writerId, PostStatus newStatus) {
        Writer writer = getWriter(writerId);
        try {
            writer.setStatus(newStatus);
            writerRepository.update(writer);
        } catch (NullPointerException e) {
            System.out.println("There are no writers with this ID!\n");
            return null;
        }
        return writer;
    }
    public Writer updateWriter(int writerId, String newName, String firstOrLastName) {
        Writer writer = getWriter(writerId);
        try {
            if (firstOrLastName.equals("first"))
                writer.setFirstName(newName);
            else
                writer.setLastName(newName);
            writerRepository.update(writer);
        } catch (NullPointerException e) {
            System.out.println("There are no writers with this ID!\n");
            return null;
        }
        return writer;
    }
    public Writer addPostToWriter(int writerId, Post Post) {
        Writer writer = getWriter(writerId);
        try {
            writer.addPost(Post);
            writerRepository.update(writer);
        } catch (NullPointerException e) {
            System.out.println("There are no writers with this ID!\n");
            return null;
        }
        return writer;
    }
    public Writer deletePostFromWriter(int writerId, int postId) {
        Writer writer = getWriter(writerId);
        try {
            writer.deletePost(postId);
            writerRepository.update(writer);
        } catch (NullPointerException e) {
            System.out.println("There are no writers with this ID!\n");
            return null;
        }
        return writer;
    }
    public Writer deleteAllPostFromWriter(int writerId) {
        Writer writer = getWriter(writerId);
        try {
            writer.getPosts().clear();
            writerRepository.update(writer);
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
