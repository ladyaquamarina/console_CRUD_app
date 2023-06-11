package com.Tretyak_Marina.javacore.chapter10.repository;

import com.Tretyak_Marina.javacore.chapter10.model.Post;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonPostRepositoryImpl implements PostRepository {
    public boolean addToJson(Post post){
        try {
            Gson gson = new Gson();
            List<Post> posts = readJson();
            posts.add(post);
            PrintWriter printWriter = new PrintWriter(FILENAME);
            printWriter.write("");
            printWriter.flush();
            printWriter.close();
            java.io.Writer ioWriter = new FileWriter(FILENAME);
            gson.toJson(posts, ioWriter);
            ioWriter.flush();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            System.out.println();
            return false;
        }
        return true;
    }

    public boolean rewriteJson(List<Post> postsList) {
        try {
            File file = new File(FILENAME);
            file.createNewFile();
            PrintWriter printWriter = new PrintWriter(FILENAME);
            printWriter.write("");
            printWriter.flush();
            printWriter.close();
            Gson gson = new Gson();
            java.io.Writer ioWriter = new FileWriter(FILENAME);
            gson.toJson(postsList, ioWriter);
            ioWriter.flush();
        } catch (JsonSyntaxException | IOException e) {
            System.out.println("Exception: " + e);
            System.out.println();
            return false;
        }
        return true;
    }

    public List<Post> readJson() {
        List<Post> posts = null;
        try {
            File file = new File(FILENAME);
            file.createNewFile();
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(FILENAME));
            Type listPostType = new TypeToken<List<Post>>() {}.getType();
            posts = gson.fromJson(reader, listPostType);
            if (posts == null)
                posts = new ArrayList<>();
            PrintWriter printWriter = new PrintWriter(FILENAME);
            printWriter.write("");
            printWriter.flush();
            printWriter.close();
        } catch (JsonSyntaxException | IOException e) {
            System.out.println("Exception: " + e);
            System.out.println();
            return null;
        }
        return posts;
    }

    public Post getById(Integer id){
        Post result = null;
        try {
            List<Post> posts = readJson();
            for (Post p : posts)
                if (p.getId() == id) {
                    result = p;
                    posts.remove(p);
                    break;
                }
            rewriteJson(posts);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            System.out.println();
            return null;
        }
        return result;
    }
}
