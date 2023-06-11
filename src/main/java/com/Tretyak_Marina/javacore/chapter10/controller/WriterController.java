package com.Tretyak_Marina.javacore.chapter10.controller;

import com.Tretyak_Marina.javacore.chapter10.model.*;
import com.Tretyak_Marina.javacore.chapter10.repository.GsonWriterRepositoryImpl;
import com.Tretyak_Marina.javacore.chapter10.repository.WriterRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WriterController {
    WriterRepository writerRepository = new GsonWriterRepositoryImpl();
    public boolean createWriter(int id, String firstName, String lastName) {
        Set<Integer> set = ids();
        if (!set.add(id))
            return false;
        Writer writer = new Writer(id, firstName, lastName);
        return addToJsonWriter(writer);
    }
    public Writer readWriter(int id) { // just for read and print in console
        Writer result = getWriter(id);
        if (!addToJsonWriter(result))
            result = null;
        return result;
    }

    public Writer getWriter(int id) { // for use in methods that modify objects
        return writerRepository.getById(id);
    }
    public List<Writer> readAllWriters() { // just for read and print in console
        List<Writer> active_writers = getAllWriters();
        try {
            for (Writer w : active_writers)
                addToJsonWriter(w);
        } catch (NullPointerException e) {
            System.out.println("There is no saved writer!\n");
            System.out.println();
            return null;
        }
        return active_writers;
    }
    public List<Writer> getAllWriters() {  // for use in methods that modify objects
        return writerRepository.readJson();
    }
    public boolean updateWriter(int writerId, int newId) {
        Set<Integer> set = ids();
        Writer writer = getWriter(writerId);
        try {
            if (!set.add(writerId)) {
                addToJsonWriter(writer);
                return false;
            }
            writer.setId(newId);
        } catch (NullPointerException e) {
            System.out.println("There are no active writers with this ID!\n");
            return false;
        }
        return addToJsonWriter(writer);
    }
    public boolean updateWriter(int writerId, PostStatus newStatus) {
        Writer writer = getWriter(writerId);
        try {
            writer.setStatus(newStatus);
        } catch (NullPointerException e) {
            System.out.println("There are no active writers with this ID!\n");
            return false;
        }
        return addToJsonWriter(writer);
    }
    public boolean updateWriter(int writerId, String newName, String firstOrLastName) {
        Writer writer = getWriter(writerId);
        try {
            if (firstOrLastName.equals("first"))
                writer.setFirstName(newName);
            else
                writer.setLastName(newName);
        } catch (NullPointerException e) {
            System.out.println("There are no active writers with this ID!\n");
            return false;
        }
        return addToJsonWriter(writer);
    }
    public boolean addPostToWriter(int writerId, Post Post) {
        Writer writer = getWriter(writerId);
        try {
            writer.addPost(Post);
        } catch (NullPointerException e) {
            System.out.println("There are no active writers with this ID!\n");
            return false;
        }
        return addToJsonWriter(writer);
    }
    public boolean deletePostFromWriter(int writerId, int postId) {
        Writer writer = getWriter(writerId);
        try {
            writer.deletePost(postId);
        } catch (NullPointerException e) {
            System.out.println("There are no active writers with this ID!\n");
            return false;
        }
        return addToJsonWriter(writer);
    }
    public boolean deleteAllPostFromWriter(int writerId) {
        Writer writer = getWriter(writerId);
        try {
            writer.getPosts().clear();
        } catch (NullPointerException e) {
            System.out.println("There are no active writers with this ID!\n");
            return false;
        }
        return addToJsonWriter(writer);
    }
    public boolean deleteWriter(int writerId) {
        Writer writer = getWriter(writerId);
        try {
            writer.setStatus(PostStatus.DELETED);
        } catch (NullPointerException e) {
            System.out.println("There are no saved writers with this ID!\n");
            return false;
        }
        return addToJsonWriter(writer);
    }
    public boolean deleteAllWriters() {
        try {
            for (Writer w : writerRepository.readJson()) {
                w.setStatus(PostStatus.DELETED);
                addToJsonWriter(w);
            }
        } catch (NullPointerException e) {
            System.out.println("There are no saved writers!\n");
            return false;
        }
        return true;
    }
    public boolean addToJsonWriter(Writer writer) {
        if (writer == null)
            return false;
        return writerRepository.addToJson(writer);
    }
    public Set<Integer> ids() {
        List<Writer> writers = writerRepository.readJson();
        Set<Integer> result = new HashSet<>();
        try {
            for (Writer w : writers) {
                result.add(w.getId());
                addToJsonWriter(w);
            }
        } catch (NullPointerException e) {
            System.out.println("\nThere are no active labels with this ID!\n");
            return null;
        }
        return result;
    }
}
