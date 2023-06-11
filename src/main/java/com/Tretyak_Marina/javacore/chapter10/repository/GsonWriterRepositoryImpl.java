package com.Tretyak_Marina.javacore.chapter10.repository;

import com.Tretyak_Marina.javacore.chapter10.model.Writer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonWriterRepositoryImpl implements WriterRepository {
    public boolean addToJson(Writer writer) {
        try {
            Gson gson = new Gson();
            List<Writer> writers = readJson();
            writers.add(writer);
            PrintWriter printWriter = new PrintWriter(FILENAME);
            printWriter.write("");
            printWriter.flush();
            printWriter.close();
            java.io.Writer ioWriter = new FileWriter(FILENAME);
            gson.toJson(writers, ioWriter);
            ioWriter.flush();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            System.out.println();
            return false;
        }
        return true;
    }

    public boolean rewriteJson(List<Writer> writersList) {
        try {
            File file = new File(FILENAME);
            file.createNewFile();
            PrintWriter printWriter = new PrintWriter(FILENAME);
            printWriter.write("");
            printWriter.flush();
            printWriter.close();
            Gson gson = new Gson();
            java.io.Writer ioWriter = new FileWriter(FILENAME);
            gson.toJson(writersList, ioWriter);
            ioWriter.flush();
        } catch (JsonSyntaxException | IOException e) {
            System.out.println("Exception: " + e);
            System.out.println();
            return false;
        }
        return true;
    }

    public List<Writer> readJson() {
        List<Writer> writers = null;
        try {
            File file = new File(FILENAME);
            file.createNewFile();
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(FILENAME));
            Type listWriterType = new TypeToken<List<Writer>>() {}.getType();
            writers = gson.fromJson(reader, listWriterType);
            if (writers == null)
                writers = new ArrayList<>();
            PrintWriter printWriter = new PrintWriter(FILENAME);
            printWriter.write("");
            printWriter.flush();
            printWriter.close();
        } catch (JsonSyntaxException | IOException e) {
            System.out.println("Exception: " + e);
            System.out.println();
            return null;
        }
        return writers;
    }

    public Writer getById(Integer id){
        Writer result = null;
        try {
            List<Writer> writers = readJson();
            for (Writer w : writers)
                if (w.getId() == id) {
                    result = w;
                    writers.remove(w);
                    break;
                }
            rewriteJson(writers);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            System.out.println();
            return null;
        }
        return result;
    }
}
