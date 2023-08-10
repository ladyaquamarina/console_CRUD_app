package com.Tretyak_Marina.javacore.chapter10;

import com.Tretyak_Marina.javacore.chapter10.controller.LabelController;
import com.Tretyak_Marina.javacore.chapter10.model.Label;
import com.Tretyak_Marina.javacore.chapter10.repository.LabelRepository;

import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.valueOf;
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

        Label label1 = new Label();
        label1.setId(1L);
        label1.setName("label 1");
        Label label2 = new Label();
        label2.setId(2L);
        label2.setName("label 2");

        labels.add(label1);
        labels.add(label2);
    }
    private void setLabelRepositoryCreate() {
        Label labelReturn = new Label();
        labelReturn.setId(1L);
        labelReturn.setName("label 1");
        when(labelRepository.add(any(Label.class))).thenReturn(labelReturn);
    }
    private void setLabelRepositoryGetLabel() {
        Label label = new Label();
        label.setId(1L);
        label.setName("label 1");
        when(labelRepository.getById(1L)).thenReturn(label);
        when(labelRepository.getById(500L)).thenReturn(null);
    }
    private void setLabelRepositoryGetAllLabels() {
        when(labelRepository.getAll()).thenReturn(labels);
    }
    private void setLabelRepositoryUpdate() {
        Label label = new Label();
        label.setId(1L);
        label.setName("new label 1");
        when(labelRepository.update(any(Label.class))).thenReturn(label);
    }

    @Test
    public void shouldCreateLabelTest() {
        setLabelRepositoryCreate();
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
        setLabelRepositoryGetLabel();
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
        setLabelRepositoryGetAllLabels();
        List<Label> resultList = labelController.getAllLabels();
        assertEquals(labels, resultList);
    }

    @Test
    public void shouldUpdateLabelTest() {
        setLabelRepositoryUpdate();
        Label label = labelController.updateLabel(1, "new label 1");
        assertEquals(valueOf(1), label.getId());
        assertEquals("new label 1", label.getName());
    }

    @Test
    public void shouldNotUpdateLabelTest() {
        assertNull(labelController.updateLabel(1, ""));
        assertNull(labelController.updateLabel(-1, "new label 1"));
    }

    @Test
    public void shouldDeleteLabelTest() {
        labelController.deleteLabel(1);
        when(labelRepository.getById(1L)).thenReturn(null);
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
