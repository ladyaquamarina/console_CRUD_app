package com.Tretyak_Marina.javacore.chapter10.controller;

import com.Tretyak_Marina.javacore.chapter10.model.Label;
import com.Tretyak_Marina.javacore.chapter10.repository.LabelRepository;

import java.util.List;

public class LabelController {
    private final LabelRepository labelRepository;
    public LabelController(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }
    public Label createLabel(String name) {
        if (name.isEmpty()) {
            return null;
        }
        Label label = new Label(name);
        return labelRepository.add(label);
    }
    public Label getLabel(int labelId) {
        if (labelId < 1)
            return null;
        return labelRepository.getById(labelId);
    }
    public List<Label> getAllLabels() { // for use in methods that modify objects
        return labelRepository.getAll();
    }
    public Label updateLabel(int labelId, String newName) {
        if (labelId < 1 || newName.isEmpty()) {
            return null;
        }
        Label label = getLabel(labelId);
        try {
            label.setName(newName);
            label = labelRepository.update(label);
        } catch (NullPointerException e) {
            System.out.println("\nThere are no labels with this ID!\n");
            return null;
        }
        return label;
    }
    public void deleteLabel(int labelId) {   // доделать
        labelRepository.deleteById(labelId);
    }
    public void deleteAllLabels() {  // доделать
        labelRepository.deleteAll();
    }
}