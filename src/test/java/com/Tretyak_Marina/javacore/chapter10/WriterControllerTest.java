package com.Tretyak_Marina.javacore.chapter10;

import com.Tretyak_Marina.javacore.chapter10.controller.WriterController;
import com.Tretyak_Marina.javacore.chapter10.model.Post;
import com.Tretyak_Marina.javacore.chapter10.model.PostStatus;
import com.Tretyak_Marina.javacore.chapter10.model.Writer;
import com.Tretyak_Marina.javacore.chapter10.repository.WriterRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.valueOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class WriterControllerTest {
    private final WriterRepository writerRepository = mock();
    private final WriterController writerController = new WriterController(writerRepository);
    private List<Writer> writers;

    @Before
    public void before() {
        setWriters();
        setWriterRepositoryCreate();
        setWriterRepositoryGetWriter();
        setWriterRepositoryGetAllWriters();
        setWriterRepositoryUpdate();
    }

    private void setWriters() {
        writers = new ArrayList<>();
        Writer writer1 = new Writer(1, "first name 1", "last name 1", new ArrayList<>());
        Writer writer2 = new Writer(2, "first name 2", "last name 2", new ArrayList<>());
        writers.add(writer1);
        writers.add(writer2);
    }

    private void setWriterRepositoryCreate() {
        Writer writer = new Writer("first name 1", "second name 1");
        Writer writerReturn = new Writer(1, "first name 1", "last name 1", new ArrayList<>());
        when(writerRepository.add(any(Writer.class))).thenReturn(writerReturn);
    }

    private void setWriterRepositoryGetWriter() {
        Writer writer = new Writer(1, "first name 1", "last name 1", new ArrayList<>());
        when(writerRepository.getById(1)).thenReturn(writer);
        when(writerRepository.getById(500)).thenReturn(null);
    }

    private void setWriterRepositoryGetAllWriters() {
        when(writerRepository.getAll()).thenReturn(writers);
    }

    private void setWriterRepositoryUpdate() {
        Writer writer = new Writer(1, "new first name 1", "last name 1", new ArrayList<>());
        when(writerRepository.update(any(Writer.class))).thenReturn(writer);
    }

    @Test
    public void shouldCreateWriterTest() {
        Writer writer = writerController.createWriter("first name 1", "last name 1");
        assertEquals(valueOf(1), writer.getId());
        assertEquals("first name 1", writer.getFirstName());
        assertEquals("last name 1", writer.getLastName());
        assertEquals(0, writer.getPosts().size());
    }

    @Test
    public void shouldNotCreateWriterTest() {
        assertNull(writerController.createWriter("", "last name 1"));
        assertNull(writerController.createWriter("first name 1", ""));
    }

    @Test
    public void shouldGetWriterTest() {
        Writer writer = writerController.getWriter(1);
        assertEquals(valueOf(1), writer.getId());
        assertEquals("first name 1", writer.getFirstName());
        assertEquals("last name 1", writer.getLastName());
        assertEquals(0, writer.getPosts().size());
    }

    @Test
    public void shouldNotGetWriterTest() {
        assertNull(writerController.getWriter(-1));
    }

    @Test
    public void shouldGetAllWritersTest() {
        List<Writer> resultList = writerController.getAllWriters();
        assertEquals(writers, resultList);
    }

    @Test
    public void shouldUpdateWriterFirstNameTest() {
        Writer writer = writerController.updateWriter(1, "new first name 1", "first");
        assertEquals(valueOf(1), writer.getId());
        assertEquals("new first name 1", writer.getFirstName());
        assertEquals("last name 1", writer.getLastName());
        assertEquals(0, writer.getPosts().size());
    }

    @Test
    public void shouldUpdateWriterLastNameTest() {
        Writer result = new Writer(1, "first name 1", "new last name 1", new ArrayList<>());
        when(writerRepository.update(any(Writer.class))).thenReturn(result);

        Writer writer = writerController.updateWriter(1, "new last name 1", "last");
        assertEquals(valueOf(1), writer.getId());
        assertEquals("first name 1", writer.getFirstName());
        assertEquals("new last name 1", writer.getLastName());
        assertEquals(0, writer.getPosts().size());
    }

    @Test
    public void shouldNotUpdateWriterTest() {
        assertNull(writerController.updateWriter(-1, "new first name 1", "first"));
        assertNull(writerController.updateWriter(1, "", "first"));
    }

    @Test
    public void shouldAddPostToWriterTest() {
        Writer writer = new Writer(1, "first name 1", "last name 1", new ArrayList<>());
        Date now = new Date();
        Post post = new Post(1, "post 1", now, now, new ArrayList<>(), PostStatus.ACTIVE);
        writer.addPost(post);
        when(writerRepository.update(any(Writer.class))).thenReturn(writer);
        Writer result = writerController.addPostToWriter(1, post);
        assertEquals(writer, result);
    }

    @Test
    public void shouldNotAddPostToWriterTest() {
        Date now = new Date();
        Post post = new Post(1, "post 1", now, now, new ArrayList<>(), PostStatus.ACTIVE);
        assertNull(writerController.addPostToWriter(-1, post));
    }

    @Test
    public void shouldDeletePostFromWriterTest() {
        Writer result = new Writer(1, "first name 1", "last name 1", new ArrayList<>());
        Date now = new Date();
        Post post = new Post(1, "post 1", now, now, new ArrayList<>(), PostStatus.ACTIVE);
        when(writerRepository.update(any(Writer.class))).thenReturn(result);
        Writer writer = new Writer(1, "first name 1", "last name 1", new ArrayList<>());
        writer.addPost(post);
        writer = writerController.deletePostFromWriter(1, 1);
        assertEquals(result.getPosts(), writer.getPosts());
    }

    @Test
    public void shouldNotDeletePostFromWriterTest() {
        assertNull(writerController.deletePostFromWriter(-1, 1));
        assertNull(writerController.deletePostFromWriter(1, -1));
    }

    @Test
    public void shouldDeleteAllPostsFromWriterTest() {
        Writer result = new Writer(1, "first name 1", "last name 1", new ArrayList<>());
        when(writerRepository.update(any(Writer.class))).thenReturn(result);

        Date now = new Date();
        Post post1 = new Post(1, "post 1", now, now, new ArrayList<>(), PostStatus.ACTIVE);
        Post post2 = new Post(2, "post 2", now, now, new ArrayList<>(), PostStatus.ACTIVE);
        Writer writer = new Writer(1, "first name 1", "last name 1", new ArrayList<>());
        writer.addPost(post1);
        writer.addPost(post2);

        writer = writerController.deleteAllPostFromWriter(1);
        assertEquals(valueOf(1), writer.getId());
        assertEquals("first name 1", writer.getFirstName());
        assertEquals("last name 1", writer.getLastName());
        assertEquals(0, writer.getPosts().size());
    }

    @Test
    public void shouldNotDeleteAllPostsFromWriterTest() {
        assertNull(writerController.deleteAllPostFromWriter(-1));
    }

    @Test
    public void shouldDeleteWriterTest() {
        writerController.deleteAllWriters();
        when(writerRepository.getAll()).thenReturn(null);
        assertNull(writerController.getAllWriters());
    }
}
