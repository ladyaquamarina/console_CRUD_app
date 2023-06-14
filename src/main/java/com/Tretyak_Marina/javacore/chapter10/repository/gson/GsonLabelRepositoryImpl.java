package com.Tretyak_Marina.javacore.chapter10.repository.gson;

import com.Tretyak_Marina.javacore.chapter10.model.Label;

import com.Tretyak_Marina.javacore.chapter10.model.PostStatus;
import com.Tretyak_Marina.javacore.chapter10.repository.LabelRepository;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GsonLabelRepositoryImpl implements LabelRepository {
    private final String FILENAME = "src/main/resources/labels.json";
    private final File file = new File(FILENAME);
    private final Gson GSON = new Gson();

    private Integer generateId(List<Label> labels) {
        return labels.stream().mapToInt(Label::getId).max().orElseGet(() -> 0) + 1;
    }

    private void rewriteJson(List<Label> labelsList) {
        try {
            PrintWriter printWriter = new PrintWriter(FILENAME);
            printWriter.write("");
            printWriter.flush();
            printWriter.close();
            java.io.Writer ioWriter = new FileWriter(FILENAME);
            GSON.toJson(labelsList, ioWriter);
            ioWriter.flush();
            ioWriter.close();
        } catch (JsonSyntaxException | IOException e) {
            System.out.println("Exception: " + e + "\n");
        }
    }
    private List<Label> readJson() {
        List<Label> labels = null;
        try {
            JsonReader reader = new JsonReader(new FileReader(FILENAME));
            Type listLabelType = new TypeToken<List<Label>>() {}.getType();
            labels = GSON.fromJson(reader, listLabelType);
            if (labels == null)
                labels = new ArrayList<>();
            reader.close();
        } catch (JsonSyntaxException | IOException e) {
            System.out.println("Exception: " + e + "\n");
            return new ArrayList<>();
        }
        return labels;
    }
    @Override
    public Label add(Label label) {
        List<Label> currentLabels = readJson();
        Integer id = generateId(currentLabels);
        label.setId(id);
        currentLabels.add(label);
        rewriteJson(currentLabels);
        return label;
    }

    @Override
    public Label update(Label label) {
        List<Label> currentLabels = readJson().stream().map(l -> {
            if (Objects.equals(l.getId(), label.getId())) {
                return label;
            }
            return l;
        }).toList();
        rewriteJson(currentLabels);
        return label;
    }

    @Override
    public List<Label> getAll() {
        return readJson();
    }
    @Override
    public Label getById(Integer id) {
        return readJson().stream()
                .filter(l -> Objects.equals(l.getId(), id))
                .findFirst().orElse(null);
    }

    @Override
    public void deleteById(Integer integer) {
        List<Label> currentLabels = readJson().stream().peek(l -> {
            if (Objects.equals(l.getId(), integer)) {
                l.setStatus(PostStatus.DELETED);
            }
        }).toList();
        rewriteJson(currentLabels);
    }

    @Override
    public void deleteAll() {
        List<Label> currentLabels = readJson().stream()
                .peek(l -> l.setStatus(PostStatus.DELETED)).toList();
        rewriteJson(currentLabels);
    }
}
