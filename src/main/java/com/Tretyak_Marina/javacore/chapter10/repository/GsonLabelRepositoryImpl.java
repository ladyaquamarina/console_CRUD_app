package com.Tretyak_Marina.javacore.chapter10.repository;

import com.Tretyak_Marina.javacore.chapter10.model.Label;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonLabelRepositoryImpl implements LabelRepository {
    public boolean addToJson(Label label){
        try {
            Gson gson = new Gson();
            List<Label> labels = readJson();
            labels.add(label);
            PrintWriter printWriter = new PrintWriter(FILENAME);
            printWriter.write("");
            printWriter.flush();
            printWriter.close();
            java.io.Writer ioWriter = new FileWriter(FILENAME);
            gson.toJson(labels, ioWriter);
            ioWriter.flush();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            System.out.println();
            return false;
        }
        return true;
    }

    public boolean rewriteJson(List<Label> labelsList) {
        try {
            File file = new File(FILENAME);
            file.createNewFile();
            PrintWriter printWriter = new PrintWriter(FILENAME);
            printWriter.write("");
            printWriter.flush();
            printWriter.close();
            Gson gson = new Gson();
            java.io.Writer ioWriter = new FileWriter(FILENAME);
            gson.toJson(labelsList, ioWriter);
            ioWriter.flush();
        } catch (JsonSyntaxException | IOException e) {
            System.out.println("Exception: " + e);
            System.out.println();
            return false;
        }
        return true;
    }

    public List<Label> readJson() {
        List<Label> labels = null;
        try {
            File file = new File(FILENAME);
            file.createNewFile();
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(FILENAME));
            Type listLabelType = new TypeToken<List<Label>>() {}.getType();
            labels = gson.fromJson(reader, listLabelType);
            if (labels == null)
                labels = new ArrayList<>();
            PrintWriter printWriter = new PrintWriter(FILENAME);
            printWriter.write("");
            printWriter.flush();
            printWriter.close();
        } catch (JsonSyntaxException | IOException e) {
            System.out.println("Exception: " + e);
            System.out.println();
            return null;
        }
        return labels;
    }

    public Label getById(Integer id) {
        Label result = null;
        try {
            List<Label> labels = readJson();
            for (Label l : labels)
                if (l.getId() == id) {
                    result = l;
                    labels.remove(l);
                    break;
                }
            rewriteJson(labels);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            System.out.println();
            return null;
        }
        return result;
    }
}
