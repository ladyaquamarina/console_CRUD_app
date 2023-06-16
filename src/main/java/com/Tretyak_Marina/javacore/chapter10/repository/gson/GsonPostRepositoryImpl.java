package com.Tretyak_Marina.javacore.chapter10.repository.gson;

import com.Tretyak_Marina.javacore.chapter10.model.Post;

import com.Tretyak_Marina.javacore.chapter10.model.PostStatus;
import com.Tretyak_Marina.javacore.chapter10.repository.PostRepository;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GsonPostRepositoryImpl implements PostRepository {
    private final String FILENAME = "src/main/resources/posts.json";
    private final File file = new File(FILENAME);
    private final Gson GSON = new Gson();

    private Integer genetateId(List<Post> posts) {
        return posts.stream().mapToInt(Post::getId).max().orElseGet(() -> 0) + 1;
    }

    private void rewriteJson(List<Post> postsList) {
        try (PrintWriter printWriter = new PrintWriter(FILENAME)) {
            printWriter.write("");
            printWriter.flush();
        } catch (IOException e) {
            System.out.println("Exception: " + e + "\n");
        }
        try (java.io.Writer ioWriter = new FileWriter(FILENAME)) {
            GSON.toJson(postsList, ioWriter);
            ioWriter.flush();
        } catch (JsonSyntaxException | IOException e) {
            System.out.println("Exception: " + e + "\n");
        }
    }

    private List<Post> readJson() {
        List<Post> posts = null;
        try (JsonReader reader = new JsonReader(new FileReader(FILENAME))) {
            Type listPostType = new TypeToken<List<Post>>() {}.getType();
            posts = GSON.fromJson(reader, listPostType);
            if (posts == null)
                posts = new ArrayList<>();
            reader.close();
        } catch (JsonSyntaxException | IOException e) {
            System.out.println("Exception: " + e + "\n");
            return new ArrayList<>();
        }
        return posts;
    }

    @Override
    public Post add(Post post) {
        List<Post> currentPosts = readJson();
        Integer id = genetateId(currentPosts);
        post.setId(id);
        currentPosts.add(post);
        rewriteJson(currentPosts);
        return post;
    }

    @Override
    public Post update(Post post) {
        List<Post> currentPosts = readJson().stream().map(p -> {
            if (Objects.equals(p.getId(), post.getId())) {
                return post;
            }
            return p;
        }).toList();
        rewriteJson(currentPosts);
        return post;
    }

    @Override
    public List<Post> getAll() {
        return readJson();
    }

    public Post getById(Integer id){
        return readJson().stream()
                .filter(p -> Objects.equals(p.getId(), id))
                .findFirst().orElse(null);
    }

    @Override
    public void deleteById(Integer integer) {
        List<Post> currentPosts = readJson().stream().peek(p -> {
            if (Objects.equals(p.getId(), integer)) {
                p.setStatus(PostStatus.DELETED);
            }
        }).toList();
        rewriteJson(currentPosts);
    }

    @Override
    public void deleteAll() {
        List<Post> currentPosts = readJson().stream()
                .peek(p -> p.setStatus(PostStatus.DELETED)).toList();
        rewriteJson(currentPosts);
    }
}
