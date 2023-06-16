package com.Tretyak_Marina.javacore.chapter10.repository.gson;

import com.Tretyak_Marina.javacore.chapter10.model.PostStatus;
import com.Tretyak_Marina.javacore.chapter10.model.Writer;

import com.Tretyak_Marina.javacore.chapter10.repository.WriterRepository;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GsonWriterRepositoryImpl implements WriterRepository {
    private final String FILENAME = "src/main/resources/writers.json";
    private final File file = new File(FILENAME);
    private final Gson GSON = new Gson();

    private Integer generateId(List<Writer> writers) {
        return writers.stream().mapToInt(Writer::getId).max().orElseGet(() -> 0) + 1;
    }

    private void rewriteJson(List<Writer> writersList) {
        try (PrintWriter printWriter = new PrintWriter(FILENAME)) {
            printWriter.write("");
            printWriter.flush();
        } catch (IOException e) {
            System.out.println("Exception: " + e + "\n");
        }
        try (java.io.Writer ioWriter = new FileWriter(FILENAME)) {
            GSON.toJson(writersList, ioWriter);
            ioWriter.flush();
        } catch (JsonSyntaxException | IOException e) {
            System.out.println("Exception: " + e + "\n");
        }
    }

    private List<Writer> readJson() {
        List<Writer> writers = null;
        try {
            JsonReader reader = new JsonReader(new FileReader(FILENAME));
            Type listWriterType = new TypeToken<List<Writer>>() {}.getType();
                writers = GSON.fromJson(reader, listWriterType);
            if (writers == null)
                writers = new ArrayList<>();
            reader.close();
        } catch (JsonSyntaxException | IOException e) {
            System.out.println("Exception: " + e + "\n");
            return new ArrayList<>();
        }
        return writers;
    }

    @Override
    public Writer add(Writer writer) {
        List<Writer> currentWriters = readJson();
        Integer id = generateId(currentWriters);
        writer.setId(id);
        currentWriters.add(writer);
        rewriteJson(currentWriters);
        return writer;
    }

    @Override
    public Writer update(Writer writer) {
        List<Writer> currentWriters = readJson().stream().map(w -> {
            if (Objects.equals(w.getId(), writer.getId())) {
                return writer;
            }
            return w;
        }).toList();
        rewriteJson(currentWriters);
        return writer;
    }

    @Override
    public List<Writer> getAll() {
        return readJson();
    }

    public Writer getById(Integer id){
        return readJson().stream()
                .filter(w -> Objects.equals(w.getId(), id))
                .findFirst().orElse(null);
    }

    @Override
    public void deleteById(Integer integer) {
        List<Writer> currentWriters = readJson().stream().peek(w -> {
            if (Objects.equals(w.getId(), integer)) {
                w.setStatus(PostStatus.DELETED);
            }
        }).toList();
        rewriteJson(currentWriters);
    }

    @Override
    public void deleteAll() {
        List<Writer> currentWriters = readJson().stream()
                .peek(w -> w.setStatus(PostStatus.DELETED)).toList();
        rewriteJson(currentWriters);
    }
}
