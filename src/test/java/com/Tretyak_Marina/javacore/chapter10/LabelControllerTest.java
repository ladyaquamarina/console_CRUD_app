package com.Tretyak_Marina.javacore.chapter10;

import com.Tretyak_Marina.javacore.chapter10.controller.LabelController;
import com.Tretyak_Marina.javacore.chapter10.model.Label;
import com.Tretyak_Marina.javacore.chapter10.repository.LabelRepository;

import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.valueOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LabelControllerTest {
    private final LabelRepository labelRepository = mock();
    private final LabelController labelController = new LabelController(labelRepository);
    private List<Label> labels;

    @Before
    public void before() {
        setLabels();
        setLabelRepositoryCreate();
        setLabelRepositoryGetLabel();
        setLabelRepositoryGetAllLabels();
        setLabelRepositoryUpdate();
    }
    private void setLabels(){
        labels = new ArrayList<>();
        Label label1 = new Label(1, "label 1");
        Label label2 = new Label(2, "label 2");
        labels.add(label1);
        labels.add(label2);
    }
    private void setLabelRepositoryCreate() {
        Label label = new Label("label 1");
        Label labelReturn = new Label(1, "label 1");
        when(labelRepository.add(any(Label.class))).thenReturn(labelReturn);
    }
    private void setLabelRepositoryGetLabel() {
        Label label = new Label(1, "label 1");
        when(labelRepository.getById(1)).thenReturn(label);
        when(labelRepository.getById(500)).thenReturn(label);
    }
    private void setLabelRepositoryGetAllLabels() {
        when(labelRepository.getAll()).thenReturn(labels);
    }
    private void setLabelRepositoryUpdate() {
        Label label = new Label(1, "new label 1");
        when(labelRepository.update(any(Label.class))).thenReturn(label);
    }

    @Test
    public void shouldCreateLabelTest() {
        Label label = labelController.createLabel("label 1");
        assertEquals(valueOf(1), label.getId());
        assertEquals("label 1", label.getName());
    }

    @Test
    public void shouldNotCreateEmptyNameLabelTest() {
        assertNull(labelController.createLabel(""));
    }

    @Test
    public void shouldGetLabelTest() {
        Label label = labelController.getLabel(1);
        assertEquals(valueOf(1), label.getId());
        assertEquals("label 1", label.getName());
    }

    @Test
    public void shouldNotGetWrongIdLabelTest() {
        assertNull(labelController.getLabel(-1));
    }

    @Test
    public void shouldGetAllLabelsTest() {
        List<Label> resultList = labelController.getAllLabels();
        assertEquals(labels, resultList);
    }

    @Test
    public void shouldUpdateLabelTest() {
        Label label = labelController.updateLabel(1, "new label 1");
        assertEquals(valueOf(1), label.getId());
        assertEquals("new label 1", label.getName());
    }

    @Test
    public void shouldNotUpdateEmptyNameLabelTest() {
        assertNull(labelController.updateLabel(1, ""));
    }

    @Test
    public void shouldNotUpdateWrongIdLabelTest() {
        assertNull(labelController.updateLabel(-1, "new label 1"));
    }

    @Test
    public void shouldDeleteLabelTest() {
        labelController.deleteLabel(1);
        when(labelRepository.getById(1)).thenReturn(null);
        assertNull(labelController.getLabel(1));
    }

    @Test
    public void shouldDeleteWrongIdLabelTest() {
        labelController.deleteLabel(-1);
    }

    @Test
    public void shouldDeleteAllLabelsTest() {
        labelController.deleteAllLabels();
        when(labelRepository.getAll()).thenReturn(null);
        assertNull(labelController.getAllLabels());
    }
}
